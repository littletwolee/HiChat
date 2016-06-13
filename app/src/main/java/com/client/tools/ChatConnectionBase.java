package com.client.tools;

import android.content.Context;

import com.client.hichat.R;
import com.client.tasks.ChatConnectionTask;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

/**
 * Created by lee on 6/13/16.
 */
public class ChatConnectionBase {
    private static XMPPTCPConnectionConfiguration.Builder configBuilder = null;
    public static AbstractXMPPConnection Connection;
    private ChatConnectionTask chatConnectionTask;
    private ChatConnectionBase(Context context){
        configBuilder = XMPPTCPConnectionConfiguration.builder();
        configBuilder.setHost(context.getString(R.string.xmpp_host));
        configBuilder.setPort(Integer.valueOf(context.getString(R.string.xmpp_port)));
        configBuilder.setServiceName(context.getString(R.string.xmpp_domain));
        Connection = new XMPPTCPConnection(configBuilder.build());
    }
    private static class ChatConnectionBaseHolder {
        private Context context;
        private static final ChatConnectionBase INSTANCE = new ChatConnectionBase(context);
        private static final ChatConnectionBaseHolder(Context context){
            this.context = context;
        }
    }
    public static final ChatConnectionBase getInstance(Context context) {
        new ChatConnectionBaseHolder(context).context = context;
        return ChatConnectionBaseHolder.INSTANCE;
    }
    public void ChatConnect(){
        chatConnectionTask = new ChatConnectionTask(Connection);
        chatConnectionTask.execute();
    }
}
