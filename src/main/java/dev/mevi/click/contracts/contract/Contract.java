package dev.mevi.click.contracts.contract;

import java.time.Instant;
import java.util.UUID;

public record Contract(
        UUID id,
        String templateId,
        int amount,
        Instant expiresAt
) {}
