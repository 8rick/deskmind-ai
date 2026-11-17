package br.com.rick.deskmind_backend.analysis;

import br.com.rick.deskmind_backend.analysis.dto.AnalisysSuggestion;

public interface TicketAnalyzer {

    AnalisysSuggestion analyze(String tiltle, String description);
}
