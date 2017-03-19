package com.example.vnilov.translator.models;

/**
 * Created by vnilov on 19.03.17.
 */

import android.os.Build;
import android.support.annotation.RequiresApi;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HistoryUnitTest {

    private History history;

    Translation translation = new Translation("Hello", "Привет", "en", "ru");
    Translation translationRepeat = new Translation("Hello", "Привет", "en", "ru");
    Translation translationReversed = new Translation("Привет", "Hello", "ru", "en");

    @Before
    public void init() {
        this.history = History.getInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Test
    public void testAdd() {
        // add 2 elements
        this.history.add(this.translation);
        this.history.add(this.translationReversed);
        assertEquals(this.history.getAll().size(), 2);
        // get the index of the second one
        int idx = (this.history.getIndex(this.translationReversed));
        // it shouldn't be null
        assertNotNull(idx);
        // it should have index = 1
        assertEquals(idx, 1);
        // add the first element again
        this.history.add(this.translation);
        // the size of the storage should be the same
        assertEquals(this.history.getAll().size(), 2);
        // check the index of the first element
        idx = (this.history.getIndex(this.translation));
        // this should be change because we renew it
        assertEquals(idx, 1);
        // add another object with the same content as the first one
        this.history.add(this.translationRepeat);
        // the size of the storage should be the same
        assertEquals(this.history.getAll().size(), 2);
    }

}
