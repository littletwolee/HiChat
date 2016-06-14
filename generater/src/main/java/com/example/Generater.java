package com.example;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.PropertyType;
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
        user.addStringProperty("Username").notNull();
        user.addStringProperty("Password").notNull();
        user.addBooleanProperty("Islogin").notNull();
        user.addDateProperty("Lastlogin").notNull();
    }
    private static void addChatMsg(Schema schema) {
        Entity chatMsg = schema.addEntity("ChatMsg");
        chatMsg.addStringProperty("Fromuser").notNull();
        chatMsg.addStringProperty("Touser").notNull();
        chatMsg.addDateProperty("Msgdate").notNull();
        chatMsg.addIntProperty("Msgtype").notNull();
        chatMsg.addByteArrayProperty("Msgbody").notNull();
    }
}
