package com.tensionup.seoul_story.parcelable;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

class FavoriteCardImgParceable implements Parcelable {

    private Bitmap[] m_bitmapImgs;

    //Parcelable를 생성하기 위한 생성자 //임의 생성
    public FavoriteCardImgParceable(Bitmap[] bitmaps) {
        this.m_bitmapImgs = bitmaps;
    }

    //Parcelable를 생성하기 위한 생성자 Parcel를 파라메타로 넘겨 받음
    private FavoriteCardImgParceable(Parcel source) {
        // TODO Auto-generated constructor stub
//        m_bitmapImgs = source.read();
//        mAge = source.readInt();
    }

    //각 값을 넘겨주기 위한 get터 생성
    public Bitmap[] getBitmapImgs() {
        return m_bitmapImgs;
    }

    //Parcelable을 상속 받으면 필수 Method
    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    //Parcelable의 write를 구현하기 위한 Method
    @Override
    public void writeToParcel(Parcel arg0, int arg1) {
        // TODO Auto-generated method stub
//        arg0.wri
//        arg0.writeString(mName);
//        arg0.writeInt(mAge);
//        arg0.writeString(mEmail);
    }

    //Parcelable 객체로 구현하기 위한 Parcelable Method ArrayList구현 등..
    public static final Parcelable.Creator<FavoriteCardImgParceable> CREATOR
            = new Parcelable.Creator<FavoriteCardImgParceable>() {

        @Override
        public FavoriteCardImgParceable createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new FavoriteCardImgParceable(source);
        }

        @Override
        public FavoriteCardImgParceable[] newArray(int size) {
            // TODO Auto-generated method stub
            return new FavoriteCardImgParceable[size];
        }
    };
}