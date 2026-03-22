package io.fluxverde.incoming.rest.visualization;

import io.fluxverde.domain.visualization.ConsumptionTrendService;
import io.fluxverde.rest.api.ConsumptionTrendApi;
import io.fluxverde.rest.model.ConsumptionTrendModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConsumptionTrendApiController implements ConsumptionTrendApi {
    private final ConsumptionTrendService service;
    private final ConsumptionTrendApiMapper mapper;

    @Override
    public ResponseEntity<ConsumptionTrendModel> createConsumptionTrend(ConsumptionTrendModel consumptionTrendApi) {
        ConsumptionTrendModel created = mapper.toApi(service.create(mapper.toDomain(consumptionTrendApi)));
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteConsumptionTrend(Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<ConsumptionTrendModel> getConsumptionTrendById(Long id) {
        return service.findById(id).map(mapper::toApi).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<ConsumptionTrendModel>> listConsumptionTrends() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toApi).toList());
    }

    @Override
    public ResponseEntity<ConsumptionTrendModel> updateConsumptionTrend(Long id, ConsumptionTrendModel consumptionTrendApi) {
        return service.update(id, mapper.toDomain(consumptionTrendApi))
            .map(mapper::toApi)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
