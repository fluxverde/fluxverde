package io.fluxverde.incoming.rest.regulatory;

import io.fluxverde.domain.regulatory.CountryRequirementService;
import io.fluxverde.rest.api.CountryRequirementApi;
import io.fluxverde.rest.model.CountryRequirementModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CountryRequirementApiController implements CountryRequirementApi {
    private final CountryRequirementService service;
    private final CountryRequirementApiMapper mapper;

    @Override
    public ResponseEntity<CountryRequirementModel> createCountryRequirement(CountryRequirementModel countryRequirementApi) {
        CountryRequirementModel created = mapper.toApi(service.create(mapper.toDomain(countryRequirementApi)));
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteCountryRequirement(Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<CountryRequirementModel> getCountryRequirementById(Long id) {
        return service.findById(id).map(mapper::toApi).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<CountryRequirementModel>> listCountryRequirements() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toApi).toList());
    }

    @Override
    public ResponseEntity<CountryRequirementModel> updateCountryRequirement(Long id, CountryRequirementModel countryRequirementApi) {
        return service.update(id, mapper.toDomain(countryRequirementApi))
            .map(mapper::toApi)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
