package com.client.chat.hichat;


import android.content.Context;
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
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {
    private PullToRefreshListView mPullToRefreshListView;
    private List<Map<String, Object>> mListItems;
    private ArrayAdapter<String> mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);
        mPullToRefreshListView = (PullToRefreshListView)view.findViewById(R.id.chats_list);
        mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getContext().getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                // Do work to refresh the list here.
                new GetDataTask().execute();
            }
        });

        ListView actualListView = mPullToRefreshListView.getRefreshableView();

        mListItems = new LinkedList<String>();
        mListItems.addAll(Arrays.asList(mStrings));

        mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mListItems);
        actualListView.setAdapter(mAdapter);
        return view;
    }
    private class GetDataTask extends AsyncTask<Void, Void, List<ChatItem>> {

        @Override
        protected List<ChatItem> doInBackground(Void... params) {
            // Simulates a background job.
            List<ChatItem> chatList = null;
            chatList = new ArrayList<ChatItem>();
            chatList.add(new ChatItem("1", "friend", new byte[0], "lllll", "now"));
            return chatList;
        }
        @Override
        protected void onPostExecute(List<ChatItem> result) {
            mAdapter.notifyDataSetChanged();

            // Call onRefreshComplete when the list has been refreshed.
            mPullToRefreshListView.onRefreshComplete();
            super.onPostExecute(result);
        }
    }

    public class ChatAdapter extends BaseAdapter
    {
        private LayoutInflater mInflater = null;
        private ChatAdapter(Context context)
        {
            //根据context上下文加载布局，这里的是Demo17Activity本身，即this
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            //How many items are in the data set represented by this Adapter.
            //在此适配器中所代表的数据集中的条目数
            return mListItems.size();
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
                chatItem.Pic = (ImageView)convertView.findViewById(R.id.img);
                chatItem.title = (TextView)convertView.findViewById(R.id.tv);
                chatItem.info = (TextView)convertView.findViewById(R.id.info);
                //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
                convertView.setTag(chatItem);
            }else
            {
                chatItem = (ChatItem)convertView.getTag();
            }
            chatItem.img.setBackgroundResource((Integer) mListItems.get(position).get("img"));
            chatItem.title.setText((String) mListItems.get(position).get("title"));
            chatItem.info.setText((String) mListItems.get(position).get("info"));

            return convertView;
        }

    }

}
