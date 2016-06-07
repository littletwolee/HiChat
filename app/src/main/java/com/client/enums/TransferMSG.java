package com.client.enums;

/**
 * Created by lee on 6/7/16.
 */
public class TransferMSG {
    public static enum TransferType{
        SEND,
        RECEIVE;
    }
    public static enum SendStatus{
        SENDING,
        COMPLETED;
    }
}
