package com.client.hichat.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.client.hichat.R;
import com.client.tools.AsyncRestClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class LoginActivity extends AppCompatActivity {
    private Button btn_register,btn_login;
    private EditText et_user_name, et_user_pwd;
    private TextView txt_title;
    private RelativeLayout rl_back;
    private String _Url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inIt();
    }
    private void inIt() {
        //variable assignment
        _Url = "user/login?";
        //register controls
        btn_register = (Button)findViewById(R.id.btn_register);
        btn_login = (Button)findViewById(R.id.btn_login);
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_user_pwd = (EditText)findViewById(R.id.et_user_pwd);
        txt_title = (TextView)findViewById(R.id.txt_title);
        rl_back = (RelativeLayout)findViewById(R.id.rl_back);
        rl_back.setVisibility(View.VISIBLE);
        txt_title.setText(R.string.login);
        //add events
        btn_register.setOnClickListener(btn_register_click);
        rl_back.setOnClickListener(rl_back_click);
        btn_login.setOnClickListener(btn_login_click);
    }
    //handlers
    View.OnClickListener btn_register_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
            LoginActivity.this.startActivityForResult(intent, 1);
        }
    };
    View.OnClickListener btn_login_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name = et_user_name.getText().toString().trim(),
                    pwd = et_user_pwd.getText().toString().trim();

            if (name.equals("")){
                et_user_name.setError("username is required");
                return;
            }
            if (pwd.equals("")){
                et_user_name.setError("password is required");
                return;
            }
            AsyncRestClient.get(LoginActivity.this, _Url+"username="+name+"&password="+pwd, null, getString(R.string.http_json), registerHandler);
        }
    };
    View.OnClickListener rl_back_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    AsyncHttpResponseHandler registerHandler = new JsonHttpResponseHandler(){
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            String a;
        }
        
    };
}

