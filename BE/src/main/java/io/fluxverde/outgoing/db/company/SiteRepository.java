package io.fluxverde.outgoing.db.company;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteRepository extends JpaRepository<SiteEntity, Long> {
    Optional<SiteEntity> findBySiteCode(String siteCode);
}
