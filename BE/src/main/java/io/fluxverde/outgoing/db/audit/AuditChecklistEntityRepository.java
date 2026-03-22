package io.fluxverde.outgoing.db.audit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditChecklistEntityRepository extends JpaRepository<AuditChecklistEntity, Long> {
}

