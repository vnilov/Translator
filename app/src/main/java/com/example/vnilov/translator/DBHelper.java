package com.example.vnilov.translator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.vnilov.translator.models.Translation;

import java.util.ArrayList;
import java.util.List;

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
    public static final int ALL = 1;
    public static final int HISTORY = 2;
    public static final int FAVORITES = 3;
    private int maxSort;

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
        // get max sort value on object's initiating
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(this.createTableSQL());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void add(Translation translation, Integer atIndex, int is_favorite) throws Exception {

        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        if (is_favorite > 0) {
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
    public void update(Translation translation, Integer atIndex, int is_favorite) throws Exception {


        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        if (is_favorite > 0) {
            values.put(CN_FAVORITE, 1);
        }

        if (atIndex != null) {
            // update sort field
            values.put(CN_SORT, atIndex);
        }

        String where = CN_INPUT +
                " = ? AND " + CN_FROM_LANG +
                " = ? AND " + CN_TO_LANG
                + " = ?";
        String[] whereArgs = {
                translation.getInput(),
                translation.getFromLangCode(),
                translation.getToLangCode()
        };

        // update value
        db.update(TABLE_NAME, values, where, whereArgs);
    }

    public List<Translation> get(int type) throws Exception {

        // initialize result list
        List<Translation> result = new ArrayList<Translation>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                CN_INPUT, CN_TRANSLATION, CN_FROM_LANG, CN_TO_LANG, CN_FAVORITE, CN_SORT
        };

        String sortOrder =
                CN_SORT + " ASC";

        String where;
        String[] whereArgs = new String[1];

        switch (type) {
            case FAVORITES:
                where = CN_FAVORITE + " = ?";
                whereArgs[0] = "1";
                break;
            case HISTORY:
                where = CN_HISTORY + " = ?";
                whereArgs[0] = "1";
                break;
            case ALL:
            default:
                where = null;
                whereArgs = null;
                break;
        }

        Cursor cursor  = db.query(
                TABLE_NAME,
                projection,
                where,
                whereArgs,
                null,
                null,
                sortOrder
        );

        if (cursor.moveToFirst()){
            do {
                Translation translation = new Translation(
                        cursor.getString(cursor.getColumnIndexOrThrow(CN_INPUT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(CN_TRANSLATION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(CN_FROM_LANG)),
                        cursor.getString(cursor.getColumnIndexOrThrow(CN_TO_LANG))
                );
                if (Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(CN_FAVORITE))) == 1) {
                    translation.setFavorite();
                }
                result.add(translation);

            } while(cursor.moveToNext());
        }

        cursor.close();

        return result;
    }

    /* Get max value of sort in our table.
     * We'll use for our next add/update queries.
     * We could use autoincrement field, but if we did this,
     * we'll make 2 queries(delete and insert) instead one update;
     * I chose the first variant;
    */
    public int getMaxSortDB() {

        int result = -1;

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = { CN_SORT };
        String sortOrder = CN_SORT + " DESC";

        Cursor cursor  = db.query(TABLE_NAME, projection, null, null, null, null, sortOrder, "1");

        if (cursor.moveToFirst()) {
            result = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(CN_SORT)));
        }

        cursor.close();

        return result;
    }

    // getter for maxSort
    public int getMaxSort() {
        return this.maxSort;
    }

    // setter
    public void setMaxSort(int value) {
        this.maxSort = value;
    }
}
