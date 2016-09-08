package com.wjf.mygreen;

import android.app.Activity;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wjf.mygreen.entity.DBHelper;
import com.wjf.mygreen.entity.DaoMaster;
import com.wjf.mygreen.entity.DaoSession;
import com.wjf.mygreen.entity.User;
import com.wjf.mygreen.entity.UserDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    UserDao userDao;
    TextView tv;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "lenve.db", null);
//        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
//        DaoSession daoSession = daoMaster.newSession();
//         userDao =daoSession.getUserDao();

//        DBHelper devOpenHelper = new DBHelper(this);
//        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
//        DaoSession daoSession = daoMaster.newSession();
//        userDao = daoSession.getUserDao();


        tv = (TextView) findViewById(R.id.textView);
        lv = (ListView) findViewById(R.id.listView);
        System.out.println("lv.getAdapter()==null 2= " + lv.getAdapter()==null);
    }
    int i = 0;

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume lv.getAdapter()==null 1= " + lv.getAdapter()==null);
    }

    public void add(View v) {
        String[] strs = {"bb", "aa", "cc"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, strs);
        lv.setAdapter(arrayAdapter);
        System.out.println("lv.getAdapter()==null = " + lv.getAdapter().isEmpty());
    }

    public void del(View v) {
        tv.setText("tv");
    }

    public void update(View v) {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }


}
