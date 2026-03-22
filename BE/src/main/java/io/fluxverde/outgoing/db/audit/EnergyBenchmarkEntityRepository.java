package io.fluxverde.outgoing.db.audit;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnergyBenchmarkEntityRepository extends JpaRepository<EnergyBenchmarkEntity, Long> {
    Optional<EnergyBenchmarkEntity> findByBenchmarkCode(String benchmarkCode);
}
