package br.com.rick.deskmind_backend.analysis;

import org.springframework.stereotype.Component;

@Component
public class FakeTicketAnalyzer implements TicketAnalyzer {

    @Override
    public AnalisysSuggestion analyze(String tiltle, String description) {
       return new AnalisysSuggestion(
               AnalysisCategory.GENERAL,
               AnalysisCategory.MEDIUM,
               "Análise inicial do chamado: " + tiltle,
               "Óla Recebemos seu chamado e nossa equipe já está verificando a solicitação.",
               "Classificação gerada pelo analisador simulado (FakeTicketAnalyzer)."
                );
    }
}
