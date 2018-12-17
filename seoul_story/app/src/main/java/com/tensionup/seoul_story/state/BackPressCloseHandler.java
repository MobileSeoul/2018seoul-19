package com.tensionup.seoul_story.state;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.tensionup.seoul_story.R;
import com.tensionup.seoul_story.news.NewsFragment;
import com.tensionup.seoul_story.search.SearchFragment;

public class BackPressCloseHandler {
    private long backKeyPressedTime = 0;
    private Toast toast;
    private Activity activity;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    public BackPressCloseHandler(Activity context, FragmentManager fragmentManager, FragmentTransaction fragmentTransaction) {
        this.activity = context;
        this.fragmentManager = fragmentManager;
        this.fragmentTransaction = fragmentTransaction;
    }

    public void onBackPressed(String state) {
        Fragment fragment = null;
        if(state.matches(".*root")) {
            onBackDoublePressed();
        }else {
            if(state.matches(".*news.*")) {
                // news root 프래그먼트로 가기
                fragment = new NewsFragment();
            }else if(state.matches(".*search.*")) {
                fragment = new SearchFragment();

            }
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace( R.id.fragmentPlace, fragment );
            fragmentTransaction.commit();
        }


    }

    private void onBackDoublePressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis(); showGuide();
            return;
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) {
            activity.finishAffinity();
            toast.cancel();
        }
    }

    private void showGuide() {
        toast = Toast.makeText(activity, "\'뒤로가기\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }

}


