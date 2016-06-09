package com.client.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.client.enums.TransferMSG;
import com.client.hichat.R;
import com.client.models.ChatItemData;
import com.client.models.ChatMsgItem;

import java.util.List;

/**
 * Created by lee on 16-6-4.
 */
public class ChatAdapter extends BaseAdapter {
    private LayoutInflater mInflater = null;
    private Activity activity = null;
    public List<ChatItemData> data;
    public ChatAdapter(Context context)
    {
        //根据context上下文加载布局，这里的是Demo17Activity本身，即this
        this.mInflater = LayoutInflater.from(context);
        this.activity = (Activity)context;
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

    //Get a View that displays the data at the specified position in the data set.
    //获取一个在数据集中指定索引的视图来显示数据
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMsgItem chatMsgItem;
        ChatItemData chatItemData = data.get(position);
        ;
        //如果缓存convertView为空，则需要创建View
        if(chatItemData.MsgType == TransferMSG.TransferType.RECEIVE){}
        if(null == convertView){
            if(chatItemData.MsgType == TransferMSG.TransferType.RECEIVE) {
                convertView = mInflater.inflate(R.layout.layout_chat_receive_msg, null);
            } else {
                convertView = mInflater.inflate(R.layout.layout_chat_send_msg, null);
            }
            //根据自定义的Item布局加载布局
            chatMsgItem = new ChatMsgItem(convertView, chatItemData.MsgType);
//            ChatDataItem.Pic.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View arg0) {
//                    Intent intent=new Intent(activity,ChatActivity.class);
//                    intent.putExtra("userid", chatItem.ChatID);
//                    activity.startActivityForResult(intent, 1);
//                }
//            });
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(chatMsgItem);
        } else {
            chatMsgItem = (ChatMsgItem)convertView.getTag();
        }
        chatMsgItem.setObject(chatMsgItem, chatItemData);
        return convertView;
    }
}
