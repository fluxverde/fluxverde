package io.fluxverde.outgoing.db.company;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeterReadingEntityRepository extends JpaRepository<MeterReadingEntity, Long> {
    List<MeterReadingEntity> findByBatchId(Long batchId);
}
