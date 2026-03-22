package io.fluxverde.outgoing.db.regulatory;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRequirementEntityRepository extends JpaRepository<CountryRequirementEntity, Long> {
    Optional<CountryRequirementEntity> findByRequirementCode(String requirementCode);
}
