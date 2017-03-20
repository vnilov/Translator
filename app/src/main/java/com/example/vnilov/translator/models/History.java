package com.example.vnilov.translator.models;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.example.vnilov.translator.DBHelper;

/**
 * Singleton which describes history entity of our application.
 *
 * Created by vnilov on 18.03.17.
 */

public class History extends Dictionary {

    private static final History ourInstance = new History();

    static History getInstance() { return ourInstance; }

    private History() {

    }


    @Override
    public void add(Translation translation) {

        // get new index
        int index = this.storage.size();
        // if translation exists then set it to the top of the list
        if (this.ifExists(translation)) {
            this.delete(translation);
            // update in sqlite
            try {
                dbHelper.update(translation, index, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // add translation to the top of the history
        this.storage.add(translation);
        try {
            dbHelper.add(translation, index, null);
        } catch (Exception e) {
            e.getMessage();
        }


    }

}
