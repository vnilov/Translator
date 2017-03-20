package com.example.vnilov.translator.models;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.example.vnilov.translator.db.DBHelper;

/**
 * Singleton which describes history entity of our application.
 *
 * Created by vnilov on 18.03.17.
 */

class History extends Dictionary {

    private static final History ourInstance = new History();

    static History getInstance() { return ourInstance; }

    private History() {

    }


    @Override
    public void add(Translation translation) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // if translation exists then set it to the top of the list
        if (this.ifExists(translation)) {
            int index = this.storage.size() - 1;
            this.storage.set(index, translation);

            // update sort field
            ContentValues values = new ContentValues();

            values.put(DBHelper.CN_SORT, index);

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
        } else { // else add translation to the top of the history
            this.storage.add(translation);

            // add translation to DB
            ContentValues values = new ContentValues();

            values.put(DBHelper.CN_INPUT, translation.getInput());
            values.put(DBHelper.CN_FROM_LANG, translation.getFromLangCode());
            values.put(DBHelper.CN_TO_LANG, translation.getToLangCode());
            values.put(DBHelper.CN_TRANSLATION, translation.getTranslation());
            values.put(DBHelper.CN_SORT, this.getIndex(translation)); // sort value equals index from the list
        }


    }

}
