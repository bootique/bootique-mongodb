package io.bootique.mongo.junit5;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.bootique.junit5.BQTestScope;
import io.bootique.junit5.scope.BQAfterScopeCallback;
import io.bootique.junit5.scope.BQBeforeScopeCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

public class MongoTester implements BQBeforeScopeCallback, BQAfterScopeCallback {

    public static MongoTester create() {
        return new MongoTester();
    }

    private MongoDBContainer container;

    private MongoTester() { /* noop */ }

    @Override
    public void beforeScope(BQTestScope bqTestScope, ExtensionContext extensionContext) {
        if (container == null) {
            init();
        }
    }

    @Override
    public void afterScope(BQTestScope bqTestScope, ExtensionContext extensionContext) {
        if (container != null) {
            container.close();
            container = null;
        }
    }

    private void init() {
        container = new MongoDBContainer(DockerImageName.parse("mongo:5.0.6"));
        container.start();
    }

    public String getConnectionUrl() {
        if (container == null) {
            init();
        }

        return "mongodb://" + container.getHost() + ":" + container.getFirstMappedPort();
    }

    public MongoClient createMongoClient() {
        return MongoClients.create(getConnectionUrl());
    }

}
