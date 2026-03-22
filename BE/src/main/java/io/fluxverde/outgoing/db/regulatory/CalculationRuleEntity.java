package io.fluxverde.outgoing.db.regulatory;

import io.fluxverde.domain.regulatory.ECalculationNormalizationFactor;
import io.fluxverde.domain.regulatory.ECalculationSiteType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class CalculationRuleEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String ruleCode;

    @NotNull
    @Column
    private String ruleName;

    @NotNull
    @Column
    private String country;

    @Enumerated(EnumType.STRING)
    @Column
    private ECalculationSiteType applicableToSiteType;

    @Column(length = 128)
    private String applicableToIndustry;

    @NotNull
    @Column(length = 2000)
    private String formulaDescription;

    @Enumerated(EnumType.STRING)
    @Column
    private ECalculationNormalizationFactor normalizationFactor;

    @Column
    private BigDecimal benchmarkValue;

    @Column(length = 1000)
    private String climateAdjustmentFactors;

    @Column
    private BigDecimal productionVolumeFactor;

    @Column(length = 2000)
    private String calculationFormula;

    @Column
    private Instant createdAt;

    @Column
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn
    private RegulatoryTemplateEntity template;
}
