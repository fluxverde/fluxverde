package io.fluxverde.incoming.rest.audit;

import io.fluxverde.domain.company.audit.EnergyAudit;
import io.fluxverde.rest.model.EnergyAuditApi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnergyAuditApiMapper {
    EnergyAudit toDomain(EnergyAuditApi api);

    EnergyAuditApi toApi(EnergyAudit domain);
}

