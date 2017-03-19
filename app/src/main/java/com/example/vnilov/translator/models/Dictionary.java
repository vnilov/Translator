package com.example.vnilov.translator.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Dictionary abstract class
 * This is used as core from History and Favorites singletons
 *
 * Created by vnilov on 18.03.17.
 */

abstract class Dictionary {

    protected final List<Translation> storage = new ArrayList<Translation>();

    // check if Translation exists
    boolean ifExists(Translation translation) {
        Optional<Translation> t = this.storage.stream()
                .filter(item -> item.getTranslationKey().equals(translation.getTranslationKey()))
                .findFirst();
        return t.isPresent();
    };

    Integer getIndex(Translation translation) {
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
    void add(Translation translation) {};


    // remove translation from the dictionary using Translation entity
    void delete(Translation translation) {
        Iterator<Translation> iterator = this.storage.iterator();
        while (iterator.hasNext()) {
            Translation item = iterator.next();
            if (translation.getTranslationKey().equals(item.getTranslationKey())) {
                iterator.remove();
            }
        }
    };

    // remove all entities from the dictionary
    void deleteAll() {
        this.storage.clear();
    };

    // get the translation from the dictionary
    Optional<Translation> get(String name, String lang) {
        return this.storage.stream()
                .filter(item -> item.getTranslationKey().equals(item.getKey(name, lang)))
                .findFirst();
    };

    // get all translations from the dictionary
    List<Translation> getAll() {
        return this.storage;
    };
}
