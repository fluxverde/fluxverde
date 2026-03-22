package io.fluxverde.domain.company.audit;

import io.fluxverde.outgoing.db.audit.AuditEvidenceEntity;
import io.fluxverde.outgoing.db.audit.AuditEvidenceEntityRepository;
import io.fluxverde.outgoing.db.audit.AuditEvidenceMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuditEvidenceService {
    private final AuditEvidenceEntityRepository repository;
    private final AuditEvidenceMapper mapper;

    @Transactional(readOnly = true)
    public List<AuditEvidence> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public Optional<AuditEvidence> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    public AuditEvidence create(AuditEvidence domain) {
        AuditEvidenceEntity entity = mapper.toEntity(domain);
        entity.setId(null);
        return mapper.toDomain(repository.save(entity));
    }

    public Optional<AuditEvidence> update(Long id, AuditEvidence domain) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        AuditEvidenceEntity entity = mapper.toEntity(domain);
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

