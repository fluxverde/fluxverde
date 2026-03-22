package io.fluxverde.outgoing.db.report;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportGenerationEntityRepository extends JpaRepository<ReportGenerationEntity, Long> {
    Optional<ReportGenerationEntity> findByReportCode(String reportCode);
}
