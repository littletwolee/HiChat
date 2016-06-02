package com.client.daogenerater;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by lee on 6/2/16.
 */
public class modelgenerater {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1000, "com.client.models");

        addUser(schema);

        new DaoGenerator().generateAll(schema, "./app/src/main/java");
    }

    private static void addUser(Schema schema) {
        Entity user = schema.addEntity("User");
        user.addIdProperty();
        user.addStringProperty("username").notNull();
        user.addStringProperty("password").notNull();
        user.addDateProperty("lastlogin").notNull();
    }
}
