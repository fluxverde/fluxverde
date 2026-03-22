package io.fluxverde.outgoing.db.company;

import io.fluxverde.domain.company.EUserRoles;
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
public class CompanyUserEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String userEmail;

    @NotNull
    @Column
    private String firstName;

    @NotNull
    @Column
    private String lastName;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column
    private EUserRoles userRole;

    @Column(length = 20)
    private String phoneNumber;

    @Column
    private Boolean isActive;

    @Column
    private Instant lastLoginAt;

    @Column
    private Instant createdAt;

    @Column
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn
    private CompanyEntity company;
}
