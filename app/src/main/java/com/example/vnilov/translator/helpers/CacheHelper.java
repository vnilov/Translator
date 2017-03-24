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
                result = new JSONObject(buffer.toString().trim());
            }

        } catch (Exception e) {
            return null;
        }
        return result;
    }

    // store cache data
    public void addData(JSONObject data, String filename) {
        try {
            File file = new File(ctx.getCacheDir(), filename);
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(data.toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* remove cache functions
       got these functions as the answer for
       http://stackoverflow.com/questions/6898090/how-to-clear-cache-android
       question
    */
    public void deleteCache() {
        try {
            File dir = ctx.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {}
    }

    public boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}
