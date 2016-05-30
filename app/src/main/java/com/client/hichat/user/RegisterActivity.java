package com.client.hichat.user;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.client.hichat.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


public class RegisterActivity extends AppCompatActivity {
    private Button btn_register;
    private EditText et_user_name, et_user_pwd;
    private TextView txt_title, txt_left;
    private RelativeLayout rl_back;
    private String _Url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inIt();
    }
    private void inIt() {
        //variable assignment
        _Url = getString(R.string.api_url) + "user/register";
        //register controls
        btn_register = (Button)findViewById(R.id.btn_register);
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_user_pwd = (EditText)findViewById(R.id.et_user_pwd);
        txt_title = (TextView)findViewById(R.id.txt_title);
        txt_left = (TextView)findViewById(R.id.txt_left);
        rl_back = (RelativeLayout)findViewById(R.id.rl_back);
        rl_back.setVisibility(View.VISIBLE);
        txt_title.setText(R.string.register);
        txt_left.setText(R.string.login);
        //add events
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    JSONObject params = new JSONObject();
                    params.put("name", "111");
                    params.put("pwd", "222");
                    client.post(RegisterActivity.this, _Url, new StringEntity(params.toString()), getString(R.string.http_json), new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            int a = statusCode;
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            String a;
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
