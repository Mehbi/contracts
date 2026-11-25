package dev.mevi.click.contracts;

import dev.mevi.click.contracts.contract.ContractConfigLoader;
import dev.mevi.click.contracts.contract.ContractService;
import dev.mevi.click.contracts.contract.ContractTemplate;
import dev.mevi.click.contracts.contract.ContractTemplateManager;
import dev.mevi.click.contracts.contract.impl.ContractMongoRepository;
import dev.mevi.click.contracts.database.MongoDatabase;
import org.bukkit.plugin.java.JavaPlugin;

public final class ContractsPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        MongoDatabase mongoDatabase = new MongoDatabase(this);

        ContractConfigLoader contractConfigLoader = new ContractConfigLoader(this);
        ContractTemplateManager contractTemplateManager = new ContractTemplateManager(this, contractConfigLoader);

        ContractMongoRepository contractMongoRepository = new ContractMongoRepository(mongoDatabase);

        ContractService contractService = new ContractService(contractMongoRepository);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
