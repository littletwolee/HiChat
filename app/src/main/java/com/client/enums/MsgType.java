package com.client.enums;

import android.graphics.Picture;

/**
 * Created by lee on 6/14/16.
 */
public enum MsgType {
    Text(0), Voice(1), Picture(2), File(3) {

        @Override
        public boolean isRest() {
            return true;
        }
    };

    private int value;

    private MsgType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public boolean isRest() {
        return false;
    }

}
