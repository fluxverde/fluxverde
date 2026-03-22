package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.Site;
import io.fluxverde.rest.model.SiteModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SiteApiMapper {
    Site toDomain(SiteModel api);

    SiteModel toApi(Site domain);
}

