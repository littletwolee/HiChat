package com.client.models;

/**
 * Created by lee on 5/30/16.
 */
public class JsonResult {
    public  JsonResult(){}
    public JsonResult(Object data, String msg, String status) {
        this.Data = data;
        this.Msg = msg;
        this.setStatus(status);
    }
    public Object Data;
    public String Msg;
    public boolean Status;
    public void setStatus(String status) {
        if (status != "" && status != null){
            this.Status = Boolean.parseBoolean(status);
        }
    }
}
