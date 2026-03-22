package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.CSVUpload;
import io.fluxverde.rest.model.CSVUploadApi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CSVUploadApiMapper {
    CSVUpload toDomain(CSVUploadApi api);

    CSVUploadApi toApi(CSVUpload domain);
}

