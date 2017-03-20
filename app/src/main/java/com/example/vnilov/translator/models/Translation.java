package com.example.vnilov.translator.models;

/**
 * Class which describes Translation entity.
 * This is the main entity used in our application.
 *
 * Created by vnilov on 18.03.17.
 */
 public final class Translation {

    // Input text for translate
    private String input;
    // Result of the translation
    private String translation;
    // Language code used as input language
    private String fromLangCode;
    // Language code used as destination language
    private String toLangCode;
    // Have this translation been added to favorites
    private boolean isFavorite = false;


    /*
    * Setters and Getters block
    */
    public String getInput() {
        return this.input;
    }

    public void setInput(String input) {
        this.input = input.toLowerCase();
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation.toLowerCase();
    }

    public String getFromLangCode() {
        return fromLangCode;
    }

    public void setFromLangCode(String fromLangCode) {
        this.fromLangCode = fromLangCode.toLowerCase();
    }

    public String getToLangCode() {
        return toLangCode;
    }

    public void setToLangCode(String toLangCode) {
        this.toLangCode = toLangCode.toLowerCase();
    }


    // Constructor
    public Translation(String input, String translation, String fromLangCode, String toLangCode) throws IllegalArgumentException {
        this.setInput(input);
        this.setTranslation(translation);
        this.setFromLangCode(fromLangCode);
        this.setToLangCode(toLangCode);
    }

    // make key from the instance of the translation
    public String getTranslationKey() {
        return this.getKey(this.getInput(), this.getFromLangCode(), this.getToLangCode());
    }

    public String getKey(String input, String from, String to) throws IllegalArgumentException {
        // make key from parameters
        return input.toLowerCase() + "::" + from.toLowerCase() + "::" + to.toLowerCase();
    }

    public boolean isFavorite() {
        return this.isFavorite;
    }

    public void setFavorite() {
        this.isFavorite = true;
    }
}
