package br.com.rick.deskmind_backend.analysis.dto;

import br.com.rick.deskmind_backend.analysis.AnalysisCategory;
import br.com.rick.deskmind_backend.analysis.AnalysisPriority;
import br.com.rick.deskmind_backend.analysis.TicketAnalysis;

import java.time.LocalDateTime;
import java.util.UUID;

public record AnalisysResponse(
        UUID id,
        UUID ticketId,
        AnalysisCategory category,
        AnalysisPriority priority,
        String summary,
        String suggestedResponse,
        String reasoning,
        LocalDateTime createdAt
) {

    public static AnalisysResponse from(TicketAnalysis analysis) {
        return  new AnalisysResponse(
                analysis.getId(),
                analysis.getTicket().getId(),
                analysis.getCategory(),
                analysis.getSuggestedPriority(),
                analysis.getSummary(),
                analysis.getSuggestedResponse(),
                analysis.getReasoning(),
                analysis.getCreatedAt()
        );
    }
}
