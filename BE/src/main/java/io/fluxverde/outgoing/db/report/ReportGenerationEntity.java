package io.fluxverde.outgoing.db.report;

import io.fluxverde.domain.report.EConfidentialityLevel;
import io.fluxverde.domain.report.EReportFormat;
import io.fluxverde.domain.report.EReportState;
import io.fluxverde.domain.report.EReportType;
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
@Table(name = "report_generation")
public class ReportGenerationEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_code", nullable = false, unique = true)
    private String reportCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", nullable = false)
    private EReportType reportType;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_format")
    private EReportFormat reportFormat;

    @Column(name = "generated_at", nullable = false)
    private Instant generatedAt;

    @Column(name = "generated_by", nullable = false)
    private String generatedBy;

    @Column(name = "report_title", length = 500)
    private String reportTitle;

    @Column(name = "report_file_name", length = 500)
    private String reportFileName;

    @Column(name = "report_file_path", length = 1000)
    private String reportFilePath;

    @Column(name = "report_file_size")
    private Long reportFileSize;

    @Column(name = "regulatory_template_used", length = 255)
    private String regulatoryTemplateUsed;

    @Column(name = "country", length = 2)
    private String country;

    @Column(name = "include_findings")
    private Boolean includeFindings;

    @Column(name = "include_recommendations")
    private Boolean includeRecommendations;

    @Column(name = "include_cost_analysis")
    private Boolean includeCostAnalysis;

    @Column(name = "include_charts")
    private Boolean includeCharts;

    @Enumerated(EnumType.STRING)
    @Column(name = "confidentiality_level")
    private EConfidentialityLevel confidentialityLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_status")
    private EReportState reportStatus;

    @Column(name = "submitted_at")
    private Instant submittedAt;

    @Column(name = "submitted_to", length = 255)
    private String submittedTo;

    @Column(name = "version")
    private Integer version;

    @Column(name = "previous_version_path", length = 1000)
    private String previousVersionPath;

    @Column(name = "notes", length = 1000)
    private String notes;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "audit_id", nullable = false)
    private EnergyAuditEntity audit;
}
