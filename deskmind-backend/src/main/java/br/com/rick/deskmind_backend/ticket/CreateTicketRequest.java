package br.com.rick.deskmind_backend.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTicketRequest(

        @NotBlank(message = "O título é obirgatório")
        @Size(max = 150, message = "O título deve ter no máximo 150 caracteres")
        String title,

        @Size(max = 5000, message = "A descrição deve ter no máximo 5000 caracteres")
        String description
) {
}
