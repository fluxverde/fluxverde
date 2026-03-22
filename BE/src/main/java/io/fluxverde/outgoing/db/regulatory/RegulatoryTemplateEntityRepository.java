package io.fluxverde.outgoing.db.regulatory;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegulatoryTemplateEntityRepository extends JpaRepository<RegulatoryTemplateEntity, Long> {
    Optional<RegulatoryTemplateEntity> findByTemplateCode(String templateCode);
}
