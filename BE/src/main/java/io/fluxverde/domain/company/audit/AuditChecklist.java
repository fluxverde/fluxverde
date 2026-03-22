package io.fluxverde.domain.company.audit;

import io.fluxverde.domain.audit.EAuditChecklistCategory;
import java.time.Instant;
import java.time.LocalDate;

public record AuditChecklist(
    Long id,
    String checklistItemCode,
    String checklistItemTitle,
    EAuditChecklistCategory category,
    Boolean isMandatory,
    String regulatoryReference,
    String description,
    String guidance,
    String expectedDocumentation,
    Boolean isCompleted,
    String completedBy,
    LocalDate completedDate,
    String comments,
    Instant createdAt,
    Instant updatedAt,
    EnergyAudit audit
) {
}
