package com.tensionup.seoul_story.star;

public class FavoriteListItem {
    private int bgImage;
    private int favoriteBtn;
    private int title;
    private int title_en;
    private boolean checked;

    public FavoriteListItem() { } // 기본생성자

    //get set 메소드
    public int getBgImage() {
        return bgImage;
    }

    public int getTitle() {
        return title;
    }

    public int getTitle_en() {
        return title_en;
    }

    public int getFavoriteBtn() {
        return favoriteBtn;
    }

    public boolean isChecked() { return checked; }

    public void setBgImage(int bgImage) {
        this.bgImage = bgImage;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public void setTitle_en(int title_en) {
        this.title_en = title_en;
    }

    public void setChecked(boolean checked) { this.checked = checked; }
}
