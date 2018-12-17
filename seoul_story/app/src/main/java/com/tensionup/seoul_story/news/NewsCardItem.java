package com.tensionup.seoul_story.news;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class NewsCardItem {
    private int bgImage;
    private int categoryTitle;
    private String newsTitle;
    private String newsHref;
    private Bitmap imageCard;

    private String imageCardURL;

    private String date;

    public int getBgImage() {
        return bgImage;
    }

    public int getCategoryTitle() {
        return categoryTitle;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setBgImage(int bgImage) {
        this.bgImage = bgImage;
    }

    public void setCategoryTitle(int categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public void setDate(String date) { this.date = date; }


    public String getDate() { return date; }

    public void setHref(String href) {
        this.newsHref = href;
    }

    public String getHref() {
        return this.newsHref;
    }

    public void setImageCard(Bitmap imageCard) {
        this.imageCard = imageCard;
    }

    public Bitmap getImageCard() {
        return imageCard;
    }

    public void setImageCardURL(String imageCardURL) {
        this.imageCardURL = imageCardURL;
    }

    public String getImageCardURL() {
        return imageCardURL;
    }
}
