package com.example.vnilov.translator;

/**
 * Created by vnilov on 19.03.17.
 */

class Config {
    private static final Config ourInstance = new Config();

    static Config getInstance() {
        return ourInstance;
    }

    private Config() {
    }

    public static void load() {

    }
}
