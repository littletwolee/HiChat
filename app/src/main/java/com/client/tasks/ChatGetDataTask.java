package com.client.tasks;

import android.os.AsyncTask;
import com.client.adapters.ChatAdapter;
import com.client.enums.TransferMSG;
import com.client.models.ChatItemData;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lee on 16-6-5.
 */
public class ChatGetDataTask extends AsyncTask<Void, Void, List<ChatItemData>> {
    private ChatAdapter chatAdapter;
    private PullToRefreshListView listView;
    public ChatGetDataTask(ChatAdapter chatAdapter, PullToRefreshListView listView){
        this.chatAdapter = chatAdapter;
        this.listView = listView;
    }
    @Override
    protected List<ChatItemData> doInBackground(Void... params) {
        List<ChatItemData> chatList = new ArrayList<ChatItemData>();
        ChatItemData chatItemData;
        for (int i = 1; i <= 5; i++) {
            chatItemData = new ChatItemData();
            if(i % 2 == 0) {
                chatItemData.Msg = String.valueOf(i);;
                chatItemData.MsgType = TransferMSG.TransferType.SEND;
                chatItemData.MsgDate = new Date();
                if(i % 4 == 0){
                    chatItemData.MsgStatus = TransferMSG.SendStatus.COMPLETED;
                } else {
                    chatItemData.MsgStatus = TransferMSG.SendStatus.SENDING;
                }
                chatItemData.UserName = "me";
            } else {
                chatItemData.Msg = String.valueOf(i);
                chatItemData.MsgType = TransferMSG.TransferType.RECEIVE;
                chatItemData.MsgDate = new Date();
                chatItemData.UserName = "friend";
            }
            chatList.add(chatItemData);
        }
        return chatList;
    }
    @Override
    protected void onPostExecute(List<ChatItemData> result) {
        chatAdapter.data = result;
        listView.getRefreshableView().setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();

        // Call onRefreshComplete when the list has been refreshed.
        listView.onRefreshComplete();
        super.onPostExecute(result);
    }
}
