package io.fluxverde.domain.company.audit;

import io.fluxverde.outgoing.db.report.ReportGenerationEntity;
import io.fluxverde.outgoing.db.report.ReportGenerationEntityRepository;
import io.fluxverde.outgoing.db.report.ReportGenerationMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportGenerationService {
    private final ReportGenerationEntityRepository repository;
    private final ReportGenerationMapper mapper;

    @Transactional(readOnly = true)
    public List<ReportGeneration> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public Optional<ReportGeneration> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    public ReportGeneration create(ReportGeneration domain) {
        ReportGenerationEntity entity = mapper.toEntity(domain);
        entity.setId(null);
        return mapper.toDomain(repository.save(entity));
    }

    public Optional<ReportGeneration> update(Long id, ReportGeneration domain) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        ReportGenerationEntity entity = mapper.toEntity(domain);
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

