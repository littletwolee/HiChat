package com.client.tools;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.client.hichat.R;
import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 6/16/16.
 */
public class AuthHelper {
    private Context context;
    private String code = "SHA-256";
    private int saltnum = 2;
    public AuthHelper(Context context){
        this.context = context;
    }
    public String encryption(String pwd){
        String encryptionPwd = pwd;
        for(int i = 1; i<= saltnum; i++){
            encryptionPwd = getAuthToken(code, encryptionPwd + context.getString(R.string.auth_salt));
        }
        return encryptionPwd;
    }
    public AsyncHttpClient getAuth(AsyncHttpClient asyncHttpClient) throws JSONException {
        String timestamp = String.valueOf(System.currentTimeMillis());
        asyncHttpClient.addHeader("timestamp" ,timestamp);
        asyncHttpClient.addHeader("token" ,getAuthToken(code, timestamp));
        asyncHttpClient.addHeader("version" ,getVersion());
        return asyncHttpClient;
    }
    private String getVersion(){
        String version = null;
        try {
            PackageInfo pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            version = pinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
    private String getAuthToken(String code, String str){
        String authToken = null;
        List<Integer> authSlices = getAuthSlice();
        StringBuilder authSB = new StringBuilder();
        String token = SHA256Encode(code, str);
        int start = 0;
        for (int slice : authSlices) {
            authSB.append(token.substring(start, start + slice));
            authSB.append(str);
            start += slice;
        }
        authToken = SHA256Encode(code, String.valueOf(authSB));
        return authToken;
    }
    private List<Integer> getAuthSlice(){
        List<Integer> slices = null;
        String sliceStr = context.getString(R.string.auth_slice);
        if(sliceStr != null && !"".equals(sliceStr) && sliceStr.split(",") != null){
            slices = new ArrayList<>();
        }
        for (String slice : context.getString(R.string.auth_slice).split(",")){
            slices.add(Integer.valueOf(slice));
        }
        return slices;
    }

    private String SHA256Encode(String code,String message){
        MessageDigest md;
        String encode = null;
        try {
            md = MessageDigest.getInstance(code);
            encode = byteArrayToHexString(md.digest(message
                    .getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encode;
    }
    private String byteArrayToHexString(byte[] bts) {
        String des = "";
        String tmp;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
}
