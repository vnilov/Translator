package com.example.vnilov.translator.models;

/**
 * Created by vnilov on 18.03.17.
 */

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TranslationUnitTest {

    private Translation translation;

    @Before
    public void init() {
        translation = new Translation("Привет", "Hello", "ru", "en");
    }

    @Test
    public void wordGetters_isCorrect() throws Exception {
        assertEquals(this.translation.getInput(), "привет");
        assertEquals(this.translation.getTranslation(), "hello");
        assertEquals(this.translation.getFromLangCode(), "ru");
        assertEquals(this.translation.getToLangCode(), "en");
    }

    @Test
    public void wordSetters_isCorrect() throws Exception {
        this.translation.setInput("Hola");
        assertEquals(this.translation.getInput(), "hola");
        this.translation.setTranslation("Bonjour");
        assertEquals(this.translation.getTranslation(), "bonjour");
        this.translation.setFromLangCode("es");
        assertEquals(this.translation.getFromLangCode(), "es");
        this.translation.setToLangCode("fr");
        assertEquals(this.translation.getToLangCode(), "fr");
    }


    @Test
    public void testGetKey() {
        assertEquals(this.translation.getKey("param1", "param2", "param3"), "param1::param2::param3");
    }

    @Test
    public void testGetTranslationKey() {
        this.translation.setInput("Hola");
        this.translation.setFromLangCode("es");
        this.translation.setToLangCode("ru");
        assertEquals(this.translation.getTranslationKey(), "hola::es::ru");
    }

    @Test
    public void testIsFavorite() {
        assertFalse(this.translation.isFavorite());
        this.translation.setFavorite();
        assertTrue(this.translation.isFavorite());
    }

}
