package com.client.models;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.client.hichat.R;

import java.util.Date;

/**
 * Created by lee on 16-6-9.
 */
public class ChatMsgSendItem {
    public ChatMsgSendItem(){}
    public ChatMsgSendItem(View view) {
        viewInit(view);
    }
    private void viewInit(View view){
        this.Sending = (ProgressBar) view.findViewById(R.id.pb_sending);
        this.Msg = (TextView) view.findViewById(R.id.tv_msg);
        this.Pic = (ImageView) view.findViewById(R.id.iv_user_head);
        this.MsgDate = (TextView) view.findViewById(R.id.timestamp);
    }
    private void setData(ChatMsgSendItem chatMsgItem, ChatItemData chatItemData){
        chatMsgItem.isSending(true);
        chatMsgItem.setMsg(chatItemData.Msg);
        chatMsgItem.setPic(chatItemData.Pic);
        chatMsgItem.setMsgDate(chatItemData.MsgDate);
    }
    public ImageView Pic;
    public TextView Msg;
    public TextView MsgDate;
    public ProgressBar Sending;
    public void setMsg(String msg) {
        this.Msg.setText(msg);
    }
    public void setMsgDate(Date msgDate) {
        this.MsgDate.setText(msgDate.toString());
    }
    public void setPic(byte[] pic) {
        this.Pic.setImageResource(android.R.mipmap.sym_def_app_icon);
    }
    public void isSending(boolean issending) {
        if(issending){
            this.Sending.setVisibility(View.GONE);
        }
    }
    //
    //internal
    //
    public void setObject(ChatMsgSendItem chatMsgItem, ChatItemData chatItemData){
        chatMsgItem.setData(chatMsgItem, chatItemData);
    }
}
