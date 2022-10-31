/*
 * Licensed to ObjectStyle LLC under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ObjectStyle LLC licenses
 * this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
class MongoModuleIT {

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