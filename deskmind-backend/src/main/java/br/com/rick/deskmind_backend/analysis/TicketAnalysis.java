package br.com.rick.deskmind_backend.analysis;

import br.com.rick.deskmind_backend.ticket.Ticket;
import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Table(name = "ticket_analysis")
public class TicketAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Enumerated(EnumType.STRING)
    private AnalysisCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "suggested_priority")
    private AnalysisPriority suggestedPriority;

    private String summary;

    @Column(name = "suggested_response")
    private String suggestedResponse;

    private String reasoning;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    protected TicketAnalysis() {}

   protected TicketAnalysis(Ticket ticket, AnalysisCategory category, AnalysisPriority priority, String summary, String suggested, String reasoning) {
       this.ticket = ticket;
       this.category = category;
       this.suggestedPriority = priority;
       this.summary = summary;
       this.suggestedResponse = suggested;
       this.reasoning = reasoning;
   }

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
    }

    public UUID getId() {
        return id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public AnalysisCategory getCategory() {
        return category;
    }

    public AnalysisPriority getSuggestedPriority() {
        return suggestedPriority;
    }

    public String getSummary() {
        return summary;
    }

    public String getSuggestedResponse() {
        return suggestedResponse;
    }

    public String getReasoning() {
        return reasoning;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
