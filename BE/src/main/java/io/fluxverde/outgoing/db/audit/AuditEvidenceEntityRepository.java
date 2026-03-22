package io.fluxverde.outgoing.db.audit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditEvidenceEntityRepository extends JpaRepository<AuditEvidenceEntity, Long> {
}

