package io.fluxverde.incoming.rest.audit;

import io.fluxverde.domain.company.audit.AuditFinding;
import io.fluxverde.rest.model.AuditFindingModel;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuditFindingApiMapper {
    AuditFinding toDomain(AuditFindingModel api);

    AuditFindingModel toApi(AuditFinding domain);
}

