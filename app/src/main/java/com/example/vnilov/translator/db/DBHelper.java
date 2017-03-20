package com.example.vnilov.translator.db;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vnilov on 19.03.17.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "translator";
    public static final String TABLE_NAME = "data";
    public static final String CN_INPUT = "INPUT";
    public static final String CN_TRANSLATION = "TRANSLATION";
    public static final String CN_FROM_LANG = "FROM_LANG";
    public static final String CN_TO_LANG = "TO_LANG";
    public static final String CN_SORT = "SORT";
    public static final String CN_FAVORITE = "IS_FAVORITE";
    public static final String CN_HISTORY = "IN_HISTORY";

    private String createTableSQL() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                CN_INPUT + " TEXT NOT NULL, " +
                CN_TRANSLATION + " TRANSLATION TEXT NOT NULL, " +
                CN_FROM_LANG + " VARCHAR(3) NOT NULL, " +
                CN_TO_LANG + " VARCHAR(3) NOT NULL, " +
                CN_SORT + " SORT INT, " +
                CN_FAVORITE + " TINYINT(1) DEFAULT 0, " +
                CN_HISTORY + " TINYINT(1) DEFAULT 1, " +
                "PRIMARY KEY (INPUT, FROM_LANG, TO_LANG)" +
                ");";
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(this.createTableSQL());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
