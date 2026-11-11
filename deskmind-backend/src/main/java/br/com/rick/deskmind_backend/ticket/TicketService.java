package br.com.rick.deskmind_backend.ticket;


import br.com.rick.deskmind_backend.ticket.dto.CreateTicketRequest;
import br.com.rick.deskmind_backend.ticket.dto.TicketResponse;
import br.com.rick.deskmind_backend.ticket.dto.UpdateTicketStatusRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Transactional(readOnly = true)
    public List<TicketResponse> findAll() {
        return ticketRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(TicketResponse::from)
                .toList();
    }

    @Transactional
    public TicketResponse updateStatus(UUID id, UpdateTicketStatusRequest request) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));

        ticket.updateStatus(request.status());
        return TicketResponse.from(ticket);
    }
}