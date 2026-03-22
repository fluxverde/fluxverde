package io.fluxverde.outgoing.db.audit;

import io.fluxverde.domain.audit.EAuditEvidenceCategory;
import io.fluxverde.domain.audit.EFileType;
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
public class AuditEvidenceEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String evidenceCode;

    @NotNull
    @Column
    private String evidenceTitle;

    @NotNull
    @Column
    private String fileName;

    @Enumerated(EnumType.STRING)
    @Column
    private EFileType fileType;

    @NotNull
    @Column
    private String filePath;

    @Column
    private Long fileSize;

    @Enumerated(EnumType.STRING)
    @Column
    private EAuditEvidenceCategory category;

    @NotNull
    @Column
    private String uploadedBy;

    @NotNull
    @Column
    private Instant uploadedAt;

    @Column(length = 1000)
    private String description;

    @Column
    private Instant createdAt;

    @Column
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn
    private EnergyAuditEntity audit;

    @ManyToOne
    @JoinColumn
    private AuditFindingEntity finding;
}
