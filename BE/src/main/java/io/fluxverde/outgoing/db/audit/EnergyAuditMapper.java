package io.fluxverde.outgoing.db.audit;

import io.fluxverde.domain.company.audit.EnergyAudit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnergyAuditMapper {
    EnergyAudit toDomain(EnergyAuditEntity entity);

    EnergyAuditEntity toEntity(EnergyAudit domain);
}
