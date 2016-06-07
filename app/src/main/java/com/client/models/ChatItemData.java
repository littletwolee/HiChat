package com.client.models;

import android.view.View;

import com.client.enums.TransferMSG;

import java.util.Date;

/**
 * Created by lee on 6/7/16.
 */
public class ChatItemData {
    public ChatItemData() {
    }

    public ChatItemData(String userName, String msg, TransferMSG.TransferType msgType, Date msgDate, TransferMSG.SendStatus msgStatus) {
        this.UserName = userName;
        this.Msg = msg;
        this.MsgType = msgType;
        this.MsgDate = msgDate;
        this.MsgStatus = msgStatus;

    }
    public String UserName;
    public String Msg;
    public TransferMSG.TransferType MsgType;
    public Date MsgDate;
    public TransferMSG.SendStatus MsgStatus;
}