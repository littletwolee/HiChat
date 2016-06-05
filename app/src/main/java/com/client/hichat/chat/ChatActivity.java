package com.client.hichat.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.client.adapters.ChatAdapter;
import com.client.hichat.R;
import com.client.hichat.user.UserInfoActivity;
import com.client.tasks.ChatGetDataTask;
import com.client.tools.DBHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.jivesoftware.smack.chat.Chat;


public class ChatActivity extends Activity{
    private EditText mEditTextContent;
    private Chat topChat;
    private PullToRefreshListView listView;
    private ImageView iv_emoticons_normal, img_user_info, img_back_main, iv_emoticons_checked;
    private InputMethodManager manager;
    private LinearLayout btnContainer, emojiIconContainer;
    private RelativeLayout edittext_layout;
    private View buttonSetModeKeyboard, more, buttonSend, buttonPressToSpeak, btnMore, buttonSetModeVoice;
    private ChatAdapter chatAdapter;
    private ChatGetDataTask chatGetDataTask;
    DBHelper database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        inIt();
    }
    private void inIt() {
        listView = (PullToRefreshListView) findViewById(R.id.list);
        more = findViewById(R.id.more);
        iv_emoticons_normal = (ImageView) findViewById(R.id.iv_emoticons_normal);
        iv_emoticons_normal.setVisibility(View.VISIBLE);
        iv_emoticons_checked = (ImageView) findViewById(R.id.iv_emoticons_checked);
        iv_emoticons_checked.setVisibility(View.INVISIBLE);
        img_back_main = (ImageView)findViewById(R.id.title_back);
        img_back_main.setVisibility(View.VISIBLE);
        img_user_info = (ImageView)findViewById(R.id.title_user_info);
        img_user_info.setVisibility(View.VISIBLE);
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        btnContainer = (LinearLayout) findViewById(R.id.ll_btn_container);
        emojiIconContainer = (LinearLayout) findViewById(R.id.ll_face_container);
        edittext_layout = (RelativeLayout) findViewById(R.id.edit_text_layout);
        edittext_layout.setBackgroundResource(R.mipmap.input_bar_bg_normal);
        buttonSetModeKeyboard = findViewById(R.id.btn_set_mode_keyboard);
        btnMore = findViewById(R.id.btn_more);
        buttonSend = findViewById(R.id.btn_send);
        buttonPressToSpeak = findViewById(R.id.btn_press_to_speak);
        buttonSetModeVoice = findViewById(R.id.btn_set_mode_voice);
        mEditTextContent = (EditText) findViewById(R.id.et_send_message);
        chatAdapter = new ChatAdapter(ChatActivity.this);
        database = new DBHelper(ChatActivity.this);
        chatGetDataTask = new ChatGetDataTask(chatAdapter, listView);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(ChatActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                chatGetDataTask.execute();
            }
        });
        chatGetDataTask.execute();
        //add events
        img_back_main.setOnClickListener(rl_back_click);
        img_user_info.setOnClickListener(img_user_info_click);
//        chat_text_list = (EditText)findViewById(R.id.chat_msg_box);
//        sendmsg = (ImageView)findViewById(R.id.more_type_btn);
//        chat_msg_list = (LinearLayout)findViewById(R.id.chat_msg_list);
//        String ip = "10.157.193.27";
//
//
//        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
//
//        configBuilder.setHost(ip);
//        configBuilder.setPort(5222);
//        configBuilder.setServiceName(ip);
//        final AbstractXMPPConnection connection =
//                new XMPPTCPConnection(configBuilder.build());
//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//                try {
//                    //Thread.sleep(3000);
//                    connection.connect();
//                    connection.login("user2", "123456");
//                    Presence presence = new Presence(Presence.Type.available);
//                    presence.setStatus("I am online");
//                    connection.sendStanza(presence);
//                    setText("connected server");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//        final ChatManager cm = ChatManager.getInstanceFor(connection);
//
//        sendmsg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                try {
//                    String text = (String)chat_text_list.getText().toString();
//                    topChat.sendMessage(text);
//                    setText(text);
//                } catch (SmackException.NotConnectedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        });
//        topChat = cm.createChat("user1@"+ip, new ChatMessageListener() {
//
//            public void processMessage(Chat chat, Message message){
//                System.out.println("Received message: " + message);
//            }
//        });
//        cm.addChatListener(new ChatManagerListener(){
//
//            @Override
//            public void chatCreated(Chat arg0, boolean arg1) {
//                arg0.addMessageListener(new ChatMessageListener(){
//
//                    @Override
//                    public void processMessage(Chat arg0, Message arg1) {
//                        topChat = arg0;
//                        if(null!=arg1.getBody())
//                        {
//                            String from = arg1.getFrom().substring(0,arg1.getFrom().indexOf("@"));
//                            setText("from "+from+" : "+arg1.getBody());
//                        }
//                    }
//                });
//            }
//        });
    }
    //add events
    View.OnClickListener rl_back_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    View.OnClickListener img_user_info_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(ChatActivity.this,UserInfoActivity.class);
            intent.putExtra("userid", "");
            ChatActivity.this.startActivityForResult(intent, 1);
        }
    };
    TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(mEditTextContent.getText().toString().length() > 0){
                buttonSend.setVisibility(View.VISIBLE);
            }else {
                buttonSend.setVisibility(View.GONE);
            }
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void afterTextChanged(Editable s) {}
    };
    //handlers
    private void setText(final String text)
    {
        runOnUiThread(new Runnable(){
            public void run() {
                TextView tv = new TextView(ChatActivity.this);
                tv.setTextColor(Color.parseColor("#000000"));
                tv.setText(text);
                //chat_msg_list.addView(tv);
            };
        });
    }
    public void editClick(View v) {
        //listView.setSelection(listView.getCount() - 1);
        if (more.getVisibility() == View.VISIBLE) {
            more.setVisibility(View.GONE);
            iv_emoticons_normal.setVisibility(View.VISIBLE);
            iv_emoticons_checked.setVisibility(View.INVISIBLE);
        }

    }

    public void moreClick(View view) {
        if (more.getVisibility() == View.GONE) {
            System.out.println("more gone");
            hideKeyboard();
            more.setVisibility(View.VISIBLE);
            btnContainer.setVisibility(View.VISIBLE);
            emojiIconContainer.setVisibility(View.GONE);
        } else {
            if (emojiIconContainer.getVisibility() == View.VISIBLE) {
                emojiIconContainer.setVisibility(View.GONE);
                btnContainer.setVisibility(View.VISIBLE);
                iv_emoticons_normal.setVisibility(View.VISIBLE);
                iv_emoticons_checked.setVisibility(View.INVISIBLE);
            } else {
                more.setVisibility(View.GONE);
            }

        }

    }

    public void setTypeVoice(View view) {
        hideKeyboard();
        edittext_layout.setVisibility(View.GONE);
        more.setVisibility(View.GONE);
        view.setVisibility(View.GONE);
        buttonSetModeKeyboard.setVisibility(View.VISIBLE);
        buttonSend.setVisibility(View.GONE);
        btnMore.setVisibility(View.VISIBLE);
        buttonPressToSpeak.setVisibility(View.VISIBLE);
        iv_emoticons_normal.setVisibility(View.VISIBLE);
        iv_emoticons_checked.setVisibility(View.INVISIBLE);
        btnContainer.setVisibility(View.VISIBLE);
        emojiIconContainer.setVisibility(View.GONE);

    }
    private void hideKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void setModeKeyboard(View view) {
        edittext_layout.setVisibility(View.VISIBLE);
        more.setVisibility(View.GONE);
        view.setVisibility(View.GONE);
        buttonSetModeVoice.setVisibility(View.VISIBLE);
        // mEditTextContent.setVisibility(View.VISIBLE);
        mEditTextContent.requestFocus();
        // buttonSend.setVisibility(View.VISIBLE);
        buttonPressToSpeak.setVisibility(View.GONE);
        if (TextUtils.isEmpty(mEditTextContent.getText())) {
            btnMore.setVisibility(View.VISIBLE);
            buttonSend.setVisibility(View.GONE);
        } else {
            btnMore.setVisibility(View.GONE);
            buttonSend.setVisibility(View.VISIBLE);
        }

    }
}
