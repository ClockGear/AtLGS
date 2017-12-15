package com.dvo.lgs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dvo.lgs.fragments.EventFragment;
import com.dvo.lgs.fragments.PresenceFragment;
import com.dvo.lgs.fragments.StoreFragment;
import com.dvo.lgs.fragments.UserFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private MainPagerAdapter adapter;
    private View fragmentButton1;
    private View fragmentButton2;
    private View fragmentButton3;
    private View fragmentButton4;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(0);
        fragmentButton1 = findViewById(R.id.fragmentButton1);
        fragmentButton2 = findViewById(R.id.fragmentButton2);
        fragmentButton3 = findViewById(R.id.fragmentButton3);
        fragmentButton4 = findViewById(R.id.fragmentButton4);
        pager = findViewById(R.id.pager);
        adapter= new MainPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        sharedPref = getSharedPreferences(getString(R.string.def_pref),Context.MODE_PRIVATE);
        //region ViewPager Listener
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitle(position);
                switch (position) {
                    case 0:
                        fragmentButton1.setBackgroundColor(getResources().getColor(R.color.primaryColor));
                        fragmentButton2.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
                        fragmentButton3.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
                        fragmentButton4.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
                        break;
                    case 1:
                        fragmentButton1.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
                        fragmentButton2.setBackgroundColor(getResources().getColor(R.color.primaryColor));
                        fragmentButton3.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
                        fragmentButton4.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
                        break;
                    case 2:
                        fragmentButton1.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
                        fragmentButton2.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
                        fragmentButton3.setBackgroundColor(getResources().getColor(R.color.primaryColor));
                        fragmentButton4.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
                        break;
                    case 3:
                        fragmentButton1.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
                        fragmentButton2.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
                        fragmentButton3.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
                        fragmentButton4.setBackgroundColor(getResources().getColor(R.color.primaryColor));
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //endregion
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout: {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove(getString(R.string.login_token));
                editor.apply();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    public void menuItemClick(View view) {
        switch (Integer.valueOf((String)view.getTag())) {
            case 1: {
                pager.setCurrentItem(0);
                break;
            }
            case 2: {
                pager.setCurrentItem(1);
                break;
            }
            case 3: {
                pager.setCurrentItem(2);
                break;
            }
            case 4: {
                pager.setCurrentItem(3);
                break;
            }
            default: {
                break;
            }
        }
    }

    public void setTitle(int position) {
        switch (position) {
            case 0: {
                getSupportActionBar().setTitle(getString(R.string.presence));
                break;
            }
            case 1: {
                getSupportActionBar().setTitle(getString(R.string.events));
                break;
            }
            case 2: {
                getSupportActionBar().setTitle(getString(R.string.users));
                break;
            }
            case 3: {
                getSupportActionBar().setTitle(getString(R.string.lgs));
                break;
            }
            default:{
                break;
            }
        }
    }

    public static class MainPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 4;

        MainPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return PresenceFragment.newInstance();
                case 1:
                    return EventFragment.newInstance();
                case 2:
                    return UserFragment.newInstance();
                case 3:
                    return StoreFragment.newInstance();
                default:
                    return null;
            }
        }
    }
}

