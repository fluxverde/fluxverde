package io.fluxverde.outgoing.db.report;

import io.fluxverde.domain.company.audit.ReportGeneration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportGenerationMapper {
    ReportGeneration toDomain(ReportGenerationEntity entity);

    ReportGenerationEntity toEntity(ReportGeneration domain);
}
