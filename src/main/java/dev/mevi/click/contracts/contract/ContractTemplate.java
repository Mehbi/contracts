package dev.mevi.click.contracts.contract;

import net.kyori.adventure.text.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public record ContractTemplate(
        String id,
        String displayName,
        ContractType type,
        String target,
        int minAmount,
        int maxAmount,
        Duration timeLimit
) {
    public Contract generate() {
        int amount = ThreadLocalRandom.current().nextInt(minAmount, maxAmount + 1);
        return new Contract(UUID.randomUUID(), this.id, amount, Instant.now().plus(timeLimit));
    }
}
