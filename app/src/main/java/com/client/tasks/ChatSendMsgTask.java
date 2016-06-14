package com.client.tasks;

import android.os.AsyncTask;

import com.client.adapters.ChatAdapter;
import com.client.enums.TransferMSG;
import com.client.models.ChatItemData;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.jivesoftware.smack.chat.Chat;


/**
 * Created by lee on 16-6-13.
 */
public class ChatSendMsgTask extends AsyncTask<Void, Void, Boolean> {
    public ChatAdapter chatAdapter;
    public ChatItemData chatItemData;
    public PullToRefreshListView listView;
    public Chat topChat;

    @Override
    protected Boolean doInBackground(Void... params) {
        boolean flag = false;
        try{
            topChat.sendMessage(chatItemData.Msg);
            flag = true;
        }catch (Exception e){}
        return  flag;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if(result){
            chatAdapter.data.get(chatItemData.ChatID).MsgStatus = TransferMSG.SendStatus.COMPLETED;
            chatAdapter.notifyDataSetChanged();
            listView.onRefreshComplete();
            listView.getRefreshableView().smoothScrollByOffset(listView.getBottom());
        }
    }
}
