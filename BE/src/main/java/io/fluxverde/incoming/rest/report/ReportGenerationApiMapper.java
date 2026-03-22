package io.fluxverde.incoming.rest.report;

import io.fluxverde.domain.company.audit.ReportGeneration;
import io.fluxverde.rest.model.ReportGenerationModel;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReportGenerationApiMapper {
    ReportGeneration toDomain(ReportGenerationModel api);

    ReportGenerationModel toApi(ReportGeneration domain);
}

