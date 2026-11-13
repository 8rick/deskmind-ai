package br.com.rick.deskmind_backend.analysis;

public record AnalisysSuggestion(
        AnalysisCategory category,
        AnalysisPriority priority,
        String summary,
        String suggested,
        String reasoning
) {

}
