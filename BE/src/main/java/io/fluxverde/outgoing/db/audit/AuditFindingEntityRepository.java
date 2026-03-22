package io.fluxverde.outgoing.db.audit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditFindingEntityRepository extends JpaRepository<AuditFindingEntity, Long> {
}

