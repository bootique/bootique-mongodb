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

import java.util.StringJoiner;

@Entity
class MongoTestSubObject {

    private String name;
    private int value;
    private MongoTestEnum enumeration;

    public String getName() {
        return name;
    }

    public MongoTestSubObject name(String name) {
        this.name = name;
        return this;
    }

    public int getValue() {
        return value;
    }

    public MongoTestSubObject value(int value) {
        this.value = value;
        return this;
    }

    public MongoTestEnum getEnumeration() {
        return enumeration;
    }

    public MongoTestSubObject enumeration(MongoTestEnum enumeration) {
        this.enumeration = enumeration;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MongoTestSubObject.class.getSimpleName() + "[", "]")
            .add("name='" + name + "'")
            .add("value=" + value)
            .add("enumeration=" + enumeration)
            .toString();
    }
}
