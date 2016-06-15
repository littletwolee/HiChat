package com.client.enums;

import android.graphics.Picture;

/**
 * Created by lee on 6/14/16.
 */
public enum MsgType {
    TypeText(0), TypeVoice(1), TypePicture(2), TypeFile(3) {

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
