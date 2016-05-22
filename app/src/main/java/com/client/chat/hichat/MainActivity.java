package com.client.chat.hichat;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IBtnCallListener{
    private RadioGroup rgs;
    public List<Fragment> fragments = new ArrayList<Fragment>();
    private HashMap<String, RadioButton> fragmentmap = new HashMap<String, RadioButton>();
    private IBtnCallListener mBtnCallListener;
    private int num = 0;
    // private boolean n0, n1, n2, n3, n4, n5, n6 = false;
    private String url, listurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    public void transfermsg(String v, String u) {
        // TODO Auto-generated method stub
//        RadioButton rb = (RadioButton) fragmentmap.get(v);
//        if (v == "ServiceCenterToGtsPilotActivity") {
//            ServiceCenterToGtsPilotActivity wa5 = (ServiceCenterToGtsPilotActivity) fragments
//                    .get(6);
//            if (u != null) {
//                listurl = u;
//                wa5._url = u;
//                url = u;
//            } else {
//                wa5._url = listurl;
//                url = listurl;
//            }
//        } else if (v == "GtsPilotInfoActivity") {
//            GtsPilotInfoActivity wa6 = (GtsPilotInfoActivity) fragments.get(7);
//            wa6._url = u;
//            url = u;
//        }
//        rb.performClick();
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

