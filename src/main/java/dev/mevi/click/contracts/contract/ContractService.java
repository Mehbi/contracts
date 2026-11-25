package dev.mevi.click.contracts.contract;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ContractService {
    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public CompletableFuture<Contract> create(ContractTemplate contractTemplate) {
        return contractRepository.save(contractTemplate.generate());
    }
    public CompletableFuture<List<Contract>> getAll() {
        return contractRepository.getAll();
    }
}
