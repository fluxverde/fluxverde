package io.fluxverde.outgoing.db.audit;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditChecklistEntityRepository extends JpaRepository<AuditChecklistEntity, Long> {
    Optional<AuditChecklistEntity> findByChecklistItemCode(String checklistItemCode);
}
