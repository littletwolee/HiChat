package com.client.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.client.hichat.R;
import com.client.models.ChatItemData;
import com.client.models.ChatMsgReceiveItem;
import com.client.models.ChatMsgSendItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lee on 16-6-4.
 */
public class ChatAdapter extends BaseAdapter {
    private LayoutInflater mInflater = null;
    private Activity activity = null;
    public Map<Integer, ChatItemData> data;
    private static Map mapView;
    private static final int SEND = 0;
    private static final int RECEIVE = 1;
    public ChatAdapter(Context context)
    {
        //根据context上下文加载布局，这里的是Demo17Activity本身，即this
        this.mInflater = LayoutInflater.from(context);
        this.activity = (Activity)context;
        this.mapView = new HashMap();
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
        return data.get(position);
    }
    @Override
    public long getItemId(int position) {
        //Get the row id associated with the specified position in the list.
        //获取在列表中与指定索引对应的行id
        return position;
    }
    @Override
    public int getItemViewType(int position) {

        ChatItemData chatItemData = data.get(position);
        int type = chatItemData.MsgType.ordinal();
        return type;
    }
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    //Get a View that displays the data at the specified position in the data set.
    //获取一个在数据集中指定索引的视图来显示数据
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMsgReceiveItem chatMsgReceiveItem = null;
        ChatMsgSendItem chatMsgSendItem = null;
        ChatItemData chatItemData = (ChatItemData)getItem(position);
        int type = getItemViewType(position);
        //如果缓存convertView为空，则需要创建View
        switch (type) {
            case SEND:
                if(null == convertView){
                    convertView = mInflater.inflate(R.layout.layout_chat_send_msg, null);
                    chatMsgSendItem = new ChatMsgSendItem(convertView);
                    convertView.setTag(chatMsgSendItem);
                } else {
                    chatMsgSendItem = (ChatMsgSendItem)convertView.getTag();
                }
                chatMsgSendItem.setObject(chatMsgSendItem, chatItemData);
                break;
            case RECEIVE:
                if(null == convertView){
                    convertView = mInflater.inflate(R.layout.layout_chat_receive_msg, null);
                    chatMsgReceiveItem = new ChatMsgReceiveItem(convertView);
                    convertView.setTag(chatMsgReceiveItem);
                } else {
                    chatMsgReceiveItem = (ChatMsgReceiveItem)convertView.getTag();
                }
                chatMsgReceiveItem.setObject(chatMsgReceiveItem, chatItemData);
                break;
            default:
                break;
        }
        return convertView;
    }
}
