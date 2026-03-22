package io.fluxverde.outgoing.db.audit;

import io.fluxverde.outgoing.db.company.CompanyEntity;
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
@Table(name = "energy_benchmark")
public class EnergyBenchmarkEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "benchmark_code", nullable = false, unique = true)
    private String benchmarkCode;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "industry", nullable = false)
    private String industry;

    @Column(name = "site_type", length = 128)
    private String siteType;

    @Column(name = "typical_energy_use_kwh_m2")
    private java.math.BigDecimal typicalEnergyUsekWhM2;

    @Column(name = "median_energy_use_kwh_m2")
    private java.math.BigDecimal medianEnergyUsekWhM2;

    @Column(name = "best_in_class_kwh_m2")
    private java.math.BigDecimal bestInClasskWhM2;

    @Column(name = "source_data_year")
    private Integer sourceDataYear;

    @Column(name = "number_of_facilities_in_sample")
    private Integer numberOfFacilitiesInSample;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "source_reference", length = 255)
    private String sourceReference;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;
}
