package com.client.chat.hichat;

import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.client.chat.tools.ToolBarActivity;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;


public class ChatActivity extends ToolBarActivity {
    private EditText chat_text_list;
    private ImageView sendmsg;
    private LinearLayout chat_msg_list;
    private Chat topChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chat_text_list = (EditText)findViewById(R.id.chat_msg_box);
        sendmsg = (ImageView)findViewById(R.id.chat_msg_send_btn);
        chat_msg_list = (LinearLayout)findViewById(R.id.chat_msg_list);
        String ip = "10.157.193.27";


        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();

        configBuilder.setHost(ip);
        configBuilder.setPort(5222);
        configBuilder.setServiceName(ip);
        final AbstractXMPPConnection connection =
                new XMPPTCPConnection(configBuilder.build());
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    //Thread.sleep(3000);
                    connection.connect();
                    connection.login("user2", "123456");
                    Presence presence = new Presence(Presence.Type.available);
                    presence.setStatus("I am online");
                    connection.sendStanza(presence);
                    setText("connected server");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        final ChatManager cm = ChatManager.getInstanceFor(connection);

        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    String text = (String)chat_text_list.getText().toString();
                    topChat.sendMessage(text);
                    setText(text);
                } catch (SmackException.NotConnectedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        topChat = cm.createChat("user1@"+ip, new ChatMessageListener() {

            public void processMessage(Chat chat, Message message){
                System.out.println("Received message: " + message);
            }
        });
        cm.addChatListener(new ChatManagerListener(){

            @Override
            public void chatCreated(Chat arg0, boolean arg1) {
                arg0.addMessageListener(new ChatMessageListener(){

                    @Override
                    public void processMessage(Chat arg0, Message arg1) {
                        topChat = arg0;
                        if(null!=arg1.getBody())
                        {
                            String from = arg1.getFrom().substring(0,arg1.getFrom().indexOf("@"));
                            setText("from "+from+" : "+arg1.getBody());
                        }
                    }
                });
            }
        });
    }
    private void setText(final String text)
    {
        runOnUiThread(new Runnable(){
            public void run() {
                TextView tv = new TextView(ChatActivity.this);
                tv.setTextColor(Color.parseColor("#000000"));
                tv.setText(text);
                chat_msg_list.addView(tv);
            };
        });
    }
    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);
        toolbar.showOverflowMenu() ;
        getLayoutInflater().inflate(R.layout.toobar_button, toolbar) ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
