package io.bootique.mongo.morphia;

public class MorphiaConfig {

    private String dbname;

    public String getDbname() {
        return dbname;
    }

    public MorphiaConfig dbname(String dbname) {
        this.dbname = dbname;
        return this;
    }
}
