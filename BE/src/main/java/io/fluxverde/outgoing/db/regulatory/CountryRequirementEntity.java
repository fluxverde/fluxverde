package io.fluxverde.outgoing.db.regulatory;

import io.fluxverde.domain.regulatory.ERegulatorySource;
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
public class CountryRequirementEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String requirementCode;

    @NotNull
    @Column
    private String requirementTitle;

    @NotNull
    @Column(length = 2)
    private String country;

    @Enumerated(EnumType.STRING)
    @Column
    private ERegulatorySource regulatorySource;

    @Column(length = 500)
    private String sourceReference;

    @NotNull
    @Column(length = 2000)
    private String description;

    @Column(length = 255)
    private String applicableSiteType;

    @Column
    private BigDecimal energyThresholdTJ;

    @Column(length = 2000)
    private String implementationGuidance;

    @Column
    private LocalDate deadline;

    @Column(length = 1000)
    private String referenceDocumentURL;

    @Column(length = 255)
    private String contactAuthority;

    @Column
    private Instant createdAt;

    @Column
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn
    private RegulatoryTemplateEntity template;
}
