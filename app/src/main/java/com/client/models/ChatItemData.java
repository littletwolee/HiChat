package com.client.models;

import com.client.enums.TransferMSG;

import java.util.Date;

/**
 * Created by lee on 6/7/16.
 */
public class ChatItemData {
    public ChatItemData() {
    }

    public ChatItemData(long chatID, String userName, String msg, TransferMSG.TransferType msgSwapType, Date msgDate,
                        TransferMSG.SendStatus msgStatus, byte[] pic) {
        this.ChatID = chatID;
        this.UserName = userName;
        this.Msg = msg;
        this.MsgSwapType = msgSwapType;
        this.MsgDate = msgDate;
        this.MsgStatus = msgStatus;
        this.Pic = pic;
    }
    public long ChatID;
    public String UserName;
    public String Msg;
    public TransferMSG.TransferType MsgSwapType;
    public Date MsgDate;
    public TransferMSG.SendStatus MsgStatus;
    public byte[] Pic;
}