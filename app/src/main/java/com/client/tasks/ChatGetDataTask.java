package com.client.tasks;

import android.os.AsyncTask;

import com.client.adapters.ChatAdapter;
import com.client.enums.TransferMSG;
import com.client.models.ChatItemData;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lee on 16-6-5.
 */
public class ChatGetDataTask extends AsyncTask<Void, Void, Map<Integer, ChatItemData>> {
    private ChatAdapter chatAdapter;
    private PullToRefreshListView listView;
    public ChatGetDataTask(ChatAdapter chatAdapter, PullToRefreshListView listView){
        this.chatAdapter = chatAdapter;
        this.listView = listView;
    }
    @Override
    protected Map<Integer, ChatItemData> doInBackground(Void... params) {
        Map<Integer, ChatItemData> chatList = new HashMap<>();
        ChatItemData chatItemData;
        for (int i = 0; i <= 20; i++) {
            chatItemData = new ChatItemData();
            chatItemData.ChatID = i;
            if(i % 2 == 0) {
                chatItemData.Msg = "s" + String.valueOf(i);
                chatItemData.MsgType = TransferMSG.TransferType.SEND;
                chatItemData.MsgDate = new Date();
                if(i % 4 == 0){
                    chatItemData.MsgStatus = TransferMSG.SendStatus.COMPLETED;
                } else {
                    chatItemData.MsgStatus = TransferMSG.SendStatus.SENDING;
                }
                chatItemData.UserName = "me";
            } else {
                chatItemData.Msg = "r" + String.valueOf(i);
                chatItemData.MsgType = TransferMSG.TransferType.RECEIVE;
                chatItemData.MsgDate = new Date();
                chatItemData.UserName = "friend";
            }
            chatList.put(i, chatItemData);
        }
        return chatList;
    }
    @Override
    protected void onPostExecute(Map<Integer, ChatItemData> result) {
        chatAdapter.data = result;
        listView.getRefreshableView().setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();

        // Call onRefreshComplete when the list has been refreshed.
        listView.onRefreshComplete();
        super.onPostExecute(result);
    }
}
