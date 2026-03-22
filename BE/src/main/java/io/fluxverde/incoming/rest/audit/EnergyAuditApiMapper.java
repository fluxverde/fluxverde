package io.fluxverde.incoming.rest.audit;

import io.fluxverde.domain.company.audit.EnergyAudit;
import io.fluxverde.rest.model.EnergyAuditModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnergyAuditApiMapper {
    EnergyAudit toDomain(EnergyAuditModel api);

    EnergyAuditModel toApi(EnergyAudit domain);
}

