package br.com.rick.deskmind_backend.analysis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketAnalisysRepository extends JpaRepository<TicketAnalysis, UUID> {
      List<TicketAnalysis> findByTicketIdOrderByCreatedAtDesc(UUID ticketId);
}
