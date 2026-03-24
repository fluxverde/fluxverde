package io.fluxverde.outgoing.db.company;

import io.fluxverde.domain.company.CSVUpload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CompanyMapper.class)
public interface CSVUploadMapper {
    @Mapping(target = "company", source = "company", qualifiedByName = "toDomainShallow")
    CSVUpload toDomain(CSVUploadEntity entity);

    @Mapping(target = "company", source = "company", qualifiedByName = "toEntityShallow")
    CSVUploadEntity toEntity(CSVUpload domain);
}
