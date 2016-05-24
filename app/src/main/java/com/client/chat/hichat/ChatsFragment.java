package com.client.chat.hichat;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.client.chat.models.ChatItem;
import com.client.chat.models.ChatItemData;
import com.client.chat.tools.DBHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.text.Format;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {
    private PullToRefreshListView mPullToRefreshListView;
    //private List<Map<String, Object>> mListItems;
    private ChatAdapter mAdapter;
    DBHelper database;
    SQLiteDatabase db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPullToRefreshListView = (PullToRefreshListView)getActivity().findViewById(R.id.chats_list);
        mAdapter = new ChatAdapter(this.getContext());
        database = new DBHelper(this.getActivity());
        mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getContext().getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                new GetDataTask().execute();
            }
        });
        new GetDataTask().execute();

    }
    private class GetDataTask extends AsyncTask<Void, Void, List<ChatItemData>> {

        @Override
        protected List<ChatItemData> doInBackground(Void... params) {
            List<ChatItemData> chatList = new ArrayList<ChatItemData>();
            for (int i = 1; i <= 20; i++) {
                chatList.add(new ChatItemData(String.valueOf(i), "line"+String.valueOf(i), "friend", null, "hahahah", (new Date()).toString()));
            }
            db = database.getReadableDatabase();
            String sql = "insert into user(username,password) values ('Jack Johnson','iLovePopMuisc')";//插入操作的SQL语句
            db.execSQL(sql);
            return chatList;
        }
        @Override
        protected void onPostExecute(List<ChatItemData> result) {
            mAdapter.data = result;
            mPullToRefreshListView.getRefreshableView().setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            // Call onRefreshComplete when the list has been refreshed.
            mPullToRefreshListView.onRefreshComplete();
            super.onPostExecute(result);
        }
    }

    public class ChatAdapter extends BaseAdapter
    {
        private LayoutInflater mInflater = null;
        public  List<ChatItemData> data;
        private ChatAdapter(Context context)
        {
            //根据context上下文加载布局，这里的是Demo17Activity本身，即this
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            //How many items are in the data set represented by this Adapter.
            //在此适配器中所代表的数据集中的条目数
            return data.size();
        }
        @Override
        public Object getItem(int position) {
            // Get the data item associated with the specified position in the data set.
            //获取数据集中与指定索引对应的数据项
            return position;
        }
        @Override
        public long getItemId(int position) {
            //Get the row id associated with the specified position in the list.
            //获取在列表中与指定索引对应的行id
            return position;
        }

        //Get a View that displays the data at the specified position in the data set.
        //获取一个在数据集中指定索引的视图来显示数据
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ChatItem chatItem = null;
            //如果缓存convertView为空，则需要创建View
            if(convertView == null)
            {
                chatItem = new ChatItem();
                //根据自定义的Item布局加载布局
                convertView = mInflater.inflate(R.layout.chat_list_item, null);
                chatItem.ChatID = data.get(position).ChatID;
                chatItem.ChatName = (TextView)convertView.findViewById(R.id.chat_name);
                chatItem.ChatType = data.get(position).ChatType;
                chatItem.Pic = (ImageView)convertView.findViewById(R.id.pic);
                chatItem.LastMsg = (TextView)convertView.findViewById(R.id.last_msg);
                //chatItem.setLastTime(data.get(position).LastTime);
                //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                convertView.setTag(chatItem);
            }else
            {
                chatItem = (ChatItem)convertView.getTag();
            }
            //chatItem.Pic.setBackgroundResource();
            chatItem.ChatName.setText((String) data.get(position).ChatName);
            chatItem.LastMsg.setText((String) data.get(position).LastMsg);
            chatItem.Pic.setImageResource(R.mipmap.ic_launcher);
            return convertView;
        }

    }

}
