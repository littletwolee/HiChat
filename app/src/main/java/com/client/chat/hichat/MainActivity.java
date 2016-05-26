package com.client.chat.hichat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RadioGroup rgs;
    public List<Fragment> fragments = new ArrayList<Fragment>();
    private HashMap<String, RadioButton> fragmentmap = new HashMap<String, RadioButton>();
    private IBtnCallListener mBtnCallListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
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

