package io.fluxverde.domain.company.audit;

import io.fluxverde.outgoing.db.audit.AuditFindingEntity;
import io.fluxverde.outgoing.db.audit.AuditFindingEntityRepository;
import io.fluxverde.outgoing.db.audit.AuditFindingMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuditFindingService {
    private final AuditFindingEntityRepository repository;
    private final AuditFindingMapper mapper;

    @Transactional(readOnly = true)
    public List<AuditFinding> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public Optional<AuditFinding> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    public AuditFinding create(AuditFinding domain) {
        AuditFindingEntity entity = mapper.toEntity(domain);
        entity.setId(null);
        return mapper.toDomain(repository.save(entity));
    }

    public Optional<AuditFinding> update(Long id, AuditFinding domain) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        AuditFindingEntity entity = mapper.toEntity(domain);
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

