package com.client.models;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.client.hichat.R;

import java.util.Date;

/**
 * Created by lee on 6/7/16.
 */
public class ChatMsgReceiveItem {
    public ChatMsgReceiveItem(){}
    public ChatMsgReceiveItem(View view) {
        viewInit(view);
    }
    private void viewInit(View view){
        this.UserName = (TextView) view.findViewById(R.id.tv_user_id);
        this.Msg = (TextView) view.findViewById(R.id.tv_msg);
        this.Pic = (ImageView) view.findViewById(R.id.iv_user_head);
        this.MsgDate = (TextView) view.findViewById(R.id.timestamp);
    }
    private void setData(ChatMsgReceiveItem chatMsgReceiveItem, ChatItemData chatItemData){
        chatMsgReceiveItem.setUserName(chatItemData.UserName);
        chatMsgReceiveItem.setMsg(chatItemData.Msg);
        chatMsgReceiveItem.setPic(chatItemData.Pic);
        chatMsgReceiveItem.setMsgDate(chatItemData.MsgDate);
    }
    public ImageView Pic;
    public TextView UserName;
    public TextView Msg;
    public TextView MsgDate;
    public void setUserName(String msg) {
        this.Msg.setText(msg);
    }
    public void setMsg(String msg) {
        this.Msg.setText(msg);
    }
    public void setMsgDate(Date msgDate) {
        this.MsgDate.setText(msgDate.toString());
    }
    public void setPic(byte[] pic) {
        this.Pic.setImageResource(android.R.mipmap.sym_def_app_icon);
    }
    //
    //internal
    //
    public void setObject(ChatMsgReceiveItem chatMsgReceiveItem, ChatItemData chatItemData){
        chatMsgReceiveItem.setData(chatMsgReceiveItem, chatItemData);
    }
}
