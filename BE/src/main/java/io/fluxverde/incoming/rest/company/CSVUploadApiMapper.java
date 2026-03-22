package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.CSVUpload;
import io.fluxverde.rest.model.CSVUploadModel;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CSVUploadApiMapper {
    CSVUpload toDomain(CSVUploadModel api);

    CSVUploadModel toApi(CSVUpload domain);
}

