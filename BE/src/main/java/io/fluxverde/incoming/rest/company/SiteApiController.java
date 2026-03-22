package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.SiteService;
import io.fluxverde.rest.api.SiteApi;
import io.fluxverde.rest.model.SiteModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SiteApiController implements SiteApi {
    private final SiteService service;
    private final SiteApiMapper mapper;

    @Override
    public ResponseEntity<SiteModel> createSite(SiteModel siteApi) {
        SiteModel created = mapper.toApi(service.create(mapper.toDomain(siteApi)));
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteSite(Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<SiteModel> getSiteById(Long id) {
        return service.findById(id).map(mapper::toApi).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<SiteModel>> listSites() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toApi).toList());
    }

    @Override
    public ResponseEntity<SiteModel> updateSite(Long id, SiteModel siteApi) {
        return service.update(id, mapper.toDomain(siteApi))
            .map(mapper::toApi)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
