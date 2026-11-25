package dev.mevi.click.contracts.database;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.mevi.click.contracts.ContractsPlugin;

public class MongoDatabase {
    private MongoClient mongoClient;
    private com.mongodb.client.MongoDatabase database;

    public MongoDatabase(ContractsPlugin contractsPlugin) {
        MongoClient client = MongoClients.create(new ConnectionString(contractsPlugin.getConfig().getString("database.mongo.uri")));
        database = client.getDatabase(contractsPlugin.getConfig().getString("database.mongo.database"));
    }

    public com.mongodb.client.MongoDatabase getDatabase() {
        return database;
    }
}
