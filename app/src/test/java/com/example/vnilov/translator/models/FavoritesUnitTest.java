package com.example.vnilov.translator.models;

/**
 * Created by vnilov on 18.03.17.
 */


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FavoritesUnitTest {

    private Favorites favorites;

    Translation translation = new Translation("Hello", "Привет", "en", "ru");
    Translation translationRepeat = new Translation("Hello", "Привет", "en", "ru");
    Translation translationReversed = new Translation("Привет", "Hello", "ru", "en");

    @Before
    public void init() {
        this.favorites = Favorites.getInstance();
    }

    @Test
    public void testAdd() {
        this.favorites.add(this.translation);
        this.favorites.add(this.translationReversed);
        this.favorites.add(this.translationRepeat);
        assertEquals(this.favorites.getAll().size(), 2);
    }

    @Test
    public void testRemove() {
        if (this.favorites.getAll().size() > 0) { // first test was passed
            this.favorites.delete(this.translation);
            assertEquals(this.favorites.getAll().size(), 1);
        } else {
            this.favorites.add(this.translation);
            this.favorites.delete(this.translationReversed);
            assertEquals(this.favorites.getAll().size(), 1);
            this.favorites.delete(this.translation);
            assertEquals(this.favorites.getAll().size(), 0);
        }

    }

    @Test
    public void testDeleteAll()
    {
        this.favorites.add(this.translation);
        this.favorites.add(this.translation);
        this.favorites.add(this.translation);
        this.favorites.deleteAll();
        assertEquals(this.favorites.getAll().size(), 0);
    }

}
