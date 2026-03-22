package io.fluxverde.incoming.rest.report;

import io.fluxverde.domain.company.audit.ExportLog;
import io.fluxverde.rest.model.ExportLogModel;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExportLogApiMapper {
    ExportLog toDomain(ExportLogModel api);

    ExportLogModel toApi(ExportLog domain);
}

