package io.fluxverde.incoming.rest.regulatory;

import io.fluxverde.domain.regulatory.RegulatoryTemplateService;
import io.fluxverde.rest.api.RegulatoryTemplateApi;
import io.fluxverde.rest.model.RegulatoryTemplateModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegulatoryTemplateApiController implements RegulatoryTemplateApi {
    private final RegulatoryTemplateService service;
    private final RegulatoryTemplateApiMapper mapper;

    @Override
    public ResponseEntity<RegulatoryTemplateModel> createRegulatoryTemplate(RegulatoryTemplateModel regulatoryTemplateApi) {
        RegulatoryTemplateModel created = mapper.toApi(service.create(mapper.toDomain(regulatoryTemplateApi)));
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteRegulatoryTemplate(Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<RegulatoryTemplateModel> getRegulatoryTemplateById(Long id) {
        return service.findById(id).map(mapper::toApi).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<RegulatoryTemplateModel>> listRegulatoryTemplates() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toApi).toList());
    }

    @Override
    public ResponseEntity<RegulatoryTemplateModel> updateRegulatoryTemplate(Long id, RegulatoryTemplateModel regulatoryTemplateApi) {
        return service.update(id, mapper.toDomain(regulatoryTemplateApi))
            .map(mapper::toApi)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
