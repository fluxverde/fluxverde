package io.fluxverde.incoming.rest.report;

import io.fluxverde.domain.company.audit.ExportLog;
import io.fluxverde.rest.model.ExportLogModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExportLogApiMapper {
    ExportLog toDomain(ExportLogModel api);

    ExportLogModel toApi(ExportLog domain);
}

