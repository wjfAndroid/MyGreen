package com.wjf.mygreen;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断版本是否需要动态申请权限
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    testPermissions();
                } else {
                    getnamre();
                }
            }
        });

    }

    //处理相应事件
    public void getnamre() {
//        File file = new File(Environment.getExternalStorageDirectory() + "");
//        System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
//        if (file.exists()) {
//            String[] strs = file.list();
//            for (String s : strs) {
//                System.out.println("s = " + s);
//            }
//        } else {
//            System.out.println("file.exists = " + file.exists());
//        }

        getdata();
    }

    public void getword() {
        try {
            File file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "testFile" + File.separator + "PatchDownloadIntentService.java");
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                String s = null;
                while ((s = br.readLine()) != null) {
                    System.out.println("s = " + s);
                }
                br.close();
                isr.close();
                fis.close();
            } else {
                System.out.println("file.exists() = " + file.exists());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getdata() {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "testFile" + File.separator + "PatchDownloadIntentService.java");
        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                DataInputStream dataInputStream = new DataInputStream(fis);
                byte[] bs = new byte[1024];

                while ((dataInputStream.read(bs))!=0){
                    for (byte b:bs){
                        System.out.println("b = " + b);
                    }
                }
                dataInputStream.close();
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    public void testPermissions() {
        //判断是否拥有权限
        if (ContextCompat.checkSelfPermission(Main3Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(Main3Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                System.out.println("拒绝但是没有选择不再提示");
                ActivityCompat.requestPermissions(Main3Activity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        0x1);
            } else {
                System.out.println(" 第一次   或者    拒绝并且不再提示    ");
                ActivityCompat.requestPermissions(Main3Activity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        0x1);
            }
        } else {
            System.out.println(" 允许之后  再次调用 ");
            getnamre();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0x1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getnamre();
                } else {
                    //申请权限失败
                    Toast.makeText(this, "PERMISSION_deny", Toast.LENGTH_SHORT).show();
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(Main3Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        //申请权限失败并且不再提醒
                        System.out.println("申请权限拒绝并且不再提醒， 弹出对话框 让用户到设置界面修改权限");
                        //自己写dialog
                        getAppDetailSettingIntent(this);
                    }
                }
                break;
        }
    }

    //跳转到系统的该application的设置界面，由于不同手机的权限设置界面的action不同，所以只能跳转到设置界面
    private void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(localIntent);
    }
}
