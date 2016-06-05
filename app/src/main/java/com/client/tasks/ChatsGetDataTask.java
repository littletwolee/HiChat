package com.client.tasks;

import android.os.AsyncTask;

import com.client.adapters.ChatsAdapter;
import com.client.models.ChatItemData;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lee on 16-6-5.
 */
public class ChatsGetDataTask extends AsyncTask<Void, Void, List<ChatItemData>> {
    private ChatsAdapter chatAdapter;
    private PullToRefreshListView listView;
    public ChatsGetDataTask(ChatsAdapter chatAdapter, PullToRefreshListView listView){
        this.chatAdapter = chatAdapter;
        this.listView = listView;
    }
    @Override
    protected List<ChatItemData> doInBackground(Void... params) {
        List<ChatItemData> chatList = new ArrayList<ChatItemData>();
        for (int i = 1; i <= 20; i++) {
            chatList.add(new ChatItemData(String.valueOf(i), "line"+String.valueOf(i), "friend", null, "hahahah", (new Date()).toString()));
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
