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

