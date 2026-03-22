package io.fluxverde.outgoing.db.company;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuditAccessEntityRepository extends JpaRepository<UserAuditAccessEntity, Long> {
    Optional<UserAuditAccessEntity> findByUserIdAndAuditId(Long userId, Long auditId);
}
