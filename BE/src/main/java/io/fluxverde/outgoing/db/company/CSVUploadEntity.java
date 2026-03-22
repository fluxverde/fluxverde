package io.fluxverde.outgoing.db.company;

import io.fluxverde.domain.company.EUploadStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table
public class CSVUploadEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String fileName;

    @Column
    private Long fileSize;

    @NotNull
    @Column
    private String uploadedBy;

    @NotNull
    @Column
    private Instant uploadTimestamp;

    @Column
    private Long totalRecordsImported;

    @Column
    private Long successfulRecords;

    @Column
    private Long failedRecords;

    @Column
    private Long skippedRecords;

    @Enumerated(EnumType.STRING)
    @Column
    private EUploadStatus importStatus;

    @Column(length = 5000)
    private String errorLog;

    @Column
    private Boolean isProcessed;

    @Column
    private Instant processedAt;

    @Column(length = 1000)
    private String notes;

    @ManyToOne
    @JoinColumn
    private SiteEntity site;

    @ManyToOne
    @JoinColumn
    private MeterTypeEntity meterType;

    @ManyToOne
    @JoinColumn
    private CompanyEntity company;
}
