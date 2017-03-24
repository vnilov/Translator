package com.example.vnilov.translator.helpers;

import android.content.Context;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;


class CacheHelper {

    // context variable
    private static Context ctx;

    /*
    * The fastest singleton constructor
    */

    private static class CacheHelperHolder {
        static CacheHelper instance = new CacheHelper();
    }
    public static CacheHelper getInstance() {
        return CacheHelper.CacheHelperHolder.instance;
    }

    /* */

    public void init(Context context) {
        ctx = context;
    }


    // get data from cache or return null
    public JSONObject getData(String filename) {

        JSONObject result = null;

        try {

            File file = new File(ctx.getCacheDir(), filename);
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line);
            }
            if (buffer != null) {
                result = new JSONObject(buffer.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addData(String data, String filename) {
        try {
            File file = new File(ctx.getCacheDir(), filename);
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
