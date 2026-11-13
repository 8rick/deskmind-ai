package br.com.rick.deskmind_backend.analysis;

import br.com.rick.deskmind_backend.analysis.dto.AnalisysResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tickets/{ticketId}/analyses")
public class TicketAnalysisController {

    private final TicketAnalysisService ticketAnalysisService;

    public  TicketAnalysisController(TicketAnalysisService ticketAnalysisService) {
        this.ticketAnalysisService = ticketAnalysisService;
    }

    @ApiResponse(responseCode = "201")
    @PostMapping
    public ResponseEntity<AnalisysResponse> requestAnalisys(@PathVariable UUID ticketId) {
        AnalisysResponse response = ticketAnalysisService.requestAnalysis(ticketId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AnalisysResponse>> findAll(@PathVariable UUID ticketId) {
        List<AnalisysResponse> analyses = ticketAnalysisService.findAllByTicket(ticketId);
        return ResponseEntity.ok(analyses);
    }
}
