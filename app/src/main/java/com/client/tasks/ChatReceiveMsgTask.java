package com.client.tasks;

import android.os.AsyncTask;

import com.client.adapters.ChatAdapter;
import com.client.enums.TransferMSG;
import com.client.models.ChatItemData;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.jivesoftware.smack.chat.Chat;

import java.util.Date;

/**
 * Created by lee on 6/14/16.
 */
public class ChatReceiveMsgTask extends AsyncTask<Void, Void, Void> {
    public ChatAdapter chatAdapter;
    public ChatItemData chatItemData;
    public PullToRefreshListView listView;

    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        chatAdapter.data.put(chatItemData.ChatID, chatItemData);
        chatAdapter.notifyDataSetChanged();
        listView.onRefreshComplete();
        listView.getRefreshableView().smoothScrollByOffset(listView.getBottom());
    }
}
