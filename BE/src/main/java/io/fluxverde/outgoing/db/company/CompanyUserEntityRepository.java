package io.fluxverde.outgoing.db.company;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyUserEntityRepository extends JpaRepository<CompanyUserEntity, Long> {
    Optional<CompanyUserEntity> findByUserEmail(String userEmail);
}
