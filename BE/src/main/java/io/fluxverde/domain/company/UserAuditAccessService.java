package io.fluxverde.domain.company;

import io.fluxverde.outgoing.db.company.UserAuditAccessEntity;
import io.fluxverde.outgoing.db.company.UserAuditAccessEntityRepository;
import io.fluxverde.outgoing.db.company.UserAuditAccessMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAuditAccessService {
    private final UserAuditAccessEntityRepository repository;
    private final UserAuditAccessMapper mapper;

    @Transactional(readOnly = true)
    public List<UserAuditAccess> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public Optional<UserAuditAccess> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    public UserAuditAccess create(UserAuditAccess domain) {
        UserAuditAccessEntity entity = mapper.toEntity(domain);
        entity.setId(null);
        return mapper.toDomain(repository.save(entity));
    }

    public Optional<UserAuditAccess> update(Long id, UserAuditAccess domain) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        UserAuditAccessEntity entity = mapper.toEntity(domain);
        entity.setId(id);
        return Optional.of(mapper.toDomain(repository.save(entity)));
    }

    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}

