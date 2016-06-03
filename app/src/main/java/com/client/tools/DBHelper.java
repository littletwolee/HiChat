package com.client.tools;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.client.hichat.R;
import com.client.models.DaoMaster;
import com.client.models.DaoSession;

/**
 * Created by lee on 5/24/16.
 */
public class DBHelper {
    private DaoMaster daoMaster;
    private SQLiteDatabase db;
    private DaoMaster.DevOpenHelper helper;
    public DaoSession daoSession;
    public DBHelper(Context context) {
        this.setDaoSession(context);
    }
    private void setDaoSession(Context context){
        if (daoMaster.equals(null)) {
            helper = new DaoMaster.DevOpenHelper(context,
                    context.getResources().getString(R.string.db), null);
            db = helper.getWritableDatabase();
            daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
        }
    }
}