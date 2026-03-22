package io.fluxverde.outgoing.db.visualization;

import io.fluxverde.domain.visualization.EAggregationPeriod;
import io.fluxverde.domain.visualization.EConsumptionTrendDirection;
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
@Table
public class ConsumptionTrendEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate trendDate;

    @Enumerated(EnumType.STRING)
    @Column
    private EAggregationPeriod aggregationPeriod;

    @Column
    private BigDecimal energyConsumptionkWh;

    @Column
    private BigDecimal energyConsumptionTJ;

    @Column
    private BigDecimal specificEnergyUsekWhM2;

    @Column
    private BigDecimal costPerUnit;

    @Column
    private BigDecimal comparedToPreviousPeriod;

    @Enumerated(EnumType.STRING)
    @Column
    private EConsumptionTrendDirection trend;

    @Column
    private BigDecimal ambientTemperatureC;

    @Column
    private Boolean isClimateAdjusted;

    @Column
    private Instant createdAt;

    @Column
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn
    private io.fluxverde.outgoing.db.company.SiteEntity site;

    @ManyToOne
    @JoinColumn
    private io.fluxverde.outgoing.db.company.MeterEntity meter;
}
