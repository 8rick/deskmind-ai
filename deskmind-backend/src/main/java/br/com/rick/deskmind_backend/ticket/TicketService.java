package br.com.rick.deskmind_backend.ticket;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public TicketResponse create(CreateTicketRequest request) {
        Ticket ticket = new Ticket(request.title().strip(), request.description());
        Ticket saved = ticketRepository.save(ticket);
        return TicketResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public TicketResponse findById(UUID id) {
        return ticketRepository.findById(id)
                .map(TicketResponse::from)
                .orElseThrow(() -> new TicketNotFoundException(id));
    }
}
