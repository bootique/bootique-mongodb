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
package io.bootique.mongo.junit5;

import io.bootique.BQCoreModule;
import io.bootique.di.BQModule;
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

   public BQModule moduleWithTestMongoClient() {
        return b -> BQCoreModule.extend(b).setProperty("bq.mongoclient.connectionString", getConnectionUrl());
   }

}
