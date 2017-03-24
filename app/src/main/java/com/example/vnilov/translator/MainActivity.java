package com.example.vnilov.translator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.vnilov.translator.helpers.APIHelper;
import com.example.vnilov.translator.helpers.APIHelperListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    //SharedPreferences settings;

    public static final String PREFERENCES = "preferences";
    public static final String LAGN_FROM = "langFrom";
    public static final String LAGN_TO = "langTo";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);

                    try {
                        File file = new File(getCacheDir(), "getLangs");
                        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                        String line;
                        StringBuffer buffer = new StringBuffer();
                        while ((line = input.readLine()) != null) {
                            buffer.append(line);
                        }
                        System.out.println(buffer.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //settings = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        setContentView(R.layout.activity_main);

        Map<String, String> getLangUrlParams = new HashMap<String, String>();
        getLangUrlParams.put("ui", "ru");


        APIHelper apiHelper = APIHelper.getInstance();
        apiHelper.init(this.getApplicationContext());
        apiHelper.sendRequest("getLangs", getLangUrlParams, null, new APIHelperListener<JSONObject>() {
            @Override
            public void getResult(JSONObject object) {

            }
        });

        Map<String, String> getLangUrlParams_ = new HashMap<String, String>();
        getLangUrlParams_.put("lang", "ru-en");
        String text = "text=" + "Привет, чтобы мне сказать вам";

        String TAG = "MyActivity";
        apiHelper.sendRequest("translate", getLangUrlParams_, text, new APIHelperListener<JSONObject>() {
            @Override
            public void getResult(JSONObject object) {
                Log.d(TAG, object.toString());
            }
        });

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
