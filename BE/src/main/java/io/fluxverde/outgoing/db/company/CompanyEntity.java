package io.fluxverde.outgoing.db.company;

import io.fluxverde.domain.company.audit.EAuditMethodology;
import io.fluxverde.domain.company.ECompanyStatus;
import io.fluxverde.domain.regulatory.ERegulatoryObligation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import io.fluxverde.outgoing.db.audit.EnergyBenchmarkEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "company")
public class CompanyEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    @Column(name = "company_name", nullable = false, unique = true)
    private String companyName;

    @NotBlank
    @Column(name = "registration_number", nullable = false, unique = true)
    private String registrationNumber;

    @NotBlank
    @Size(min = 2, max = 2)
    @Column(nullable = false, length = 2)
    private String country;

    @Size(max = 255)
    @Column(name = "legal_representative")
    private String legalRepresentative;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    @Column(name = "contact_email", nullable = false)
    private String contactEmail;

    @Size(max = 20)
    @Column(name = "contact_phone")
    private String contactPhone;

    @Size(max = 128)
    @Column
    private String industry;

    @Min(1)
    @Column(name = "employee_count")
    private Integer employeeCount;

    @Column(name = "annual_revenue_meur", precision = 19, scale = 4)
    private BigDecimal annualRevenueMeur;

    @Column(name = "total_energy_tj_per_year", precision = 19, scale = 4)
    private BigDecimal totalEnergyTjPerYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "regulatory_obligation")
    private ERegulatoryObligation regulatoryObligation;

    @Column(name = "next_mandatory_audit_date")
    private LocalDate nextMandatoryAuditDate;

    @Column(name = "last_audit_date")
    private LocalDate lastAuditDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "audit_methodology")
    private EAuditMethodology auditMethodology;

    @Column
    private Instant createdAt;

    @Column
    private Instant updatedAt;

    @Enumerated(EnumType.STRING)
    @Column
    private ECompanyStatus status;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SiteEntity> sites = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyUserEntity> users = new ArrayList<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnergyBenchmarkEntity> benchmarks = new ArrayList<>();
}