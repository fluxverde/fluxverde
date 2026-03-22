package io.fluxverde.outgoing.db.company;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeterTypeEntityRepository extends JpaRepository<MeterTypeEntity, Long> {
    Optional<MeterTypeEntity> findByTypeName(String typeName);
}
