package dev.mevi.click.contracts.objects;

import java.time.Instant;
import java.util.UUID;

public record Contract(UUID uuid, ContractTemplate template, int amount, Instant time) {

}
