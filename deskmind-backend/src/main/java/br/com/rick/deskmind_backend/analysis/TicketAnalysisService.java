package br.com.rick.deskmind_backend.analysis;

import br.com.rick.deskmind_backend.analysis.dto.AnalisysResponse;
import br.com.rick.deskmind_backend.ticket.Ticket;
import br.com.rick.deskmind_backend.ticket.TicketNotFoundException;
import br.com.rick.deskmind_backend.ticket.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TicketAnalysisService {

    private final TicketRepository ticketRepository;
    private final TicketAnalisysRepository ticketAnalisysRepository;
    private final TicketAnalyzer ticketAnalyzer;

    public TicketAnalysisService(TicketRepository ticketRepository, TicketAnalisysRepository ticketAnalisysRepository,  TicketAnalyzer ticketAnalyzer) {
        this.ticketRepository = ticketRepository;
        this.ticketAnalisysRepository = ticketAnalisysRepository;
        this.ticketAnalyzer = ticketAnalyzer;
    }

    @Transactional
    public AnalisysResponse requestAnalysis(UUID ticketId){
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(ticketId));

        //chama o analisador falso de teste (depois coloca a ia)
        AnalisysSuggestion suggestion = ticketAnalyzer.analyze(
                ticket.getTitle(),
                ticket.getDescription()
        );

        TicketAnalysis analysis = new TicketAnalysis(
                ticket,
                suggestion.category(),
                suggestion.priority(),
                suggestion.summary(),
                suggestion.suggested(),
                suggestion.reasoning()

        );

        TicketAnalysis saved = ticketAnalisysRepository.save((analysis));
        return AnalisysResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public List<AnalisysResponse> findAllByTicket(UUID ticketId) {

        if(!ticketRepository.existsById(ticketId)) {
            throw new TicketNotFoundException(ticketId);
        }
        return ticketAnalisysRepository.findByTicketIdOrderByCreatedAtDesc(ticketId)
                .stream()
                .map(AnalisysResponse::from)
                .toList();
    }



}
