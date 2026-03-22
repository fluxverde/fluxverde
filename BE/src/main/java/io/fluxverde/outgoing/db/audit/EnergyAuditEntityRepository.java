package io.fluxverde.outgoing.db.audit;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnergyAuditEntityRepository extends JpaRepository<EnergyAuditEntity, Long> {
    Optional<EnergyAuditEntity> findByAuditCode(String auditCode);
}
