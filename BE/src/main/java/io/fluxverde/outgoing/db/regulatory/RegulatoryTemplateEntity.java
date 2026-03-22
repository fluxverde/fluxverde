package io.fluxverde.outgoing.db.regulatory;

import io.fluxverde.domain.regulatory.ERegulatoryTemplateStatus;
import io.fluxverde.domain.regulatory.ERegulatoryTemplateType;
import io.fluxverde.domain.regulatory.ESubmissionFormatType;
import io.fluxverde.domain.report.EReportFormatStandard;
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
public class RegulatoryTemplateEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String templateCode;

    @NotNull
    @Column
    private String templateName;

    @NotNull
    @Column(length = 2)
    private String country;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column
    private ERegulatoryTemplateType regulatoryFramework;

    @NotNull
    @Column
    private LocalDate validFromDate;

    @Column
    private LocalDate validUntilDate;

    @Enumerated(EnumType.STRING)
    @Column
    private EReportFormatStandard reportFormat;

    @Column(length = 2000)
    private String requiredSections;

    @Column(length = 2000)
    private String calculationMethodology;

    @Column
    private BigDecimal mandatorySEUThreshold;

    @Column
    private Boolean mandatoryCO2Reporting;

    @Column
    private Boolean mandatoryCostAnalysis;

    @Column
    private Integer submissionDeadlineMonths;

    @Enumerated(EnumType.STRING)
    @Column
    private ESubmissionFormatType submissionFormat;

    @Column(length = 255)
    private String submissionAuthority;

    @Column(length = 1000)
    private String submissionURL;

    @Column
    private Integer recordRetentionYears;

    @Column(length = 5)
    private String reportLanguage;

    @Column(length = 20)
    private String version;

    @Column(length = 1000)
    private String description;

    @Column
    private Instant createdAt;

    @Column
    private Instant updatedAt;

    @Enumerated(EnumType.STRING)
    @Column
    private ERegulatoryTemplateStatus status;
}
