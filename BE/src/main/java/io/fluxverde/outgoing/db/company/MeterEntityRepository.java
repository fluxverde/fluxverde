package io.fluxverde.outgoing.db.company;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeterEntityRepository extends JpaRepository<MeterEntity, Long> {
    Optional<MeterEntity> findByMeterCode(String meterCode);
    Optional<MeterEntity> findByMeterSerialNumber(String meterSerialNumber);
}
