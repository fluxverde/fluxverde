package io.fluxverde.outgoing.db.company;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManualMeterEntryEntityRepository extends JpaRepository<ManualMeterEntryEntity, Long> {
    List<ManualMeterEntryEntity> findByEntryDate(LocalDate entryDate);
}
