package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.CSVUpload;
import io.fluxverde.rest.model.CSVUploadModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CSVUploadApiMapper {
    CSVUpload toDomain(CSVUploadModel api);

    CSVUploadModel toApi(CSVUpload domain);
}

