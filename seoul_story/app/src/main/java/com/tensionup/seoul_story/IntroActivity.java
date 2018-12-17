package com.tensionup.seoul_story;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;

import com.tensionup.seoul_story.util.FileManagerUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends Activity{

    private Handler mHandler;
    private Runnable mRunnable;

    private boolean m_bIsDoneLoadCategoryList;
    private boolean m_bIsDoneLoadCategoryImgs;

    ArrayList<Bitmap> m_categoryImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        m_categoryImages = new ArrayList<>();

        m_bIsDoneLoadCategoryList = false;
        m_bIsDoneLoadCategoryImgs = false;

        changeAcitivity();
    }

    private void LoadFavoriteCategoryList() {
        try {

            if( FileManagerUtil.IsExistCategoryState((this))) {
                List<String> categoryList = FileManagerUtil.readXmlCategoryState(this);

                FileManagerUtil.setFavoriteCategory((ArrayList<String>)categoryList);
            } else {
                List<String> defaultCategoryList = new ArrayList<>();
                defaultCategoryList.add(getString(R.string.category_title_en_gov));
                defaultCategoryList.add(getString(R.string.category_title_en_sculture));
                defaultCategoryList.add(getString(R.string.category_title_en_welfare));

                FileManagerUtil.writeXmlCategoryState(this, defaultCategoryList);

                FileManagerUtil.setFavoriteCategory((ArrayList<String>)defaultCategoryList);
            }

            m_bIsDoneLoadCategoryList = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void LoadFavoriteCardBackgroundImages() {
        m_bIsDoneLoadCategoryImgs = FileManagerUtil.LoadFavoriteCardBackgroundImages(getResources());

    }

    private void changeAcitivity() {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if( !m_bIsDoneLoadCategoryList || !m_bIsDoneLoadCategoryImgs ) {
                    mHandler.postDelayed(mRunnable, 3000);
                } else {
                    Intent intent = new Intent(getApplicationContext()
                            , MainActivity.class);
                    startActivity(intent);
                }
            }
        };

        LoadFavoriteCategoryList();
        LoadFavoriteCardBackgroundImages();

        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 3000);
    }
}
