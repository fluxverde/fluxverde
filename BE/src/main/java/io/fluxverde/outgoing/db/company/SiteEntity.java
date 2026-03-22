package io.fluxverde.outgoing.db.company;

import io.fluxverde.domain.company.ESiteAuditStatus;
import io.fluxverde.domain.company.ESiteStatus;
import io.fluxverde.domain.company.ESiteType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "site")
public class SiteEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String siteName;

    @NotNull
    @Column(unique = true)
    private String siteCode;

    @NotNull
    @Column
    private String address;

    @NotNull
    @Column
    private String city;

    @NotNull
    @Column
    private String postalCode;

    @NotNull
    @Column(length = 2)
    private String country;

    @Column
    private BigDecimal latitude;

    @Column
    private BigDecimal longitude;

    @Enumerated(EnumType.STRING)
    @Column
    private ESiteType siteType;

    @Column(length = 255)
    private String productionProcess;

    @Column
    private Integer totalAreaM2;

    @Column
    private BigDecimal estimatedAnnualConsumptionTJ;

    @Column
    private BigDecimal estimatedAnnualConsumptionkWh;

    @Column
    private LocalDate lastAuditDate;

    @Column
    private LocalDate nextAuditDate;

    @Enumerated(EnumType.STRING)
    @Column
    private ESiteAuditStatus auditStatus;

    @Column
    private Instant createdAt;

    @Column
    private Instant updatedAt;

    @Enumerated(EnumType.STRING)
    @Column
    private ESiteStatus status;
}
