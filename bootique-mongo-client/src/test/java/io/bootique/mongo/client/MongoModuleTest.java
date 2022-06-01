package io.bootique.mongo.client;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.bootique.BQRuntime;
import io.bootique.Bootique;
import io.bootique.junit5.BQApp;
import io.bootique.junit5.BQTest;
import io.bootique.junit5.BQTestTool;
import io.bootique.mongo.junit5.MongoTester;
import org.bson.Document;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@BQTest
class MongoModuleTest  {

    @BQTestTool
    protected static MongoTester mongoTester = MongoTester.create();

    @BQApp(skipRun = true)
    static final BQRuntime app = Bootique.app()
        .module(new MongoModule())
        .module(binder -> binder.bind(MongoClient.class).toInstance(mongoTester.createMongoClient()))
        .autoLoadModules()
        .createRuntime();

    @Test
    void testMongoDBConnection() {
        MongoClient client = app.getInstance(MongoClient.class);
        MongoDatabase db = client.getDatabase("test");

        MongoCollection<Document> collection = db.getCollection("test");
        Document doc = new Document("name", "MongoDB")
            .append("type", "database")
            .append("count", 1)
            .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
            .append("info", new Document("x", 203).append("y", 102));

        assertDoesNotThrow(() -> collection.insertOne(doc));
    }
}