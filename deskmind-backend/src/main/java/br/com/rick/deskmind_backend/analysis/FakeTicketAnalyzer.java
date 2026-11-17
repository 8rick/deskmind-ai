package br.com.rick.deskmind_backend.analysis;

import br.com.rick.deskmind_backend.analysis.dto.AnalisysSuggestion;
import org.springframework.stereotype.Component;

@Component
public class FakeTicketAnalyzer implements TicketAnalyzer {

    @Override
    public AnalisysSuggestion analyze(String title, String description) {
        return new AnalisysSuggestion(
                AnalisysCategory.GENERAL,
                AnalisysPriority.MEDIUM,
                "Análise inicial do chamado: " + title,
                "Olá! Recebemos o seu chamado e nossa equipe já está verificando a solicitação.",
                "Classificação gerada pelo analisador simulado (FakeTicketAnalyzer)."
        );
    }
}