package dev.mevi.click.contracts.database;

import dev.mevi.click.contracts.ContractsPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import redis.clients.jedis.JedisPool;

public class RedisDatabase {
    private final JedisPool jedisPool;

    public RedisDatabase(ContractsPlugin plugin) {
        FileConfiguration config = plugin.getConfig();

        jedisPool = new JedisPool(config.getString("database.redis.host"), config.getInt("database.redis.port"));
    }

    public JedisPool getJedisPool() {
         return jedisPool;
    }
}
