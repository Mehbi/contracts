package dev.mevi.click.contracts.contract.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import dev.mevi.click.contracts.contract.Contract;
import dev.mevi.click.contracts.contract.ContractRepository;
import dev.mevi.click.contracts.database.MongoDatabase;
import org.bson.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

public class ContractMongoRepository implements ContractRepository {

    private static final String COLLECTION = "contracts";

    private final MongoCollection<Document> collection;
    private final Executor executor;

    public ContractMongoRepository(MongoDatabase mongoDatabase) {
        this(mongoDatabase, ForkJoinPool.commonPool());
    }

    public ContractMongoRepository(MongoDatabase mongoDatabase, Executor executor) {
        this.collection = mongoDatabase.getDatabase().getCollection(COLLECTION);
        this.executor = executor;
    }

    @Override
    public CompletableFuture<Contract> get(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            Document doc = collection.find(Filters.eq("_id", uuid.toString())).first();
            return doc == null ? null : fromDocument(doc);
        }, executor);
    }

    @Override
    public CompletableFuture<List<Contract>> getAll() {
        return CompletableFuture.supplyAsync(() ->
                collection.find()
                        .map(this::fromDocument)
                        .into(new ArrayList<>()), executor);
    }

    @Override
    public CompletableFuture<Contract> save(Contract contract) {
        return CompletableFuture.supplyAsync(() -> {
            collection.replaceOne(
                    Filters.eq("_id", contract.id().toString()),
                    toDocument(contract),
                    new ReplaceOptions().upsert(true));
            return contract;
        }, executor);
    }

    private Document toDocument(Contract contract) {
        return new Document("_id", contract.id().toString())
                .append("templateId", contract.templateId())
                .append("amount", contract.amount())
                .append("expiresAt", Date.from(contract.expiresAt()));
    }

    private Contract fromDocument(Document doc) {
        return new Contract(
                UUID.fromString(doc.getString("_id")),
                doc.getString("templateId"),
                doc.getInteger("amount"),
                doc.getDate("expiresAt").toInstant());
    }
}