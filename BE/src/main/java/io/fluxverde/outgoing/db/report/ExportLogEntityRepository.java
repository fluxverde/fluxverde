package io.fluxverde.outgoing.db.report;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExportLogEntityRepository extends JpaRepository<ExportLogEntity, Long> {
    Optional<ExportLogEntity> findByExportCode(String exportCode);
}
