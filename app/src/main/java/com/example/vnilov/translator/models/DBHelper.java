package com.example.vnilov.translator.models;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vnilov on 19.03.17.
 */

class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "translator";
    private static final String HISTORY_TABLE_NAME = "history";
    private static final String FAVORITES_TABLE_NAME = "favorites";


    private String createTableSQL(String tableName) {
        return "CREATE TABLE " + tableName + " (" +
                "INPUT TEXT, " +
                "TRANSLATION TEXT, " +
                "FROM_LANG VARCHAR(3), " +
                "TO_LANG VARCHAR(3), " +
                "UNIQUE (INPUT, FROM_LANG) ON CONFLICT REPLACE);";
    }

    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(this.createTableSQL(HISTORY_TABLE_NAME));
        db.execSQL(this.createTableSQL(FAVORITES_TABLE_NAME));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
