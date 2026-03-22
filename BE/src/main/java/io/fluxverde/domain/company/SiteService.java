package io.fluxverde.domain.company;

import io.fluxverde.outgoing.db.company.SiteEntity;
import io.fluxverde.outgoing.db.company.SiteMapper;
import io.fluxverde.outgoing.db.company.SiteRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SiteService {
    private final SiteRepository repository;
    private final SiteMapper mapper;

    @Transactional(readOnly = true)
    public List<Site> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Transactional(readOnly = true)
    public Optional<Site> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    public Site create(Site domain) {
        SiteEntity entity = mapper.toEntity(domain);
        entity.setId(null);
        return mapper.toDomain(repository.save(entity));
    }

    public Optional<Site> update(Long id, Site domain) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        SiteEntity entity = mapper.toEntity(domain);
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

