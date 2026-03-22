package io.fluxverde.outgoing.db.audit;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditEvidenceEntityRepository extends JpaRepository<AuditEvidenceEntity, Long> {
    Optional<AuditEvidenceEntity> findByEvidenceCode(String evidenceCode);
}
