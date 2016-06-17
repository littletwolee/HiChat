package com.client.hichat.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.client.hichat.MainActivity;
import com.client.hichat.R;
import com.client.models.User;
import com.client.moudles.UserHelper;
import com.client.tools.AsyncRestClient;
import com.client.tools.AuthHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class LoginActivity extends AppCompatActivity {
    private Button btn_register,btn_login;
    private EditText et_user_name, et_user_pwd;
    private TextView txt_title;
    private RelativeLayout rl_back;
    private String _Url, name, pwd;
    private UserHelper userHelper;
    private AuthHelper authHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userHelper = new UserHelper(this);
        if(userHelper.isAuth()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(intent);
        }else {
            inIt();
        }
    }

    private void inIt() {
        //variable assignment
        _Url = "user/login?";
        authHelper = new AuthHelper(this);
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
            name = et_user_name.getText().toString().trim();
            pwd = et_user_pwd.getText().toString().trim();
            if (name.equals("")){
                et_user_name.setError("username is required");
                return;
            }
            if (pwd.equals("")){
                et_user_name.setError("password is required");
                return;
            }
            pwd = authHelper.encryption(pwd);
            try {
                userHelper.updateUser(userHelper.findUser(name));
            }catch (Exception e){}
            AsyncRestClient.get(LoginActivity.this, _Url+"username="+name+"&password="+pwd, null,
                    getString(R.string.http_json), registerHandler);
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
            try {
                userHelper.login(new User(null, name, pwd, true, new Date()));
            }catch (Exception e){}
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(intent);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            if(statusCode == 401){
                Toast.makeText(LoginActivity.this, LoginActivity.this.getResources().getText(R.string.auth_err),
                        Toast.LENGTH_SHORT).show();
                et_user_name.requestFocus();
            }else {
                Toast.makeText(LoginActivity.this, LoginActivity.this.getResources().getText(R.string.login_err),
                        Toast.LENGTH_SHORT).show();
            }
        }
        
    };
}

