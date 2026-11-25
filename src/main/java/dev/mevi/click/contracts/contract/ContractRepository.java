package dev.mevi.click.contracts.contract;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ContractRepository {
    CompletableFuture<Contract> get(UUID uuid);
    CompletableFuture<List<Contract>> getAll();
    CompletableFuture<Contract> save(Contract contract);
}
