package io.fluxverde.outgoing.db.company;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class CSVUploadMapperCycleTest {

    @Test
    void toDomain_usesShallowCompanyAndAvoidsRecursion() {
        CompanyMapperImpl companyMapper = new CompanyMapperImpl();
        CSVUploadMapperImpl mapper = new CSVUploadMapperImpl();
        injectCompanyMapper(mapper, companyMapper);

        CompanyEntity company = CompanyEntity.builder()
            .id(15L)
            .companyName("Flux Verde")
            .registrationNumber("REG-15")
            .country("AT")
            .contactEmail("test@fluxverde.io")
            .build();

        CSVUploadEntity upload = CSVUploadEntity.builder()
            .id(3L)
            .fileName("test.csv")
            .uploadedBy("user@fluxverde.io")
            .uploadTimestamp(Instant.now())
            .company(company)
            .build();

        CompanyUserEntity user = CompanyUserEntity.builder()
            .id(1L)
            .userEmail("user@fluxverde.io")
            .firstName("Test")
            .lastName("User")
            .company(company)
            .build();
        company.setUsers(new ArrayList<>());
        company.getUsers().add(user);

        var mapped = assertDoesNotThrow(() -> mapper.toDomain(upload));

        assertNotNull(mapped);
        assertNotNull(mapped.company());
        assertNull(mapped.company().users());
        assertNull(mapped.company().benchmarks());
    }

    private static void injectCompanyMapper(Object target, CompanyMapper companyMapper) {
        try {
            Field field = target.getClass().getDeclaredField("companyMapper");
            field.setAccessible(true);
            field.set(target, companyMapper);
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException("Failed to inject CompanyMapper", ex);
        }
    }
}

