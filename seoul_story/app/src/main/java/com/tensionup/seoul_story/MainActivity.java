package com.tensionup.seoul_story;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.tensionup.seoul_story.news.NewsFragment;
import com.tensionup.seoul_story.search.SearchFragment;
import com.tensionup.seoul_story.star.StarFragment;
import com.tensionup.seoul_story.state.BackPressCloseHandler;
import com.tensionup.seoul_story.state.WindowState;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    public WindowState windowState;
    private BackPressCloseHandler backHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Fragment fragment = new NewsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add( R.id.fragmentPlace, fragment );
        fragmentTransaction.commit();
        android.support.design.widget.BottomNavigationView navigationView = (android.support.design.widget.BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setSelectedItemId(R.id.navigation_news);
        navigationView.setOnNavigationItemSelectedListener(this);

        windowState = new WindowState(getResources().getString(R.string.window_state_news_root));
        backHandler = new BackPressCloseHandler(this, fragmentManager, fragmentTransaction);
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();

        int width = dm.widthPixels;

        int height = dm.heightPixels;

        Log.v(this.getClass().getName(),"HI"+ Integer.toString(width));
        Log.v(this.getClass().getName(), Integer.toString(height));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.navigation_news:
                fragment = new NewsFragment();
                break;

            case R.id.navigation_star:
                fragment = new StarFragment();
                break;

            case R.id.navigation_search:
                fragment = new SearchFragment();
                break;

//            case R.id.navigation_setting:
//                fragment = new SettingFragment();
//                break;

            default:
                return false;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace( R.id.fragmentPlace, fragment );
        fragmentTransaction.commit();
        return true;
    }
    @Override
    public void onBackPressed() {
        backHandler.onBackPressed(windowState.getWindowState());
    }

    public void moveStarFragment(View v) {
        android.support.design.widget.BottomNavigationView navigationView = (android.support.design.widget.BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setSelectedItemId(R.id.navigation_search);

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace( R.id.fragmentPlace, new SearchFragment() );
        fragmentTransaction.commit();
    }
}
