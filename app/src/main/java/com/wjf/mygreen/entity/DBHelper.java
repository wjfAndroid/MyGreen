package com.wjf.mygreen.entity;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

public class DBHelper extends DaoMaster.OpenHelper {
    public static final String DBNAME = "lenve.db";

    public DBHelper(Context context) {
        super(context, DBNAME, null);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        System.out.println("db = [" + db + "], oldVersion = [" + oldVersion + "], newVersion = [" + newVersion + "]");
        MigrationHelper.migrate(db,UserDao.class);
    }
}