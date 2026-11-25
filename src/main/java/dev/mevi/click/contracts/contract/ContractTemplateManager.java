package dev.mevi.click.contracts.contract;

import dev.mevi.click.contracts.ContractsPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class ContractTemplateManager {
    private Map<String, ContractTemplate> templates;

    public ContractTemplateManager (ContractsPlugin plugin, ContractConfigLoader loader) {
        load(plugin, loader);
    }

    public ContractTemplate getTemplate(String identifier) {
        return templates.get(identifier);
    }
    public void load(ContractsPlugin plugin, ContractConfigLoader loader) {
        File file = new File(plugin.getDataFolder(), "templates.yml");

        this.templates = loader.load(YamlConfiguration.loadConfiguration(file));
        plugin.getComponentLogger().info(Component.text("Templates has been loaded", NamedTextColor.GREEN));
    }
}
