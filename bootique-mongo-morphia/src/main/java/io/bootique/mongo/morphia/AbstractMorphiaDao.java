package io.bootique.mongo.morphia;

import dev.morphia.Datastore;
import dev.morphia.query.experimental.filters.Filters;

import java.util.Optional;
import java.util.stream.Stream;

public abstract class AbstractMorphiaDao<T> {

    protected static final String DEFAULT_ID_FIELD_NAME = "id";

    protected final Datastore datastore;

    protected AbstractMorphiaDao(Datastore datastore) {
        this.datastore = datastore;
    }

    /** to be overwritten */
    protected String getIdFieldName() {
        return DEFAULT_ID_FIELD_NAME;
    }

    /** @return class of object this dao is using */
    protected abstract Class<T> getEntityClass();

    public Optional<T> getById(String id) {
        var entity = datastore.find(getEntityClass())
            .filter(Filters.eq(getIdFieldName(), id))
            .first();
        return Optional.ofNullable(entity);
    }

    public Stream<T> getAll() {
        return datastore.find(getEntityClass()).stream();
    }

    public T save(T entity) {
        return datastore.save(entity);
    }

    public boolean deleteById(String id) {
        var deleteResult = datastore.find(getEntityClass())
            .filter(Filters.eq(getIdFieldName(), id))
            .delete();
        return deleteResult.getDeletedCount() > 0;
    }

}

