package io.fluxverde.outgoing.db.audit;

import io.fluxverde.domain.audit.EAuditChecklistCategory;
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
public class AuditChecklistEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String checklistItemCode;

    @NotNull
    @Column
    private String checklistItemTitle;

    @Enumerated(EnumType.STRING)
    @Column
    private EAuditChecklistCategory category;

    @Column
    private Boolean isMandatory;

    @Column(length = 255)
    private String regulatoryReference;

    @Column(length = 1000)
    private String description;

    @Column(length = 2000)
    private String guidance;

    @Column(length = 500)
    private String expectedDocumentation;

    @Column
    private Boolean isCompleted;

    @Column(length = 255)
    private String completedBy;

    @Column
    private LocalDate completedDate;

    @Column(length = 1000)
    private String comments;

    @Column
    private Instant createdAt;

    @Column
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn
    private EnergyAuditEntity audit;
}
