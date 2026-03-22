package io.fluxverde.outgoing.db.company;

import io.fluxverde.domain.company.meter.EMeterCategory;
import io.fluxverde.domain.company.meter.EMeterCollectionMethod;
import io.fluxverde.domain.company.meter.EMeterReadingFrequency;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class MeterEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String meterName;

    @NotNull
    @Column(unique = true)
    private String meterCode;

    @Column(length = 128, unique = true)
    private String meterSerialNumber;

    @Column(length = 128)
    private String manufacturer;

    @Column
    private LocalDate installationDate;

    @Column
    private Integer nominalPower;

    @Column(length = 50)
    private String accuracy;

    @Column(length = 255)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column
    private EMeterCategory meterCategory;

    @Enumerated(EnumType.STRING)
    @Column
    private EMeterCollectionMethod collectionMethod;

    @Enumerated(EnumType.STRING)
    @Column
    private EMeterReadingFrequency readingFrequency;

    @Column
    private Boolean isActive;

    @Column
    private Instant createdAt;

    @Column
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn
    private SiteEntity site;

    @ManyToOne
    @JoinColumn
    private MeterTypeEntity meterType;
}
