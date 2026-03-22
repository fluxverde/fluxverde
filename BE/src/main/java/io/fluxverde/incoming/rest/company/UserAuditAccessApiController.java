package io.fluxverde.incoming.rest.company;

import io.fluxverde.domain.company.UserAuditAccessService;
import io.fluxverde.rest.api.UserAuditAccessApi;
import io.fluxverde.rest.model.UserAuditAccessModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserAuditAccessApiController implements UserAuditAccessApi {
    private final UserAuditAccessService service;
    private final UserAuditAccessApiMapper mapper;

    @Override
    public ResponseEntity<UserAuditAccessModel> createUserAuditAccess(UserAuditAccessModel userAuditAccessApi) {
        UserAuditAccessModel created = mapper.toApi(service.create(mapper.toDomain(userAuditAccessApi)));
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteUserAuditAccess(Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<UserAuditAccessModel> getUserAuditAccessById(Long id) {
        return service.findById(id).map(mapper::toApi).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<UserAuditAccessModel>> listUserAuditAccesss() {
        return ResponseEntity.ok(service.findAll().stream().map(mapper::toApi).toList());
    }

    @Override
    public ResponseEntity<UserAuditAccessModel> updateUserAuditAccess(Long id, UserAuditAccessModel userAuditAccessApi) {
        return service.update(id, mapper.toDomain(userAuditAccessApi))
            .map(mapper::toApi)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
