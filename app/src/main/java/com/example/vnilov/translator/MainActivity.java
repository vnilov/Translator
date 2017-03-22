package com.example.vnilov.translator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    //SharedPreferences settings;

    public static final String PREFERENCES = "preferences" ;
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
        APIHelper api = new APIHelper(this.getApplicationContext());
        Map<String, String> http = api.getLangList();

/*        for (Map.Entry<String, String> entry : http.entrySet())
        {
            System.out.println(entry.getKey() + "/" + entry.getValue());
        }*/
        System.out.println("AFTER");
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
