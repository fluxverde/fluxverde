package io.fluxverde.incoming.rest.report;

import io.fluxverde.domain.company.audit.ReportGeneration;
import io.fluxverde.rest.model.ReportGenerationModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportGenerationApiMapper {
    ReportGeneration toDomain(ReportGenerationModel api);

    ReportGenerationModel toApi(ReportGeneration domain);
}

