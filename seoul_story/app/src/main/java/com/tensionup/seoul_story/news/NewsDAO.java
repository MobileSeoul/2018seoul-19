package com.tensionup.seoul_story.news;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;


public class NewsDAO {
    public static NewsCardItem addItem(Context context, String theme, String title, String date, String href) {
        NewsCardItem item = new NewsCardItem();

        int resImgID = context.getResources().getIdentifier("category_"+theme, "drawable", context.getPackageName());
        int resTitleID = context.getResources().getIdentifier("category_title_"+theme, "string", context.getPackageName());

        item.setBgImage(resImgID);
        item.setCategoryTitle(resTitleID);
        item.setNewsTitle(title);
        item.setDate(date);
        item.setHref(href);

        return item;
    }

    public static NewsCardItem addItem(Bitmap img, String url) {
        NewsCardItem item = new NewsCardItem();

        item.setImageCard(img);
        item.setImageCardURL(url);

        return item;
    }
    public static void setTextColorWhite(TextView textView) {
        textView.setTextColor(Color.WHITE);
    }

}
