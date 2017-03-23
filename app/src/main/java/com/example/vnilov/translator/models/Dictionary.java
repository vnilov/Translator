package com.example.vnilov.translator.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import android.app.Application;
import android.content.Context;

import com.example.vnilov.translator.DBHelper;

/**
 * Dictionary abstract class
 * This is used as core from History and Favorites singletons
 *
 * Created by vnilov on 18.03.17.
 */

abstract class Dictionary {

    protected List<Translation> storage = new ArrayList<Translation>();

    protected Context ctx;

    // I couldn't understand the warnings in my Favorites and History singletons about memory leak,
    // if I kept context instance in it.
    protected DBHelper dbHelper;


    protected void init(Context context) {
        this.ctx = context.getApplicationContext();
        this.dbHelper = new DBHelper(this.ctx);
    }

    // set dbHelper if it needed (especially for tests)
    void setDbHelper(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    // set translation storage
    void setStorage(List<Translation> storage) {
        this.storage = storage;
    }

    // check if Translation exists
    boolean ifExists(Translation translation) {
        Optional<Translation> t = this.storage.stream()
                .filter(item -> item.getTranslationKey().equals(translation.getTranslationKey()))
                .findFirst();
        return t.isPresent();
    }

    int getIndex(Translation translation) {
        // setup the default value
        int idx = -1;
        // run over storage to find a translation from param value
        Optional<Translation> t = this.storage.stream()
                .filter(item -> item.getTranslationKey().equals(translation.getTranslationKey()))
                .findFirst();

        // if we found a translation
        if (t.isPresent()) {
            idx = this.storage.indexOf(t.get());
        }

        return idx;
    }

    // add translation to the dictionary using Translation entity
    void add(Translation translation) {}


    // remove translation from the dictionary using Translation entity
    void delete(Translation translation) {
        Iterator<Translation> iterator = this.storage.iterator();
        while (iterator.hasNext()) {
            Translation item = iterator.next();
            if (translation.getTranslationKey().equals(item.getTranslationKey())) {
                iterator.remove();
            }
        }
    }

    // remove all entities from the dictionary
    void deleteAll() {
        this.storage.clear();
    }

    // get the translation from the dictionary
    Optional<Translation> get(String name, String from, String to) {
        return this.storage.stream()
                .filter(item -> item.getTranslationKey().equals(item.getKey(name, from, to)))
                .findFirst();
    }

    // get all translations from the dictionary
    List<Translation> getAll() {
        return this.storage;
    }

}
