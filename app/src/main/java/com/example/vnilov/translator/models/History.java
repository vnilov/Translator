package com.example.vnilov.translator.models;


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
    void add(Translation translation) {

        int index = dbHelper.getMaxSort() + 1;
        // if translation exists then set it to the top of the list
        if (this.ifExists(translation)) {
            this.delete(translation);
            // update in SQLite
            try {
                dbHelper.update(translation, index, 0);
                dbHelper.setMaxSort(index);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                dbHelper.add(translation, index, 0);
                dbHelper.setMaxSort(index);
            } catch (Exception e) {
                e.getMessage();
            }
        }

        // add translation to the top of the history
        this.storage.add(translation);
    }

}
