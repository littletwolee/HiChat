package com.client.hichat.user;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.client.hichat.ChatsFragment;
import com.client.hichat.MainActivity;
import com.client.hichat.R;
import com.client.hichat.chat.ChatActivity;
import com.client.models.User;
import com.client.moudles.UserHelper;
import com.client.tools.AsyncRestClient;
import com.client.tools.AuthHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


public class RegisterActivity extends AppCompatActivity {
    private Button btn_register;
    private EditText et_user_name, et_user_pwd;
    private TextView txt_title, txt_left;
    private RelativeLayout rl_back;
    private String _Url, name, pwd;
    private UserHelper userHelper;
    private User user;
    private AuthHelper authHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inIt();
    }
    private void inIt() {
        //variable assignment
        _Url = "user/register";
        authHelper = new AuthHelper(this);
        userHelper = new UserHelper(this);
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
        btn_register.setOnClickListener(btn_register_click);
        rl_back.setOnClickListener(rl_back_click);
    }
    //handlers
    View.OnClickListener btn_register_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                JSONObject params = new JSONObject();
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
                params.put("name", name);
                params.put("pwd", pwd);
                AsyncRestClient.post(RegisterActivity.this, _Url, new StringEntity(params.toString()), getString(R.string.http_json), registerHandler);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
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
            }catch (Exception e){
                Log.d("iii",e.getMessage());
            }
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            RegisterActivity.this.startActivity(intent);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            super.onFailure(statusCode, headers, responseString, throwable);
            Toast.makeText(RegisterActivity.this, RegisterActivity.this.getResources().getText(R.string.register_err),
                    Toast.LENGTH_SHORT).show();
        }
    };
}
