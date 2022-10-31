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
import io.bootique.BaseModule;
import io.bootique.config.ConfigurationFactory;
import io.bootique.di.BQModule;
import io.bootique.di.Binder;
import io.bootique.di.Provides;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Set;

public class MorphiaModule extends BaseModule implements BQModule {

    public static MorphiaModuleExtender extend(Binder binder) {
        return new MorphiaModuleExtender(binder);
    }


    @SuppressWarnings("unused")
    @Singleton
    @Provides
    public MorphiaConfig provideMorphiaConfig(ConfigurationFactory configFactory) {
        return config(MorphiaConfig.class, configFactory);
    }

    @SuppressWarnings("unused")
    @Provides
    @Singleton
    public Datastore provideMorphiaDatastore(
        MongoClient client,
        MorphiaConfig config,
        @Named("morphiaPackageNames") Set<String> morphiaPackageNames
    ) {
        var datastore = Morphia.createDatastore(client, config.getDbname());

        if (morphiaPackageNames != null) {
            var mapper = datastore.getMapper();
            morphiaPackageNames.forEach(mapper::mapPackage);
        }

        datastore.ensureIndexes();
        return datastore;
    }

}
