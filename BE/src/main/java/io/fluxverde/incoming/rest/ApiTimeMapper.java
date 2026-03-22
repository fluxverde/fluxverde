package io.fluxverde.incoming.rest;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Component;

@Component
public class ApiTimeMapper {
    public OffsetDateTime toOffsetDateTime(Instant value) {
        return value == null ? null : value.atOffset(ZoneOffset.UTC);
    }

    public Instant toInstant(OffsetDateTime value) {
        return value == null ? null : value.toInstant();
    }
}

