package io.fluxverde.domain.company.audit;

import io.fluxverde.outgoing.db.audit.AuditChecklistEntity;
import io.fluxverde.outgoing.db.audit.AuditChecklistEntityRepository;
import io.fluxverde.outgoing.db.audit.AuditChecklistMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuditChecklistService {
    private final AuditChecklistEntityRepository repository;
    private final AuditChecklistMapper mapper;

    @Transactional(readOnly = true)
    public List<AuditChecklist> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public Optional<AuditChecklist> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    public AuditChecklist create(AuditChecklist domain) {
        AuditChecklistEntity entity = mapper.toEntity(domain);
        entity.setId(null);
        return mapper.toDomain(repository.save(entity));
    }

    public Optional<AuditChecklist> update(Long id, AuditChecklist domain) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        AuditChecklistEntity entity = mapper.toEntity(domain);
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

