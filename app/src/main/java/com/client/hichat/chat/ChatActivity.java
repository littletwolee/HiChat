package com.client.hichat.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.client.adapters.ChatAdapter;
import com.client.enums.TransferMSG;
import com.client.hichat.R;
import com.client.hichat.user.UserInfoActivity;
import com.client.models.ChatItemData;
import com.client.tasks.ChatGetDataTask;
import com.client.tasks.ChatReceiveMsgTask;
import com.client.tasks.ChatSendMsgTask;
import com.client.tools.ChatConnectionBase;
import com.client.tools.DBHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

import java.util.Date;


public class ChatActivity extends Activity{
    private EditText mEditTextContent;
    private PullToRefreshListView listView;
    private ImageView iv_emoticons_normal, img_user_info, img_back_main, iv_emoticons_checked;
    private InputMethodManager manager;
    private LinearLayout btnContainer, emojiIconContainer;
    private RelativeLayout edittext_layout;
    private View btnSetModeKeyboard, more, btnSend, btnPressToSpeak, btnMore, btnSetModeVoice;
    private ChatAdapter chatAdapter;
    private ChatGetDataTask chatGetDataTask;
    private ChatConnectionBase chatConnectionBase;
    private DBHelper database;
    private static ChatManager cm;
    private Chat topChat;
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
        btnSetModeKeyboard = findViewById(R.id.btn_set_mode_keyboard);
        btnMore = findViewById(R.id.btn_more);
        btnSend = findViewById(R.id.btn_send);
        btnPressToSpeak = findViewById(R.id.btn_press_to_speak);
        btnSetModeVoice = findViewById(R.id.btn_set_mode_voice);
        mEditTextContent = (EditText) findViewById(R.id.et_send_message);
        chatAdapter = new ChatAdapter(ChatActivity.this);
        database = new DBHelper(ChatActivity.this);
        refreshListData();

        //
        //add events
        //
        img_back_main.setOnClickListener(rl_back_click);
        img_user_info.setOnClickListener(img_user_info_click);
        listView.setOnRefreshListener(listview_refresh);
        btnSetModeKeyboard.setOnClickListener(btn_SetModeKeyboard_click);
        btnSetModeVoice.setOnClickListener(btn_SetModeVoice_click);
        btnMore.setOnClickListener(button_more_click);
        mEditTextContent.setOnClickListener(et_mEditTextContent_click);
        mEditTextContent.addTextChangedListener(watcher);
        btnSend.setOnClickListener(btn_Send_click);
        //init chat connection
        chatConnectionBase = ChatConnectionBase.getInstance();
        cm = ChatManager.getInstanceFor(chatConnectionBase.Connection);
        cm.addChatListener(chatManagerListener);
        topChat = cm.createChat("user2@"+getString(R.string.xmpp_domain), chatMessageListener);
    }

    //
    //handlers
    //
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
    PullToRefreshBase.OnRefreshListener<ListView> listview_refresh = new PullToRefreshBase.OnRefreshListener<ListView>() {
        @Override
        public void onRefresh(PullToRefreshBase<ListView> refreshView) {
            String label = DateUtils.formatDateTime(ChatActivity.this, System.currentTimeMillis(),
                    DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
            refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
            refreshListData();
        }
    };
    TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(mEditTextContent.getText().toString().length() > 0){
                btnSend.setVisibility(View.VISIBLE);
            }else {
                btnSend.setVisibility(View.GONE);
            }
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void afterTextChanged(Editable s) {}
    };
    View.OnClickListener btn_SetModeKeyboard_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            edittext_layout.setVisibility(View.VISIBLE);
            more.setVisibility(View.GONE);
            btnSetModeKeyboard.setVisibility(View.GONE);
            btnSetModeVoice.setVisibility(View.VISIBLE);
            // mEditTextContent.setVisibility(View.VISIBLE);
            mEditTextContent.requestFocus();
            // buttonSend.setVisibility(View.VISIBLE);
            btnPressToSpeak.setVisibility(View.GONE);
            if (TextUtils.isEmpty(mEditTextContent.getText())) {
                btnMore.setVisibility(View.VISIBLE);
                btnSend.setVisibility(View.GONE);
            } else {
                btnMore.setVisibility(View.GONE);
                btnSend.setVisibility(View.VISIBLE);
            }
        }
    };
    View.OnClickListener btn_SetModeVoice_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideKeyboard();
            edittext_layout.setVisibility(View.GONE);
            more.setVisibility(View.GONE);
            btnSetModeVoice.setVisibility(View.GONE);
            btnSetModeKeyboard.setVisibility(View.VISIBLE);
            btnSend.setVisibility(View.GONE);
            btnMore.setVisibility(View.VISIBLE);
            btnPressToSpeak.setVisibility(View.VISIBLE);
            iv_emoticons_normal.setVisibility(View.VISIBLE);
            iv_emoticons_checked.setVisibility(View.INVISIBLE);
            btnContainer.setVisibility(View.VISIBLE);
            emojiIconContainer.setVisibility(View.GONE);
        }
    };
    View.OnClickListener button_more_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
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
    };
    View.OnClickListener et_mEditTextContent_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //listView.setSelection(listView.getCount() - 1);
            if (more.getVisibility() == View.VISIBLE) {
                more.setVisibility(View.GONE);
                iv_emoticons_normal.setVisibility(View.VISIBLE);
                iv_emoticons_checked.setVisibility(View.INVISIBLE);
            }
        }
    };
    View.OnClickListener btn_Send_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String msg = mEditTextContent.getText().toString();
            Integer chatid = chatAdapter.data.size();
            ChatItemData senddata = new ChatItemData(chatid, "me", msg,
                    TransferMSG.TransferType.SEND, new Date(), TransferMSG.SendStatus.SENDING, null);
            chatAdapter.data.put(chatid, senddata);
            chatAdapter.notifyDataSetChanged();
            listView.onRefreshComplete();
            listView.getRefreshableView().smoothScrollByOffset(listView.getBottom());
            ChatSendMsgTask chatSendMsgTask = new ChatSendMsgTask();
            chatSendMsgTask.chatAdapter = chatAdapter;
            chatSendMsgTask.topChat = topChat;
            chatSendMsgTask.chatItemData = senddata;
            chatSendMsgTask.listView = listView;
            chatSendMsgTask.execute();
        }
    };
    ChatMessageListener chatMessageListener = new ChatMessageListener(){
        public void processMessage(Chat chat, Message message){
            System.out.println("Received message: " + message);
        }
    };
    ChatManagerListener chatManagerListener = new ChatManagerListener() {
        @Override
        public void chatCreated(Chat chat, boolean result) {
            chat.addMessageListener(new ChatMessageListener(){
                @Override
                public void processMessage(Chat chat, Message msg) {
                    topChat = chat;
                    if(null!=msg.getBody())
                    {
                        ChatReceiveMsgTask chatReceiveMsgTask = new ChatReceiveMsgTask();
                        chatReceiveMsgTask.chatAdapter = chatAdapter;
                        chatReceiveMsgTask.listView = listView;
                        chatReceiveMsgTask.chatItemData = new ChatItemData(chatAdapter.data.size(), "user2", msg.getBody(),
                                TransferMSG.TransferType.RECEIVE, new Date(), null, null);
                        chatReceiveMsgTask.execute();
                    }
                }
            });
        }
    };
//    private void setText(final String text)
//    {
//        runOnUiThread(new Runnable(){
//            public void run() {
//                TextView tv = new TextView(ChatActivity.this);
//                tv.setTextColor(Color.parseColor("#000000"));
//                tv.setText(text);
//                //chat_msg_list.addView(tv);
//            };
//        });
//    }



    //
    //internal fun
    //
    private void refreshListData(){
        chatGetDataTask = new ChatGetDataTask(chatAdapter, listView);
        chatGetDataTask.execute();
    }

    private void hideKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
