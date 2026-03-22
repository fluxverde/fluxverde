package io.fluxverde.outgoing.db.company;

import io.fluxverde.domain.audit.EAccessLevel;
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
@Table
public class UserAuditAccessEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private EAccessLevel accessLevel;

    @Column
    private Instant grantedAt;

    @Column(length = 255)
    private String grantedBy;

    @Column
    private Instant expiresAt;

    @ManyToOne
    @JoinColumn
    private CompanyUserEntity user;

    @ManyToOne
    @JoinColumn
    private io.fluxverde.outgoing.db.audit.EnergyAuditEntity audit;
}
