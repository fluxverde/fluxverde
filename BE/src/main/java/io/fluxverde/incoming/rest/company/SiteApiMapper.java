package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.Site;
import io.fluxverde.rest.model.SiteModel;
import io.fluxverde.incoming.rest.ApiTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = ApiTimeMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SiteApiMapper {
    Site toDomain(SiteModel api);

    SiteModel toApi(Site domain);
}

