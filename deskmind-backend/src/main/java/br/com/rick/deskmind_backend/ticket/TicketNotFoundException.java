package br.com.rick.deskmind_backend.ticket;

import java.util.UUID;

public class TicketNotFoundException extends RuntimeException{

    public TicketNotFoundException(UUID id) {
        super("Chamado não encontrado: " + id);
    }
}
