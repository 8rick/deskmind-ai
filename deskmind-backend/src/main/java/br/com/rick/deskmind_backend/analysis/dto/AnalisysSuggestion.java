package br.com.rick.deskmind_backend.analysis.dto;

import br.com.rick.deskmind_backend.analysis.AnalisysCategory;
import br.com.rick.deskmind_backend.analysis.AnalisysPriority;

public record AnalisysSuggestion(
        AnalisysCategory category,
        AnalisysPriority priority,
        String summary,
        String suggestedResponse,
        String reasoning
) {

}
