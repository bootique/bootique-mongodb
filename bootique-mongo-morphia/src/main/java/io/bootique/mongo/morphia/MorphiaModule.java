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
