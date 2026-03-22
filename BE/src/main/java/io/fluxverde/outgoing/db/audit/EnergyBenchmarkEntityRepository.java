package io.fluxverde.outgoing.db.audit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnergyBenchmarkEntityRepository extends JpaRepository<EnergyBenchmarkEntity, Long> {
}

