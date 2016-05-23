package com.client.chat.models;

/**
 * Created by lee on 16-5-23.
 */
public class ChatItem {

    public  ChatItem(){}
    public ChatItem(String chatID, String chatName, String chatType, byte[] pic, String lastMsg, String lastTime) {
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
