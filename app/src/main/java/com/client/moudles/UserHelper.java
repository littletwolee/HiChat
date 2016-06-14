package com.client.moudles;

import android.content.Context;
import com.client.models.User;
import com.client.models.UserDao;
import com.client.tools.DBHelper;

import java.util.List;

/**
 * Created by lee on 6/3/16.
 */
public class UserHelper extends DBHelper {
    private UserDao userDao;
    private User user;
    public UserHelper(Context context){
        super(context);
        userDao = daoSession.getUserDao();
    }
    public boolean isAuth(){
        boolean flag = false;
        if(userDao.queryBuilder().where(UserDao.Properties.Islogin.eq(true)).count() > 0){
            flag = true;
        }
        return flag;
    }
    public void loginOut(String username){
        user = findUser(username);
        user.setIslogin(false);
        userDao.update(user);
    }
    public User findUser(String username){
        user = null;
        List<com.client.models.User> users = userDao.queryBuilder()
                .where(UserDao.Properties.Username.eq(username)).build().list();
        if(users != null && users.size() > 0){
            user = users.get(0);
        }
        return user;
    }
    public boolean login(User user){
        boolean flag = false;
        if(userDao.queryBuilder().where(UserDao.Properties.Username.eq(user.getUsername())).buildCount().count() > 0){
            userDao.update(user);
            flag = true;
        }else {
            if(userDao.insert(user) > 0){
                flag = true;
            }
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
    public void updateUser(User user){
        userDao.update(user);
    }
}
