package br.com.rick.deskmind_backend.analysis;

import br.com.rick.deskmind_backend.analysis.dto.AnalisysSuggestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
@ConditionalOnProperty(name = "ai.analyzer.provider", havingValue = "http", matchIfMissing = true)
public class HttpTicketAnalyzer implements TicketAnalyzer {

    private static final Logger log = LoggerFactory.getLogger(HttpTicketAnalyzer.class);
    private final RestClient restClient;

    private record AnalyzeRequestPayload(String title, String description) {}

    public HttpTicketAnalyzer(
            RestClient.Builder builder,
            @Value("${ai.service.url}") String aiServiceUrl,
            @Value("${ai.service.connect-timeout-ms:5000}") int connectTimeoutMs,
            @Value("${ai.service.read-timeout-ms:30000}") int readTimeoutMs
    ) {
        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeoutMs);
        factory.setReadTimeout(readTimeoutMs);

        this.restClient = builder
                .baseUrl(aiServiceUrl)
                .requestFactory(factory)
                .build();
    }

    @Override
    public AnalisysSuggestion analyze(String title, String description) {
        var payload = new AnalyzeRequestPayload(title, description);

        try {
            AnalisysSuggestion response = restClient.post()
                    .uri("/analyze")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(payload)
                    .retrieve()
                    .body(AnalisysSuggestion.class);

            if (response == null) {
                log.error("O serviço de IA retornou corpo vazio para o chamado '{}'", title);
                throw new AiServiceUnavailableException("Resposta vazia do serviço de IA.");
            }
            return response;
        } catch (HttpClientErrorException ex) {
            log.error("Erro 4xx ao chamar o serviço de IA (status {}): {}", ex.getStatusCode(), ex.getResponseBodyAsString(), ex);
            throw new IllegalStateException("Requisição inválida enviada ao serviço de IA.", ex);
        } catch (RestClientException ex) {
            log.error("Falha de comunicação ou erro interno no serviço de IA", ex);
            throw new AiServiceUnavailableException("Serviço de IA indisponível no momento.", ex);
        }
    }
}