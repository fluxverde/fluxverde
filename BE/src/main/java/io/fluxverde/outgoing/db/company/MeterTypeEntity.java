package io.fluxverde.outgoing.db.company;

import io.fluxverde.domain.company.meter.EMeterTypeCategory;
import io.fluxverde.domain.company.meter.EMeterTypeUnit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class MeterTypeEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String typeName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column
    private EMeterTypeUnit unit;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column
    private EMeterTypeCategory category;

    @Column
    private BigDecimal conversionFactorToTJ;

    @Column(length = 1000)
    private String description;
}
