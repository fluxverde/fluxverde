package io.fluxverde.outgoing.db.regulatory;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculationRuleEntityRepository extends JpaRepository<CalculationRuleEntity, Long> {
    Optional<CalculationRuleEntity> findByRuleCode(String ruleCode);
}
