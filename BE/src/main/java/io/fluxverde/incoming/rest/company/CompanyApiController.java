package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.CompanyService;
import io.fluxverde.rest.api.CompanyApi;
import io.fluxverde.rest.model.CompanyModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompanyApiController implements CompanyApi {
    private final CompanyService service;
    private final CompanyApiMapper mapper;

    @Override
    public ResponseEntity<CompanyModel> createCompany(CompanyModel companyApi) {
        CompanyModel created = mapper.toApi(service.create(mapper.toDomain(companyApi)));
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteCompany(Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<CompanyModel> getCompanyById(Long id) {
        return service.findById(id).map(mapper::toApi).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<CompanyModel>> listCompanys() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toApi).toList());
    }

    @Override
    public ResponseEntity<CompanyModel> updateCompany(Long id, CompanyModel companyApi) {
        return service.update(id, mapper.toDomain(companyApi))
            .map(mapper::toApi)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
