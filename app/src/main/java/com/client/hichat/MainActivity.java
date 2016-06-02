package com.client.hichat;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.client.models.DaoMaster;
import com.client.models.DaoSession;
import com.client.models.User;
import com.client.models.UserDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

public class MainActivity extends AppCompatActivity {
    private RadioGroup rgs;
    public List<Fragment> fragments = new ArrayList<Fragment>();
    private HashMap<String, RadioButton> fragmentmap = new HashMap<String, RadioButton>();
    private IBtnCallListener mBtnCallListener;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragments.add(new ChatsFragment());
        fragments.add(new ContactsFragment());
        fragments.add(new DiscoverFragment());
        fragments.add(new AboutMeFragment());
        rgs = (RadioGroup) findViewById(R.id.tabs_rg);
        fragmentmap.put("chats", (RadioButton) findViewById(R.id.tab_rb_chats));
        fragmentmap.put("contacts", (RadioButton) findViewById(R.id.tab_rb_contacts));
        fragmentmap.put("discover", (RadioButton) findViewById(R.id.tab_rb_discover));
        fragmentmap.put("about_me", (RadioButton) findViewById(R.id.tab_rb_about_me));
        FragmentTabAdapter tabAdapter = new FragmentTabAdapter(this, fragments, R.id.tab_content, rgs);
        tabAdapter
                .setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener() {
                    @Override
                    public void OnRgsExtraCheckedChanged(RadioGroup radioGroup,
                                                         int checkedId, int index) {

                    }

                });
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        userDao = daoSession.getUserDao();
        User note = new User(null, "123", "123", new Date());
        //userDao.insert(note);
        search();
    }
    private void search() {
        // Query 类代表了一个可以被重复执行的查询
        Query query = userDao.queryBuilder()
                .where(UserDao.Properties.Username.eq("123"))
                .build();

//      查询结果以 List 返回
        List notes = query.list();
        // 在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }
    @Override
    public void onAttachFragment(Fragment fragment) {
        // TODO Auto-generated method stub
        try {
            mBtnCallListener = (IBtnCallListener) fragment;
        } catch (Exception e) {
            // TODO: handle exception
        }
        super.onAttachFragment(fragment);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

