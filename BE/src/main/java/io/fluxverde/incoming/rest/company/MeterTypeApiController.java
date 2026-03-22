package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.meter.MeterTypeService;
import io.fluxverde.rest.api.MeterTypeApi;
import io.fluxverde.rest.model.MeterTypeModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MeterTypeApiController implements MeterTypeApi {
    private final MeterTypeService service;
    private final MeterTypeApiMapper mapper;

    @Override
    public ResponseEntity<MeterTypeModel> createMeterType(MeterTypeModel meterTypeApi) {
        MeterTypeModel created = mapper.toApi(service.create(mapper.toDomain(meterTypeApi)));
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteMeterType(Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<MeterTypeModel> getMeterTypeById(Long id) {
        return service.findById(id).map(mapper::toApi).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<MeterTypeModel>> listMeterTypes() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toApi).toList());
    }

    @Override
    public ResponseEntity<MeterTypeModel> updateMeterType(Long id, MeterTypeModel meterTypeApi) {
        return service.update(id, mapper.toDomain(meterTypeApi))
            .map(mapper::toApi)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
