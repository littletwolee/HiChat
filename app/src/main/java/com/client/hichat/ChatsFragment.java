package com.client.hichat;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.client.adapters.ChatsAdapter;
import com.client.tasks.ChatsGetDataTask;
import com.client.tools.DBHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {
    private PullToRefreshListView mPullToRefreshListView;
    //private List<Map<String, Object>> mListItems;
    private ChatsAdapter mAdapter;
    DBHelper database;
    SQLiteDatabase db;
    private ChatsGetDataTask chatsGetDataTask;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPullToRefreshListView = (PullToRefreshListView)getActivity().findViewById(R.id.chats_list);
        mAdapter = new ChatsAdapter(this.getContext());
        database = new DBHelper(this.getActivity());
        chatsGetDataTask = new ChatsGetDataTask(mAdapter, mPullToRefreshListView);
        mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getContext().getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                chatsGetDataTask.execute();
            }
        });
        chatsGetDataTask.execute();

    }
}
