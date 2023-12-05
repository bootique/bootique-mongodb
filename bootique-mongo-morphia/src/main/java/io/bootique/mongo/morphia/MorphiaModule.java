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
import io.bootique.BQModule;
import io.bootique.ModuleCrate;
import io.bootique.config.ConfigurationFactory;
import io.bootique.di.Binder;
import io.bootique.di.Provides;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Set;

public class MorphiaModule implements BQModule {

    private static final String CONFIG_PREFIX = "morphia";

    static final String MORPHIA_PACKAGE_NAMES_DI_NAME = "morphiaPackageNames";

    public static MorphiaModuleExtender extend(Binder binder) {
        return new MorphiaModuleExtender(binder);
    }

    @Override
    public ModuleCrate crate() {
        return ModuleCrate.of(this)
                .description("Integrates Morphia MongoDB framework.")
                .config(CONFIG_PREFIX, MorphiaFactory.class)
                .build();
    }

    @Override
    public void configure(Binder binder) {
    }

    @Provides
    @Singleton
    public Datastore provideMorphiaDatastore(
            ConfigurationFactory configFactory,
            MongoClient client,
            @Named(MORPHIA_PACKAGE_NAMES_DI_NAME) Set<String> morphiaPackageNames
    ) {
        return configFactory
                .config(MorphiaFactory.class, CONFIG_PREFIX)
                .createDatastore(client, morphiaPackageNames);
    }
}
