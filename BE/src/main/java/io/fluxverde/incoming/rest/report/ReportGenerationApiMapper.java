package io.fluxverde.incoming.rest.report;

import io.fluxverde.domain.company.audit.ReportGeneration;
import io.fluxverde.rest.model.ReportGenerationApi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportGenerationApiMapper {
    ReportGeneration toDomain(ReportGenerationApi api);

    ReportGenerationApi toApi(ReportGeneration domain);
}

