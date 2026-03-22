package io.fluxverde.outgoing.db.company;

import io.fluxverde.domain.company.Site;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SiteMapper {
    Site toDomain(SiteEntity entity);

    SiteEntity toEntity(Site domain);
}
