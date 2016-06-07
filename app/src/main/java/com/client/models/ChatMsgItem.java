package com.client.models;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lee on 6/7/16.
 */
public class ChatMsgItem {
    public  ChatMsgItem(){}
    public ChatMsgItem(String chatID, String chatName, String chatType, byte[] pic, String lastMsg, String lastTime) {
        this.ChatID = chatID;
        this.ChatName.setText(chatName);
        this.ChatType = chatType;
        this.setPic(pic);
        this.LastMsg.setText(lastMsg);
        this.LastTime.setText(lastTime);
    }
    public String ChatID;
    public TextView ChatName;
    public String ChatType;
    public ImageView Pic;
    public void setPic(byte[] pic) {
//        if (pic != null) {
//            this.Pic.setImageBitmap(BitmapFactory.decodeByteArray(pic, 0, pic.length));
//        }
        this.Pic.setImageResource(android.R.mipmap.sym_def_app_icon);
    }
    public TextView LastMsg;
    public TextView LastTime;
    public void setLastTime(String lastTime) {
        this.LastTime.setText(lastTime);
    }
}
