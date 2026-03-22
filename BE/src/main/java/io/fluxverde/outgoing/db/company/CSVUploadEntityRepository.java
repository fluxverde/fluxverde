package io.fluxverde.outgoing.db.company;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CSVUploadEntityRepository extends JpaRepository<CSVUploadEntity, Long> {
    List<CSVUploadEntity> findByFileName(String fileName);
}
