package br.com.rick.deskmind_backend.analysis;

import br.com.rick.deskmind_backend.ticket.Ticket;

import java.time.LocalDateTime;
import java.util.UUID;

public record AnalisysResponse(
        UUID id,
        Ticket ticketId,
        AnalysisCategory category,
        AnalysisPriority suggestedPriority,
        String summary,
        String suggestedResponse,
        String reasoning,
        LocalDateTime createdAt
) {

    public static AnalisysResponse from(TicketAnalysis ticketAnalisysResponse) {
        return  new AnalisysResponse(
                ticketAnalisysResponse.getId(),
                ticketAnalisysResponse.getTicket(),
                ticketAnalisysResponse.getCategory(),
                ticketAnalisysResponse.getSuggestedPriority(),
                ticketAnalisysResponse.getSummary(),
                ticketAnalisysResponse.getSuggestedResponse(),
                ticketAnalisysResponse.getReasoning(),
                ticketAnalisysResponse.getCreatedAt()
        );
    }
}
