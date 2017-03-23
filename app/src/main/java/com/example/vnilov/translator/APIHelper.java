package com.example.vnilov.translator;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Created by vnilov on 22.03.17.
 */

public class APIHelper {

    /*
    * Yandex.Translate configs
    * */
    private static final String API_KEY = "trnsl.1.1.20170322T110839Z.cd5ddcfcdc7deff8.caf3d1354c8fb119afff2d315b1e98d2710077b3";
    private static final String API_URL = "https://translate.yandex.net/api/v1.5/tr.json/";
    private static final String API_LANG = "ru";

    // context variable
    private static Context ctx;
    // volley requests' variable
    private RequestQueue mRequestQueue;


    /*
    * We make our singleton lazy loaded and thread-safe
    * */

    private static class APIHelperHolder {
        private static Context context;
        public static APIHelper instance = new APIHelper(context);
    }
    public static APIHelper getInstance() {
        return APIHelperHolder.instance;
    }

    private APIHelper(Context context) {
        ctx = context;
    }

    // get queue
    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return mRequestQueue;
    }

    /*
    * make an url for requests
    * */
    private String makeURL(String methodName, Map<String, String> params) {
        String url = API_URL + methodName + "?key=" + API_KEY;
        String[] lambda_obj = new String[1];

        lambda_obj[0] = url;
        params.forEach((k, v)-> lambda_obj[0] = lambda_obj[0] + "&" + k + "=" + v);
        url = lambda_obj[0];

        return url;
    }

    /*
    *  Request
    * */
    public void sendRequest(String methodName, Map<String, String> url_params, Map<String, String> params, final APIHelperListener<String> listener) {

        String url = this.makeURL(methodName, url_params);
        JSONObject data;

        if (params == null) {
            data = null;
        } else {
            data = new JSONObject(params);
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, data,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (null != response.toString())
                        listener.getResult(response.toString());
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (null != error.networkResponse)
                        listener.getResult(null);
                }
            }
        );

        this.getRequestQueue().add(request);
    }
    
}
