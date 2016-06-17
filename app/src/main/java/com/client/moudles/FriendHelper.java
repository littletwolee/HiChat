package com.client.moudles;

import android.content.Context;

import com.client.models.Friends;
import com.client.models.FriendsDao;
import com.client.tools.DBHelper;

import java.util.List;
import java.util.Map;

/**
 * Created by lee on 6/17/16.
 */
public class FriendHelper extends DBHelper{
    private FriendsDao friendsDao;
    private Friends friends;
    private List<Friends> friendsList;
    public FriendHelper(Context context){
        super(context);
        friendsDao = daoSession.getFriendsDao();
    }
    public List<Friends> getFriends(){
        friendsList = friendsDao.queryBuilder().build().list();
        if(friendsList == null || friendsList.size() ==0 ){

        }
        return friendsList;
    }
    public boolean addFriend(Friends friend){
        boolean flag = false;
        if(friendsDao.insert(friend) > 0){
            flag = true;
        }
        return flag;
    }
    public boolean deleteFriend(long id){
        boolean flag = false;
        try{
            friendsDao.deleteByKey(id);
            flag = true;
        }catch (Exception e){}
        return flag;
    }
}
