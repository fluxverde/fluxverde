package io.fluxverde.outgoing.db.company;

import io.fluxverde.domain.company.CSVUpload;
import io.fluxverde.domain.company.meter.MeterReading;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {MeterMapper.class, CompanyMapper.class})
public interface MeterReadingMapper {
    @Mapping(target = "sourceUpload", source = "sourceUpload", qualifiedByName = "csvUploadEntityToCsvUploadSafe")
    MeterReading toDomain(MeterReadingEntity entity);

    @Mapping(target = "sourceUpload", source = "sourceUpload", qualifiedByName = "csvUploadToCsvUploadEntitySafe")
    MeterReadingEntity toEntity(MeterReading domain);

    @Named("csvUploadEntityToCsvUploadSafe")
    @Mapping(target = "company", source = "company", qualifiedByName = "toDomainShallow")
    CSVUpload csvUploadEntityToCsvUploadSafe(CSVUploadEntity entity);

    @Named("csvUploadToCsvUploadEntitySafe")
    @Mapping(target = "company", source = "company", qualifiedByName = "toEntityShallow")
    CSVUploadEntity csvUploadToCsvUploadEntitySafe(CSVUpload domain);
}
