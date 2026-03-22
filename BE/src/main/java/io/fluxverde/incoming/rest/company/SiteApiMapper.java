package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.Site;
import io.fluxverde.rest.model.SiteApi;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SiteApiMapper {
    Site toDomain(SiteApi api);

    SiteApi toApi(Site domain);
}

