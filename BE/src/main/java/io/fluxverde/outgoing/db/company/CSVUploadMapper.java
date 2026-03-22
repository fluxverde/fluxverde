package io.fluxverde.outgoing.db.company;

import io.fluxverde.domain.company.CSVUpload;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CSVUploadMapper {
    CSVUpload toDomain(CSVUploadEntity entity);

    CSVUploadEntity toEntity(CSVUpload domain);
}
