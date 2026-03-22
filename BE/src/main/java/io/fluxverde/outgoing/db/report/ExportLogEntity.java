package io.fluxverde.outgoing.db.report;

import io.fluxverde.domain.report.EExportLogEntity;
import io.fluxverde.domain.report.EExportPurpose;
import io.fluxverde.domain.report.EExportType;
import io.fluxverde.outgoing.db.audit.EnergyAuditEntity;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "export_log")
public class ExportLogEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "export_code", nullable = false, unique = true)
    private String exportCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "export_type")
    private EExportType exportType;

    @Column(name = "export_format", length = 128)
    private String exportFormat;

    @Column(name = "exported_at", nullable = false)
    private Instant exportedAt;

    @Column(name = "exported_by", nullable = false)
    private String exportedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "exported_entity")
    private EExportLogEntity exportedEntity;

    @Column(name = "records_exported")
    private Long recordsExported;

    @Column(name = "export_file_name", length = 500)
    private String exportFileName;

    @Column(name = "export_file_path", length = 1000)
    private String exportFilePath;

    @Column(name = "recipient_email")
    private String recipientEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "purpose")
    private EExportPurpose purpose;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "audit_id")
    private EnergyAuditEntity audit;
}
