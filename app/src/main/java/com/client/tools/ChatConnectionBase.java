package com.client.tools;

import android.content.Context;

import com.client.hichat.R;
import com.client.tasks.ChatConnectionTask;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

/**
 * Created by lee on 6/13/16.
 */
public class ChatConnectionBase {
    private XMPPTCPConnectionConfiguration.Builder configBuilder;
    public AbstractXMPPConnection Connection;
    public Context context;
    private ChatConnectionTask chatConnectionTask;
    private ChatConnectionBase(){}
    public void Init(ChatConnectionBase chatConnectionBase, Context context){
        chatConnectionBase.configBuilder = XMPPTCPConnectionConfiguration.builder();
        chatConnectionBase.configBuilder.setHost(context.getString(R.string.xmpp_host));
        chatConnectionBase.configBuilder.setPort(Integer.valueOf(context.getString(R.string.xmpp_port)));
        chatConnectionBase.configBuilder.setServiceName(context.getString(R.string.xmpp_domain));
        Connection = new XMPPTCPConnection(chatConnectionBase.configBuilder.build());
    }
    private static class ChatConnectionBaseHolder {
        private static final ChatConnectionBase INSTANCE = new ChatConnectionBase();
    }
    public static final ChatConnectionBase getInstance() {
        return ChatConnectionBaseHolder.INSTANCE;
    }
    public void ChatConnect(){
        chatConnectionTask = new ChatConnectionTask(Connection);
        chatConnectionTask.execute();
    }
}
