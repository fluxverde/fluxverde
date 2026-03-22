package io.fluxverde.incoming.rest.regulatory;

import io.fluxverde.domain.regulatory.CalculationRuleService;
import io.fluxverde.rest.api.CalculationRuleApi;
import io.fluxverde.rest.model.CalculationRuleModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CalculationRuleApiController implements CalculationRuleApi {
    private final CalculationRuleService service;
    private final CalculationRuleApiMapper mapper;

    @Override
    public ResponseEntity<CalculationRuleModel> createCalculationRule(CalculationRuleModel calculationRuleApi) {
        CalculationRuleModel created = mapper.toApi(service.create(mapper.toDomain(calculationRuleApi)));
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteCalculationRule(Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<CalculationRuleModel> getCalculationRuleById(Long id) {
        return service.findById(id).map(mapper::toApi).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<CalculationRuleModel>> listCalculationRules() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toApi).toList());
    }

    @Override
    public ResponseEntity<CalculationRuleModel> updateCalculationRule(Long id, CalculationRuleModel calculationRuleApi) {
        return service.update(id, mapper.toDomain(calculationRuleApi))
            .map(mapper::toApi)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
