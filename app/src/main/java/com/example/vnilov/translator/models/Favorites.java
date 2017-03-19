package com.example.vnilov.translator.models;

/**
 * Singleton which describes favorites entity of our application.
 *
 * Created by vnilov on 18.03.17.
 */

final class Favorites extends Dictionary {

    private static final Favorites ourInstance = new Favorites();

    static Favorites getInstance() {
        return ourInstance;
    }

    private Favorites() {
    }

    @Override
    public void add(Translation translation) {
        if (this.ifExists(translation)) {
            this.storage.add(translation);
        }
    }


}
