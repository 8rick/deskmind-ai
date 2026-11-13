package br.com.rick.deskmind_backend.ticket;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TicketStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    // O JPA precisa para criar o objeto ao ler
    protected Ticket() {}

    public Ticket(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = TicketStatus.OPEN;
    }

    @PrePersist
    void onCreate() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void updateStatus(TicketStatus NewStatus) {
        if(NewStatus == null) {
            throw new InvalidTicketStateException("O novo status não pode ser nulo.");
        }

        if(this.status == TicketStatus.CLOSED) {
            throw new InvalidTicketStateException("Não é possível alterar o status de um chamado que já está fechado.");
        }
        this.status = NewStatus;
    }

    public UUID getId() { return id;}
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public TicketStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }


}
