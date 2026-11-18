package br.com.rick.deskmind_backend.analysis;

import br.com.rick.deskmind_backend.analysis.dto.AnalisysSuggestion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
@Primary
public class HttpTicketAnalyzer implements TicketAnalyzer {

    private final RestClient restClient;

    private record AnalyzeRequestPayload(String title, String description) {}

    public HttpTicketAnalyzer(
            RestClient.Builder builder,
            @Value("${ai.service.url}") String aiServiceUrl
    ) {
        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000); // 5s p/ concectar
        factory.setConnectTimeout(30000); // 30s p/ o LLM responder

        this.restClient = builder
                .baseUrl(aiServiceUrl)
                .requestFactory(new SimpleClientHttpRequestFactory())
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
                throw new AiServiceUnavailableException("Resposta vazia do serviço de IA.");
            }
            return response;
        } catch (RestClientException ex) {
            throw new AiServiceUnavailableException("Serviço de IA indisponível no momento.", ex);
        }
    }
}