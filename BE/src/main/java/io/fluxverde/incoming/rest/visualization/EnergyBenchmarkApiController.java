package io.fluxverde.incoming.rest.visualization;

import io.fluxverde.domain.visualization.EnergyBenchmarkService;
import io.fluxverde.rest.api.EnergyBenchmarkApi;
import io.fluxverde.rest.model.EnergyBenchmarkModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EnergyBenchmarkApiController implements EnergyBenchmarkApi {
    private final EnergyBenchmarkService service;
    private final EnergyBenchmarkApiMapper mapper;

    @Override
    public ResponseEntity<EnergyBenchmarkModel> createEnergyBenchmark(EnergyBenchmarkModel energyBenchmarkApi) {
        EnergyBenchmarkModel created = mapper.toApi(service.create(mapper.toDomain(energyBenchmarkApi)));
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteEnergyBenchmark(Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<EnergyBenchmarkModel> getEnergyBenchmarkById(Long id) {
        return service.findById(id).map(mapper::toApi).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<EnergyBenchmarkModel>> listEnergyBenchmarks() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toApi).toList());
    }

    @Override
    public ResponseEntity<EnergyBenchmarkModel> updateEnergyBenchmark(Long id, EnergyBenchmarkModel energyBenchmarkApi) {
        return service.update(id, mapper.toDomain(energyBenchmarkApi))
            .map(mapper::toApi)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
