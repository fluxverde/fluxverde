package io.fluxverde.outgoing.db.company;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuditAccessEntityRepository extends JpaRepository<UserAuditAccessEntity, Long> {
}

