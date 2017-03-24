package com.example.vnilov.translator.helpers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.ExecutionException;

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
        public static APIHelper instance = new APIHelper();
    }
    public static APIHelper getInstance() {
        return APIHelperHolder.instance;
    }


    public void init(Context context) {
        ctx = context;
    }

    // get queue
    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(ctx);
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
    public void sendRequest(String methodName, Map<String, String> url_params, String body, final APIHelperListener<JSONObject> listener) {

        String url = this.makeURL(methodName, url_params);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (null != response)
                        listener.getResult(response);
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (null != error.networkResponse)
                        listener.getResult(null);
                }
            }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }

            @Override
            public byte[] getBody() {
                try {
                    return body == null ? null : body.getBytes("utf-8");
                } catch (Exception e) {
                    return null;
                }
            }

        };

        this.getRequestQueue().add(request);
    }
    
}
