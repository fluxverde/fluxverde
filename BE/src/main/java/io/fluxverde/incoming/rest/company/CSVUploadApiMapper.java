package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.CSVUpload;
import io.fluxverde.domain.company.Company;
import io.fluxverde.domain.company.Site;
import io.fluxverde.domain.company.meter.MeterType;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import io.fluxverde.rest.model.CSVUploadModel;
import io.fluxverde.rest.model.CompanyModel;
import io.fluxverde.rest.model.MeterTypeModel;
import io.fluxverde.rest.model.SiteModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CSVUploadApiMapper {
    CSVUpload toDomain(CSVUploadModel api);

    CSVUploadModel toApi(CSVUpload domain);

    default Site toSite(SiteModel value) {
        if (value == null) {
            return null;
        }
        return new Site(value.getId(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    default SiteModel toSiteModel(Site value) {
        if (value == null) {
            return null;
        }
        SiteModel model = new SiteModel();
        model.setId(value.id());
        return model;
    }

    default MeterType toMeterType(MeterTypeModel value) {
        if (value == null) {
            return null;
        }
        return new MeterType(value.getId(), null, null, null, null, null);
    }

    default MeterTypeModel toMeterTypeModel(MeterType value) {
        if (value == null) {
            return null;
        }
        MeterTypeModel model = new MeterTypeModel();
        model.setId(value.id());
        return model;
    }

    default Company toCompany(CompanyModel value) {
        if (value == null) {
            return null;
        }
        return new Company(value.getId(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    default CompanyModel toCompanyModel(Company value) {
        if (value == null) {
            return null;
        }
        CompanyModel model = new CompanyModel();
        model.setId(value.id());
        return model;
    }
}
