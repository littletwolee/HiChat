package com.client.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.client.hichat.R;
import com.client.hichat.chat.ChatActivity;
import com.client.models.ChatsItem;
import com.client.models.ChatsItemData;

import java.util.List;

/**
 * Created by lee on 16-6-4.
 */
public class ChatsAdapter extends BaseAdapter {
    private LayoutInflater mInflater = null;
    private Activity activity = null;
    public List<ChatsItemData> data;
    public ChatsAdapter(Context context)
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
        final ChatsItem chatsItem;
        //如果缓存convertView为空，则需要创建View
        if(convertView == null)
        {
            chatsItem = new ChatsItem();
            //根据自定义的Item布局加载布局
            convertView = mInflater.inflate(R.layout.chat_list_item, null);
            chatsItem.ChatID = data.get(position).ChatID;
            chatsItem.ChatName = (TextView)convertView.findViewById(R.id.chat_name);
            chatsItem.ChatType = data.get(position).ChatType;
            chatsItem.Pic = (ImageView)convertView.findViewById(R.id.pic);
            chatsItem.Pic.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View arg0) {
                    Intent intent=new Intent(activity,ChatActivity.class);
                    intent.putExtra("userid", chatsItem.ChatID);
                    activity.startActivityForResult(intent, 1);
                }
            });
            chatsItem.LastMsg = (TextView)convertView.findViewById(R.id.last_msg);
            //chatItem.setLastTime(data.get(position).LastTime);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(chatsItem);
        }else
        {
            chatsItem = (ChatsItem)convertView.getTag();
        }
        //chatItem.Pic.setBackgroundResource();
        chatsItem.ChatName.setText((String) data.get(position).ChatName);
        chatsItem.LastMsg.setText((String) data.get(position).LastMsg);
        chatsItem.Pic.setImageResource(R.mipmap.ic_launcher);
        return convertView;
    }

}
