package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.CompanyUserService;
import io.fluxverde.rest.api.CompanyUserApi;
import io.fluxverde.rest.model.CompanyUserModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompanyUserApiController implements CompanyUserApi {
    private final CompanyUserService service;
    private final CompanyUserApiMapper mapper;

    @Override
    public ResponseEntity<CompanyUserModel> createCompanyUser(CompanyUserModel companyUserApi) {
        CompanyUserModel created = mapper.toApi(service.create(mapper.toDomain(companyUserApi)));
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteCompanyUser(Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<CompanyUserModel> getCompanyUserById(Long id) {
        return service.findById(id).map(mapper::toApi).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<CompanyUserModel>> listCompanyUsers() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toApi).toList());
    }

    @Override
    public ResponseEntity<CompanyUserModel> updateCompanyUser(Long id, CompanyUserModel companyUserApi) {
        return service.update(id, mapper.toDomain(companyUserApi))
            .map(mapper::toApi)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
