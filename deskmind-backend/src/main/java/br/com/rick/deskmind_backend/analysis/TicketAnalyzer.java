package br.com.rick.deskmind_backend.analysis;

public interface TicketAnalyzer {

    AnalisysSuggestion analyze(String tiltle, String description);
}
