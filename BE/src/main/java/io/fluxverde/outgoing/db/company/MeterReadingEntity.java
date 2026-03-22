package io.fluxverde.outgoing.db.company;

import io.fluxverde.domain.company.meter.EMeterReadingSource;
import io.fluxverde.domain.company.meter.EMeterReadingState;
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
public class MeterReadingEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private Instant readingTimestamp;

    @NotNull
    @Column
    private BigDecimal readingValue;

    @Enumerated(EnumType.STRING)
    @Column
    private EMeterReadingState readingStatus;

    @Column
    private Integer confidence;

    @Enumerated(EnumType.STRING)
    @Column
    private EMeterReadingSource source;

    @Column(length = 255)
    private String enteredByUser;

    @Column
    private Instant enteredAt;

    @Column
    private Boolean isOutlier;

    @Column(length = 255)
    private String anomalyReason;

    @Column(length = 1000)
    private String notes;

    @Column
    private Long batchId;

    @Column
    private BigDecimal normalizedValue;

    @ManyToOne
    @JoinColumn
    private MeterEntity meter;

    @ManyToOne
    @JoinColumn
    private CSVUploadEntity sourceUpload;
}
