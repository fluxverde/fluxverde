package io.fluxverde.domain.company.audit;

import io.fluxverde.domain.audit.EAuditEvidenceCategory;
import io.fluxverde.domain.audit.EFileType;
import java.time.Instant;

public record AuditEvidence(
    Long id,
    String evidenceCode,
    String evidenceTitle,
    String fileName,
    EFileType fileType,
    String filePath,
    Long fileSize,
    EAuditEvidenceCategory category,
    String uploadedBy,
    Instant uploadedAt,
    String description,
    Instant createdAt,
    Instant updatedAt,
    EnergyAudit audit,
    AuditFinding finding
) {
}
