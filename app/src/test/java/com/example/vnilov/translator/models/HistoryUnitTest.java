package com.example.vnilov.translator.models;

/**
 * Created by vnilov on 19.03.17.
 */

import com.example.vnilov.translator.BuildConfig;
import com.example.vnilov.translator.DBHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Iterator;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest=Config.NONE)

public class HistoryUnitTest {

    private History history;
    private History historyCheck;
    private DBHelper dbHelper;

    Translation translation = new Translation("Hello", "Привет", "en", "ru");
    Translation translationRepeat = new Translation("Hello", "Привет", "en", "ru");
    Translation translationReversed = new Translation("Привет", "Hello", "ru", "en");
    Translation translationSpanish = new Translation("Hola", "привет", "es", "ru");

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

        Iterator<Translation> iterator = this.history.getAll().iterator();
        while (iterator.hasNext()) {
            Translation item = iterator.next();
            //System.out.println(item.getInput() + " idx:" + this.history.getIndex(item));
        }

        try {
            this.history.setStorage(this.dbHelper.get(DBHelper.ALL));
            Iterator<Translation> iterator_db = this.history.getAll().iterator();
            while (iterator_db.hasNext()) {
                Translation item = iterator_db.next();
                //System.out.println(item.getInput() + " idx:" + this.history.getIndex(item));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @After
    public void tearDown() {
        dbHelper.close();
    }
}
