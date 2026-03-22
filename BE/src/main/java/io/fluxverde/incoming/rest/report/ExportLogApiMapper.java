package io.fluxverde.incoming.rest.report;

import io.fluxverde.domain.company.audit.ExportLog;
import io.fluxverde.rest.model.ExportLogApi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExportLogApiMapper {
    ExportLog toDomain(ExportLogApi api);

    ExportLogApi toApi(ExportLog domain);
}

