package io.fluxverde.outgoing.db.audit;

import io.fluxverde.domain.audit.EFundingAvailable;
import io.fluxverde.domain.audit.EImplementationTimeline;
import io.fluxverde.domain.audit.EAuditFindingCategory;
import io.fluxverde.domain.audit.EAuditFindingFeasibility;
import io.fluxverde.domain.company.audit.EAuditFindingPriority;
import io.fluxverde.domain.company.audit.EAuditFindingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.math.BigDecimal;
import io.fluxverde.outgoing.db.company.MeterEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class AuditFindingEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String findingCode;

    @NotNull
    @Column
    private String findingTitle;

    @Enumerated(EnumType.STRING)
    @Column
    private EAuditFindingCategory category;

    @NotNull
    @Column(length = 2000)
    private String description;

    @Column(length = 500)
    private String currentStatus;

    @NotNull
    @Column(length = 2000)
    private String recommendedAction;

    @Column(length = 1000)
    private String technicalParameters;

    @Column
    private BigDecimal estimatedAnnualEnergySavingsTJ;

    @Column
    private BigDecimal estimatedAnnualEnergySavingskWh;

    @Column
    private BigDecimal estimatedAnnualCostSavingsEUR;

    @Column
    private BigDecimal estimatedImplementationCostEUR;

    @Column
    private Double paybackPeriodYears;

    @Column
    private Double estimatedROIPercent;

    @Enumerated(EnumType.STRING)
    @Column
    private EAuditFindingPriority priority;

    @Enumerated(EnumType.STRING)
    @Column
    private EAuditFindingFeasibility feasibility;

    @Enumerated(EnumType.STRING)
    @Column
    private EImplementationTimeline implementationTimeline;

    @Enumerated(EnumType.STRING)
    @Column
    private EFundingAvailable fundingAvailable;

    @Column(length = 500)
    private String fundingSource;

    @Enumerated(EnumType.STRING)
    @Column
    private EAuditFindingStatus status;

    @Column(length = 1000)
    private String evidencePhotoPath;

    @Column(length = 1000)
    private String referenceMaterial;

    @Column
    private Instant createdAt;

    @Column
    private Instant updatedAt;

    @Column
    private Integer affectedMeterCount;

    @ManyToOne
    @JoinColumn
    private EnergyAuditEntity audit;

    @ManyToOne
    @JoinColumn
    private MeterEntity primaryMeter;

    @ManyToOne
    @JoinColumn
    private io.fluxverde.outgoing.db.company.SiteEntity site;

    @ManyToMany
    @JoinTable
    private java.util.Set<MeterEntity> affectedMeters;
}
