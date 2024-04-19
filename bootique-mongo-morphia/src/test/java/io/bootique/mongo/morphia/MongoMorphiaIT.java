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
package io.bootique.mongo.morphia;

import com.mongodb.client.MongoClient;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.query.filters.Filters;
import io.bootique.BQRuntime;
import io.bootique.Bootique;
import io.bootique.junit5.BQApp;
import io.bootique.junit5.BQTest;
import io.bootique.junit5.BQTestTool;
import io.bootique.mongo.junit5.MongoTester;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

@BQTest
class MongoMorphiaIT {

    private static final String DB_NAME = "test";

    @BQTestTool
    static final MongoTester mongoTester = MongoTester.create();

    @BQApp(skipRun = true)
    static final BQRuntime app = Bootique.app()
            .autoLoadModules()
            .module(mongoTester.moduleWithTestMongoClient())
            .createRuntime();

    @Test
    void testMorphia() {
        Datastore datastore = getDatastore();
        MongoTestObject testObjectOriginal = new MongoTestObject().bool(true).string("someString");

        MongoTestObject testObjectSaved = datastore.save(testObjectOriginal);

        String id = testObjectSaved.getId();
        Assertions.assertNotNull(id);
        Assertions.assertSame(testObjectSaved, testObjectOriginal);

        MongoTestObject testObjectFromDb = datastore.find(MongoTestObject.class)
                .filter(Filters.eq("id", id))
                .first();
        Assertions.assertNotNull(testObjectFromDb);

        datastore.delete(testObjectFromDb);
        MongoTestObject testObjectFromDb2 = datastore.find(MongoTestObject.class)
                .filter(Filters.eq("id", id))
                .first();
        Assertions.assertNull(testObjectFromDb2);
    }


    @Test
    void testMorphiaWithSubObjects() {
        Datastore datastore = getDatastore();
        MongoTestObject testObjectOriginal = new MongoTestObject()
                .bool(true)
                .string("someString")
                .subObjects(List.of(
                        new MongoTestSubObject().name("name").value(123).enumeration(MongoTestEnum.ONE),
                        new MongoTestSubObject().name("name1").value(1233243)
                ));

        MongoTestObject testObjectSaved = datastore.save(testObjectOriginal);

        String id = testObjectSaved.getId();
        Assertions.assertNotNull(id);
        Assertions.assertSame(testObjectSaved, testObjectOriginal);

        MongoTestObject testObjectFromDb = datastore.find(MongoTestObject.class)
                .filter(Filters.eq("id", id))
                .first();
        Assertions.assertNotNull(testObjectFromDb);

        datastore.find(MongoTestObject.class)
                .filter(Filters.eq("id", id))
                .delete();

        MongoTestObject testObjectFromDb2 = datastore.find(MongoTestObject.class)
                .filter(Filters.eq("id", id))
                .first();
        Assertions.assertNull(testObjectFromDb2);
    }


    private Datastore getDatastore() {
        MongoClient client = app.getInstance(MongoClient.class);
        Datastore datastore = Morphia.createDatastore(client, DB_NAME);
        datastore.getMapper().mapPackage(this.getClass().getPackageName());
        datastore.ensureIndexes();
        return datastore;
    }


}

