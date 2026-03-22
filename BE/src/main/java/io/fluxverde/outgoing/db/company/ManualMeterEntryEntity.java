package io.fluxverde.outgoing.db.company;

import io.fluxverde.domain.company.meter.EVerificationState;
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
@Table
public class ManualMeterEntryEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private LocalDate entryDate;

    @NotNull
    @Column
    private BigDecimal meterReadingValue;

    @NotNull
    @Column
    private String unit;

    @NotNull
    @Column
    private String enteredBy;

    @NotNull
    @Column
    private Instant entryTimestamp;

    @Enumerated(EnumType.STRING)
    @Column
    private EVerificationState verificationStatus;

    @Column(length = 255)
    private String verifiedBy;

    @Column
    private Instant verificationTimestamp;

    @Column(length = 1000)
    private String notes;

    @Column(length = 1000)
    private String correctionNotes;

    @Column(length = 255)
    private String sourceDocument;

    @ManyToOne
    @JoinColumn
    private MeterEntity meter;

    @ManyToOne
    @JoinColumn
    private CompanyUserEntity enteredByUser;

    @ManyToOne
    @JoinColumn
    private CompanyUserEntity verifiedByUser;
}
