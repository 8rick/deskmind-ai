package br.com.rick.deskmind_backend.ticket;

import br.com.rick.deskmind_backend.ticket.dto.CreateTicketRequest;
import br.com.rick.deskmind_backend.ticket.dto.TicketResponse;
import br.com.rick.deskmind_backend.ticket.dto.UpdateTicketStatusRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<TicketResponse> create(@Valid @RequestBody CreateTicketRequest request) {
        TicketResponse respsponse = ticketService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(respsponse.id())
                .toUri();
        return ResponseEntity.created(location).body(respsponse);
    }

    @GetMapping("/{id}")
    public TicketResponse findById(@PathVariable UUID id) {
        return ticketService.findById(id);
    }

    @GetMapping
    public ResponseEntity<List<TicketResponse>> findAll() {
        List<TicketResponse> tickets = ticketService.findAll();
        return ResponseEntity.ok(tickets);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TicketResponse> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateTicketStatusRequest request
    ) {
        TicketResponse response = ticketService.updateStatus(id, request);
        return ResponseEntity.ok(response);

    }

}
