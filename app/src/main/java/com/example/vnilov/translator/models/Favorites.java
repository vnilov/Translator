package com.example.vnilov.translator.models;

/**
 * Singleton which describes favorites entity of our application.
 *
 * Created by vnilov on 18.03.17.
 */

class Favorites extends Dictionary {

    private static final Favorites ourInstance = new Favorites();

    static Favorites getInstance() {
        return ourInstance;
    }

    private Favorites() {
    }

    @Override
    public void add(Translation translation) {
        this.storage.add(translation);
    }

    public void toggle(Translation translation) {
        int isFavorite = 1;
        if (!this.ifExists(translation)) {
            this.add(translation);
        } else {
            isFavorite = 0;
            this.delete(translation);
        }
        try {
            dbHelper.update(translation, null, isFavorite);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
