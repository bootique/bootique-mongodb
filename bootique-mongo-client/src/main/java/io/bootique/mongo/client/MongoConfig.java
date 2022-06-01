package io.bootique.mongo.client;

public class MongoConfig {

    private String connectionString;

    public String getConnectionString() {
        return connectionString;
    }

    public MongoConfig connectionString(String connectionString) {
        this.connectionString = connectionString;
        return this;
    }
}
