package com.example.xybrowser.sq;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xy on 2017/4/17.
 */

public class SQLDatabaseHelper extends SQLiteOpenHelper {
    //建表
    public static final String CREATE_BOOKMARKS = "create table Bookmarks("
            + "id integer primary key autoincrement, "
            + "url text,"
            + "chinese text) ";
    private Context sqcontext;

    public SQLDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                             int version) {
        super(context, name, factory, version);
        sqcontext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOKMARKS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

