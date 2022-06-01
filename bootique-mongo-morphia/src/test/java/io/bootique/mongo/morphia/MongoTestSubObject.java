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
