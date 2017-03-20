package com.example.vnilov.translator;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.vnilov.translator.models.Translation;
import java.util.Optional;

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

    public void add(Translation translation, Integer atIndex, Optional<Integer> is_favorite) throws Exception {

        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        if (is_favorite.isPresent() && is_favorite.get().equals(1)) {
            values.put(CN_FAVORITE, 1);
        }

        values.put(CN_INPUT, translation.getInput());
        values.put(CN_FROM_LANG, translation.getFromLangCode());
        values.put(CN_TO_LANG, translation.getToLangCode());
        values.put(CN_TRANSLATION, translation.getTranslation());
        values.put(CN_SORT, atIndex); // sort value equals index from the list

        db.insert(TABLE_NAME, null, values);

    }

    // function for updating sort or favorites fields
    public void update(Translation translation, Integer atIndex, Optional<Integer> is_favorite) throws Exception {


        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        if (is_favorite.isPresent() && is_favorite.get().equals(1)) {
            values.put(CN_FAVORITE, 1);
        }

        // update sort field
        values.put(DBHelper.CN_SORT, atIndex);

        String where = DBHelper.CN_INPUT +
                " = ? AND " + DBHelper.CN_FROM_LANG +
                " = ? AND" + DBHelper.CN_TO_LANG
                + " = ?";
        String[] whereArgs = {
                translation.getInput(),
                translation.getFromLangCode(),
                translation.getToLangCode()
        };

        // update value
        db.update(DBHelper.TABLE_NAME, values, where, whereArgs);
    }
}
