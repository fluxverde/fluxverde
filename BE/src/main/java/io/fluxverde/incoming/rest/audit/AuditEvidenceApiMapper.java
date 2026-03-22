package io.fluxverde.incoming.rest.audit;

import io.fluxverde.domain.company.audit.AuditEvidence;
import io.fluxverde.rest.model.AuditEvidenceModel;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuditEvidenceApiMapper {
    AuditEvidence toDomain(AuditEvidenceModel api);

    AuditEvidenceModel toApi(AuditEvidence domain);
}

