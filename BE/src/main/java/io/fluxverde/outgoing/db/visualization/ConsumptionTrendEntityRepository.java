package io.fluxverde.outgoing.db.visualization;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumptionTrendEntityRepository extends JpaRepository<ConsumptionTrendEntity, Long> {
    List<ConsumptionTrendEntity> findByTrendDate(LocalDate trendDate);
}
