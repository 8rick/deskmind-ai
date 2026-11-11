package br.com.rick.deskmind_backend.ticket;

public class InvalidTicketStateException extends RuntimeException{
    public InvalidTicketStateException(String message) {
        super(message);
    }
}
