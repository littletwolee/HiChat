package com.example;

import java.lang.reflect.Method;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Generater {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1000, "com.client.models");
        for (Method mothon: Generater.class.getDeclaredMethods()) {
            if (mothon.getName().equals("main")){
                continue;
            }
            mothon.invoke(null, schema);
        }
        new DaoGenerator().generateAll(schema, "../HiChat/app/src/main/java");
    }

    private static void addUser(Schema schema) {
        Entity user = schema.addEntity("User");
        user.addIdProperty();
        user.addStringProperty("username").notNull();
        user.addStringProperty("password").notNull();
        user.addBooleanProperty("isLogin").notNull();
        user.addDateProperty("lastlogin").notNull();
    }
}
