package io.fluxverde.outgoing.db.audit;

import io.fluxverde.domain.company.audit.EAuditState;
import io.fluxverde.domain.company.audit.EAuditType;
import io.fluxverde.domain.regulatory.ERegulatoryRequirement;
import io.fluxverde.outgoing.db.company.SiteEntity;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "energy_audit")
public class EnergyAuditEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "audit_code", nullable = false, unique = true)
    private String auditCode;

    @Column(name = "audit_start_date", nullable = false)
    private LocalDate auditStartDate;

    @Column(name = "audit_end_date")
    private LocalDate auditEndDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "audit_type")
    private EAuditType auditType;

    @Column(name = "lead_auditor", nullable = false)
    private String leadAuditor;

    @Column(name = "supporting_auditors", length = 1000)
    private String supportingAuditors;

    @Column(name = "reference_start_date", nullable = false)
    private LocalDate referenceStartDate;

    @Column(name = "reference_end_date", nullable = false)
    private LocalDate referenceEndDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "audit_status")
    private EAuditState auditStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "regulatory_requirement")
    private ERegulatoryRequirement regulatoryRequirement;

    @Column(name = "compliance_framework")
    private String complianceFramework;

    @Column(name = "total_energy_consumption_tj")
    private BigDecimal totalEnergyConsumptionTJ;

    @Column(name = "total_energy_consumption_kwh")
    private BigDecimal totalEnergyConsumptionkWh;

    @Column(name = "estimated_annual_savings_tj")
    private BigDecimal estimatedAnnualSavingsTJ;

    @Column(name = "estimated_cost_savings_eur")
    private BigDecimal estimatedCostSavingsEUR;

    @Column(name = "estimated_roi_percent")
    private BigDecimal estimatedROIPercent;

    @Column(name = "report_generated")
    private Boolean reportGenerated;

    @Column(name = "report_generated_date")
    private Instant reportGeneratedDate;

    @Column(name = "report_path", length = 1000)
    private String reportPath;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private SiteEntity site;
}
