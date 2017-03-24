package com.example.vnilov.translator.models;

/**
 * Created by vnilov on 19.03.17.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

import com.example.vnilov.translator.BuildConfig;
import com.example.vnilov.translator.helpers.DBHelper;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest=Config.NONE)

public class HistoryUnitTest {

    private History history;
    private History historyCheck;
    private DBHelper dbHelper;

    private Translation translation = new Translation("Hello", "Привет", "en", "ru");
    private Translation translationRepeat = new Translation("Hello", "Привет", "en", "ru");
    private Translation translationReversed = new Translation("Привет", "Hello", "ru", "en");
    private Translation translationSpanish = new Translation("Hola", "привет", "es", "ru");

    @Before
    public void init() {

        this.dbHelper = new DBHelper(RuntimeEnvironment.application);
        dbHelper.setMaxSort(dbHelper.getMaxSortDB());
        this.history = History.getInstance();
        this.historyCheck = History.getInstance();
        this.history.setDbHelper(dbHelper);
    }

    @Test
    public void checkSingleton() {
        assertEquals(history, historyCheck);
    }

    @Test
    public void testAdd() {
        // add 2 elements
        this.history.add(this.translation); // idx = 0
        this.history.add(this.translationRepeat); // idx = 0
        this.history.add(this.translationReversed); // idx = 1
        assertEquals(this.history.getAll().size(), 2);
        // get the index of the second one
        int idx = (this.history.getIndex(this.translationReversed)); // 1
        // it shouldn't be null
        assertNotNull(idx);
        // it should have index = 1
        assertEquals(idx, 1);
        // add the first element again
        this.history.add(this.translation); // idx = 1
        // the size of the storage should be the same
        assertEquals(this.history.getAll().size(), 2);
        // check the index of the first element
        idx = (this.history.getIndex(this.translation));
        // this should be change because we renew it
        assertEquals(idx, 1);
        // add another object with the same content as the first one
        this.history.add(this.translationReversed); // idx = 1
        idx = (this.history.getIndex(this.translation)); // 0
        int idxReversed = (this.history.getIndex(this.translationReversed));
        assertEquals(idx, 0);
        assertEquals(idxReversed, 1);
        // the size of the storage should be the same
        assertEquals(this.history.getAll().size(), 2);

        this.history.add(this.translationSpanish); // idx = 2
        assertEquals(this.history.getAll().size(), 3);

        this.history.add(this.translation); // idx = 2

        try {
            // test DB values
            this.history.setStorage(this.dbHelper.get(DBHelper.ALL));
        } catch (Exception e) {
            e.printStackTrace();
        }

        int translationIdx = this.history.getIndex(translation);
        int translationRepeatIdx = this.history.getIndex(translationRepeat);
        int translationReversedIdx = this.history.getIndex(translationReversed);
        int translationSpanishIdx = this.history.getIndex(translationSpanish);

        assertEquals(translationIdx, 2);
        assertEquals(translationRepeatIdx, 2);
        assertEquals(translationReversedIdx, 0);
        assertEquals(translationSpanishIdx, 1);

    }

    @After
    public void tearDown() {
        dbHelper.close();
    }
}
