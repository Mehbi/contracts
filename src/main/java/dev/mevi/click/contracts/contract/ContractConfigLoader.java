package dev.mevi.click.contracts.contract;

import dev.mevi.click.contracts.ContractsPlugin;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ContractConfigLoader {

    private final ContractsPlugin plugin;

    public ContractConfigLoader(ContractsPlugin plugin) {
        this.plugin = plugin;

        File file = new File(plugin.getDataFolder(), "templates.yml");

        if (!file.exists()) {
            plugin.saveResource("templates.yml", false);
            plugin.getComponentLogger().warn(Component.text("Contracts templates file has been created"));
        }
    }

    public Map<String, ContractTemplate> load(FileConfiguration cfg) {
        Map<String, ContractTemplate> result = new LinkedHashMap<>();
        ConfigurationSection root = cfg.getConfigurationSection("contracts");
        if (root == null) return result;

        for (String id : root.getKeys(false)) {
            try {
                result.put(id, parse(id, root.getConfigurationSection(id)));
            } catch (IllegalArgumentException e) {
                plugin.getComponentLogger().warn(Component.text("Contract '" + id + "' ignored: " + e.getMessage()));
            }
        }
        return result;
    }

    private ContractTemplate parse(String id, ConfigurationSection s) {
        ContractType type = ContractType.valueOf(
                s.getString("type", "").toUpperCase());   // lanza IllegalArgumentException

        int min = s.getInt("amount.min", 1);
        int max = s.getInt("amount.max", min);
        if (min <= 0 || max < min) {
            throw new IllegalArgumentException("amount inválido (min=" + min + ", max=" + max + ")");
        }

        String target = s.getString("target");

        return new ContractTemplate(
                id,
                s.getString("display-name", id),
                type,
                target,
                min, max,
                Duration.ofMinutes(s.getLong("time-limit-minutes", 60))
        );
    }
}
