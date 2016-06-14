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
        user.addStringProperty("UserName").notNull();
        user.addStringProperty("PassWord").notNull();
        user.addBooleanProperty("IsLogin").notNull();
        user.addDateProperty("LastLogin").notNull();
    }
    private static void addChatMsg(Schema schema) {
        Entity chatMsg = schema.addEntity("ChatMsg");
        chatMsg.addIdProperty();
        chatMsg.addStringProperty("FromUser").notNull();
        chatMsg.addStringProperty("ToUser").notNull();
        chatMsg.addDateProperty("MsgDate").notNull();
        chatMsg.addStringProperty("MsgType").notNull();
        chatMsg.addByteProperty("MsgBody").notNull();
    }
}
