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

import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;

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

