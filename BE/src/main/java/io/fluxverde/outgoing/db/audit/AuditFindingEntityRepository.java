package io.fluxverde.outgoing.db.audit;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditFindingEntityRepository extends JpaRepository<AuditFindingEntity, Long> {
    Optional<AuditFindingEntity> findByFindingCode(String findingCode);
}
