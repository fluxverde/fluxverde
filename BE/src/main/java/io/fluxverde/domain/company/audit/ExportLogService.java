package io.fluxverde.domain.company.audit;

import io.fluxverde.outgoing.db.report.ExportLogEntity;
import io.fluxverde.outgoing.db.report.ExportLogEntityRepository;
import io.fluxverde.outgoing.db.report.ExportLogMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ExportLogService {
    private final ExportLogEntityRepository repository;
    private final ExportLogMapper mapper;

    @Transactional(readOnly = true)
    public List<ExportLog> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public Optional<ExportLog> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    public ExportLog create(ExportLog domain) {
        ExportLogEntity entity = mapper.toEntity(domain);
        entity.setId(null);
        return mapper.toDomain(repository.save(entity));
    }

    public Optional<ExportLog> update(Long id, ExportLog domain) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        ExportLogEntity entity = mapper.toEntity(domain);
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

