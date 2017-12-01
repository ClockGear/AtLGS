package com.dvo.lgs;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = findViewById(R.id.pager);
    }

    public void menuItemClick(View view) {
        switch (Integer.valueOf((String)view.getTag())) {
            case 1: {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    pager.setBackgroundColor(getColor(R.color.primaryColor));
                } else {
                    pager.setBackgroundColor(getResources().getColor(R.color.primaryColor));
                }
                break;
            }
            case 2: {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    pager.setBackgroundColor(getColor(R.color.primaryDarkColor));
                } else {
                    pager.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
                }
                break;
            }
        }
    }
}
