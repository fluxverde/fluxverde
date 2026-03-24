package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.CSVUpload;
import io.fluxverde.domain.company.meter.Meter;
import io.fluxverde.domain.company.meter.MeterReading;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import io.fluxverde.rest.model.CSVUploadModel;
import io.fluxverde.rest.model.MeterModel;
import io.fluxverde.rest.model.MeterReadingModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MeterReadingApiMapper {
    MeterReading toDomain(MeterReadingModel api);

    MeterReadingModel toApi(MeterReading domain);

    default Meter toMeter(MeterModel value) {
        if (value == null) {
            return null;
        }
        return new Meter(value.getId(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    default MeterModel toMeterModel(Meter value) {
        if (value == null) {
            return null;
        }
        MeterModel model = new MeterModel();
        model.setId(value.id());
        return model;
    }

    default CSVUpload toCSVUpload(CSVUploadModel value) {
        if (value == null) {
            return null;
        }
        return new CSVUpload(value.getId(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    default CSVUploadModel toCSVUploadModel(CSVUpload value) {
        if (value == null) {
            return null;
        }
        CSVUploadModel model = new CSVUploadModel();
        model.setId(value.id());
        return model;
    }
}
