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


import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

import java.util.List;
import java.util.StringJoiner;

@Entity
class MongoTestObject {

    @Id
    String id;
    String string;
    Boolean bool;
    List<MongoTestSubObject> subObjects;

    public String getId() {
        return id;
    }

    public MongoTestObject id(String id) {
        this.id = id;
        return this;
    }

    public String getString() {
        return string;
    }

    public MongoTestObject string(String string) {
        this.string = string;
        return this;
    }

    public Boolean getBool() {
        return bool;
    }

    public MongoTestObject bool(Boolean bool) {
        this.bool = bool;
        return this;
    }

    public List<MongoTestSubObject> getSubObjects() {
        return subObjects;
    }

    public MongoTestObject subObjects(List<MongoTestSubObject> subObjects) {
        this.subObjects = subObjects;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MongoTestObject.class.getSimpleName() + "[", "]")
            .add("id='" + id + "'")
            .add("string='" + string + "'")
            .add("bool=" + bool)
            .add("subObjects=" + subObjects)
            .toString();
    }
}

