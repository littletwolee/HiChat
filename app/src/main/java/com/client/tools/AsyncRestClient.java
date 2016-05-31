package com.client.tools;

import android.content.Context;

import com.client.hichat.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import cz.msebera.android.httpclient.HttpEntity;


/**
 * Created by lee on 5/31/16.
 */
public class AsyncRestClient {
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(Context context, String url, HttpEntity entity, String contentType, ResponseHandlerInterface responseHandler) {
        client.get(context,getAbsoluteUrl(context,url), entity, contentType, responseHandler);
    }

    public static void post(Context context, String url, HttpEntity entity, String contentType, ResponseHandlerInterface responseHandler) {
        client.post(context,getAbsoluteUrl(context,url), entity, contentType, responseHandler);
    }

    private static String getAbsoluteUrl(Context context,String relativeUrl) {
        return context.getString(R.string.api_url) + relativeUrl;
    }
}
