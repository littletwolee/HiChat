package com.client.moudles;

import android.content.Context;

import com.client.hichat.R;
import com.client.models.DaoMaster;
import com.client.models.UserDao;
import com.client.tools.DBHelper;

import java.util.List;

/**
 * Created by lee on 6/3/16.
 */
public class UserHelper extends DBHelper {
    private UserDao userDao;
    public UserHelper(Context context){
        super(context);
        userDao = daoSession.getUserDao();
    }
    public boolean isAuth(){
        boolean flag = false;
        if(userDao.queryBuilder().where(UserDao.Properties.Lastlogin.eq(true)).buildCount().count() > 0){
            flag = true;
        }
        return flag;
    }
    public com.client.models.User findUser(){
        com.client.models.User user = null;
        List<com.client.models.User> users = userDao.queryBuilder().build().list();
        if(users.size() > 0){
            user = users.get(0);
        }
        return user;
    }
    public boolean createUser(com.client.models.User user){
        boolean flag = false;
        if(userDao.insert(user) > 0){
            flag = true;
        }
        return flag;
    }

    public boolean deleteUser(){
        boolean flag = false;
        userDao.deleteAll();
        if(userDao.queryBuilder().buildCount().count() <= 0){
            flag = true;
        }
        return flag;
    }
    public void updateUser(com.client.models.User user){
        userDao.update(user);
    }
}
