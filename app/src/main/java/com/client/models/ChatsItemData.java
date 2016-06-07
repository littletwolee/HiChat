package com.client.models;

/**
 * Created by lee on 5/24/16.
 */
public class ChatsItemData {
    public  ChatsItemData(){}
    public ChatsItemData(String chatID, String chatName, String chatType, byte[] pic, String lastMsg, String lastTime) {
        this.ChatID = chatID;
        this.ChatName = chatName;
        this.ChatType = chatType;
        this.Pic = pic;
        this.LastMsg = lastMsg;
        this.LastTime = lastTime;
    }
    public String ChatID;
    public String ChatName;
    public String ChatType;
    public byte[] Pic;
    public String LastMsg;
    public String LastTime;
}
