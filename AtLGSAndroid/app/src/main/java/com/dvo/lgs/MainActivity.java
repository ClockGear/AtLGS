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
import com.dvo.lgs.fragments.UserFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private MainPagerAdapter adapter;
    private View fragmentButton1;
    private View fragmentButton2;
    private View fragmentButton3;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentButton1 = findViewById(R.id.fragmentButton1);
        fragmentButton2 = findViewById(R.id.fragmentButton2);
        fragmentButton3 = findViewById(R.id.fragmentButton3);
        pager = findViewById(R.id.pager);
        adapter= new MainPagerAdapter(this, getSupportFragmentManager());
        pager.setAdapter(adapter);
        sharedPref = getSharedPreferences(getString(R.string.def_pref),Context.MODE_PRIVATE);
        //region ViewPager Listener
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        fragmentButton1.setBackgroundColor(getResources().getColor(R.color.primaryColor));
                        fragmentButton2.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
                        fragmentButton3.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
                        break;
                    case 1:
                        fragmentButton1.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
                        fragmentButton2.setBackgroundColor(getResources().getColor(R.color.primaryColor));
                        fragmentButton3.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
                        break;
                    case 2:
                        fragmentButton1.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
                        fragmentButton2.setBackgroundColor(getResources().getColor(R.color.primaryDarkColor));
                        fragmentButton3.setBackgroundColor(getResources().getColor(R.color.primaryColor));
                        break;
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
        }
    }

    public static class MainPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;
        private Context context;

        MainPagerAdapter(Context context, FragmentManager fragmentManager) {
            super(fragmentManager);
            this.context = context;
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show PresenceFragment
                    return PresenceFragment.newInstance();
                case 1: // Fragment # 1 - This will show EventFragment
                    return EventFragment.newInstance();
                case 2: // Fragment # 2 - This will show UserFragment
                    return UserFragment.newInstance();
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return context.getString(R.string.presence);
                case 1:
                    return context.getString(R.string.events);
                case 2:
                    return context.getString(R.string.users);
                default:
                    return null;
            }
        }
    }
}

