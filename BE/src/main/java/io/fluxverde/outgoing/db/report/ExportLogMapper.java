package io.fluxverde.outgoing.db.report;

import io.fluxverde.domain.company.audit.ExportLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExportLogMapper {
    ExportLog toDomain(ExportLogEntity entity);

    ExportLogEntity toEntity(ExportLog domain);
}
