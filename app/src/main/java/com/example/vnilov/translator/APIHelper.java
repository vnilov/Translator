package com.example.vnilov.translator;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by vnilov on 22.03.17.
 */

public class APIHelper {

    private static final String API_KEY = "trnsl.1.1.20170322T110839Z.cd5ddcfcdc7deff8.caf3d1354c8fb119afff2d315b1e98d2710077b3";
    private static final String API_URL = "https://translate.yandex.net/api/v1.5/tr.json/";
    private static final String API_LANG = "ru";

    private Context ctx;

    APIHelper(Context context) {
        this.ctx = context;
    }

    public Map<String, String> getLangList() {

        String url =  API_URL + "getLangs?key=" + API_KEY + "&ui=" + API_LANG;

        Map<String, String> result = new HashMap<String, String>();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            response = response.getJSONObject("langs");
                            Iterator<String> iterator = response.keys();
                            while (iterator.hasNext()) {
                                String key = iterator.next();
                                try {
                                    result.put(key, response.get(key).toString());
                                    System.out.println(key + "/" + response.get(key).toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        Volley.newRequestQueue(ctx).add(request);

        return result;
    }



}
