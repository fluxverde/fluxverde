package io.fluxverde.domain.company;

import io.fluxverde.outgoing.db.company.CSVUploadEntity;
import io.fluxverde.outgoing.db.company.CSVUploadEntityRepository;
import io.fluxverde.outgoing.db.company.CSVUploadMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CSVUploadService {
    private final CSVUploadEntityRepository repository;
    private final CSVUploadMapper mapper;

    @Transactional(readOnly = true)
    public List<CSVUpload> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public Optional<CSVUpload> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    public CSVUpload create(CSVUpload domain) {
        CSVUploadEntity entity = mapper.toEntity(domain);
        entity.setId(null);
        return mapper.toDomain(repository.save(entity));
    }

    public Optional<CSVUpload> update(Long id, CSVUpload domain) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        CSVUploadEntity entity = mapper.toEntity(domain);
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

