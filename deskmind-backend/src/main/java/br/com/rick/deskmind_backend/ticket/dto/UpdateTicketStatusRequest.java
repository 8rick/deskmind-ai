package br.com.rick.deskmind_backend.ticket.dto;

import br.com.rick.deskmind_backend.ticket.TicketStatus;
import jakarta.validation.constraints.NotNull;


public record UpdateTicketStatusRequest(

        @NotNull(message = "O status é obrigatório")
        TicketStatus status
) {
}
