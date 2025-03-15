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
import dev.morphia.mapping.Mapper;
import io.bootique.annotation.BQConfig;
import io.bootique.annotation.BQConfigProperty;

import jakarta.inject.Named;
import java.util.Objects;
import java.util.Set;

/**
 * @since 3.0
 */
@BQConfig
public class MorphiaFactory {

    private final MongoClient client;
    private final Set<String> packageNames;

    private String dbname;

    public MorphiaFactory(
            MongoClient client,
            @Named(MorphiaModule.MORPHIA_PACKAGE_NAMES_DI_NAME) Set<String> packageNames) {
        this.client = client;
        this.packageNames = packageNames;
    }

    public Datastore create() {
        Objects.requireNonNull(dbname);
        Datastore datastore = Morphia.createDatastore(client, dbname);

        if (!packageNames.isEmpty()) {
            Mapper mapper = datastore.getMapper();
            packageNames.forEach(mapper::mapPackage);
        }

        datastore.ensureIndexes();
        return datastore;
    }

    @BQConfigProperty
    public MorphiaFactory setDbname(String dbname) {
        this.dbname = dbname;
        return this;
    }
}
