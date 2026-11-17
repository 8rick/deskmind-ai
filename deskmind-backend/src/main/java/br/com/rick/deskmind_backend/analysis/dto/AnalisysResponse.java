package br.com.rick.deskmind_backend.analysis.dto;

import br.com.rick.deskmind_backend.analysis.AnalisysCategory;
import br.com.rick.deskmind_backend.analysis.AnalisysPriority;
import br.com.rick.deskmind_backend.analysis.TicketAnalisys;

import java.time.LocalDateTime;
import java.util.UUID;

public record AnalisysResponse(
        UUID id,
        UUID ticketId,
        AnalisysCategory category,
        AnalisysPriority priority,
        String summary,
        String suggestedResponse,
        String reasoning,
        LocalDateTime createdAt
) {

    public static AnalisysResponse from(TicketAnalisys analysis) {
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
