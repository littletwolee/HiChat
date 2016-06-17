package com.client.tasks;

import android.os.AsyncTask;

import com.client.adapters.ChatAdapter;
import com.client.models.Friends;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lee on 6/17/16.
 */
public class ContactsGetDataTask extends AsyncTask<Void, Void, List<Friends> {
    private ChatAdapter chatAdapter;
    private PullToRefreshListView listView;
    public ContactsGetDataTask(ChatAdapter chatAdapter, PullToRefreshListView listView){
        this.chatAdapter = chatAdapter;
        this.listView = listView;
    }
    @Override
    protected List<Friends> doInBackground(Void... params) {
        List<Friends> friendsList = new ArrayList<>();
        Friends friend;
        for (long i = 0; i <= 20; i++) {
            friend = new Friends();
            friend.setUsername(String.valueOf(i));
            friendsList.add(friend);
        }
        return friendsList;
    }
    @Override
    protected void onPostExecute(List<Friends> result) {
        chatAdapter.data = result;
        listView.getRefreshableView().setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();

        // Call onRefreshComplete when the list has been refreshed.
        listView.onRefreshComplete();
        super.onPostExecute(result);
    }
}
