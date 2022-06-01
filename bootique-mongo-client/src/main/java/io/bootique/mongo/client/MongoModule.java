package io.bootique.mongo.client;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.bootique.BaseModule;
import io.bootique.config.ConfigurationFactory;
import io.bootique.di.Provides;

import javax.inject.Singleton;

public class MongoModule extends BaseModule {

    @SuppressWarnings("unused")
    @Provides
    public MongoConfig provideMongoConfig(ConfigurationFactory configFactory) {
        return config(MongoConfig.class, configFactory);
    }

    @SuppressWarnings("unused")
    @Provides
    @Singleton
    public MongoClient provideMongoClient(MongoConfig config) {
        return MongoClients.create(config.getConnectionString());
    }


}

