package io.fluxverde.incoming.rest.audit;

import io.fluxverde.domain.company.audit.AuditChecklist;
import io.fluxverde.rest.model.AuditChecklistModel;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuditChecklistApiMapper {
    AuditChecklist toDomain(AuditChecklistModel api);

    AuditChecklistModel toApi(AuditChecklist domain);
}

