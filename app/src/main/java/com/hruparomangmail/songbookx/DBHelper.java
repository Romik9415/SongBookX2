package com.hruparomangmail.songbookx;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 * Created by Roman Khrupa on 12.03.2016.
 * SQlite live here .
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=4;
    private static final String DATABASE_NAME="songBookX.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here
        String CREATE_TABLE_EXPENSE ="CREATE TABLE "
                + Card.TABLE  + " ("
                + Card.KEY_ID  + " TEXT PRIMARY KEY,"
                + Card.KEY_TITLE + " TEXT, "
                + Card.KEY_LYRICS + " TEXT,"
                + Card.KEY_AUTHOR + " TEXT, "
                + Card.KEY_CATEGORY + " TEXT) ";

        db.execSQL(CREATE_TABLE_EXPENSE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Card.TABLE);
        onCreate(db);
    }

}
