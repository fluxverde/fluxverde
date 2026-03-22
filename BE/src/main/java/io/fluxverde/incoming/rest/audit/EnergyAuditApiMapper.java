package io.fluxverde.incoming.rest.audit;

import io.fluxverde.domain.company.audit.EnergyAudit;
import io.fluxverde.rest.model.EnergyAuditModel;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EnergyAuditApiMapper {
    EnergyAudit toDomain(EnergyAuditModel api);

    EnergyAuditModel toApi(EnergyAudit domain);
}

