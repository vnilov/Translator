package com.example.vnilov.translator.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton which describes history entity of our application.
 *
 * Created by vnilov on 18.03.17.
 */

final class History extends Dictionary {

    private static final History ourInstance = new History();

    static History getInstance() { return ourInstance; }

    private History() {

    }


    @Override
    public void add(Translation translation) {
        // if translation exists then remove it from the list
        if (this.ifExists(translation)) {
            this.delete(translation);
        }
        // now add translation to the top of the history
        this.storage.add(translation);
    }

}
