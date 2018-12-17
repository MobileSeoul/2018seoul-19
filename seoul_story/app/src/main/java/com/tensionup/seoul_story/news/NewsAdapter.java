package com.tensionup.seoul_story.news;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tensionup.seoul_story.ArticleActivity;
import com.tensionup.seoul_story.MainActivity;
import com.tensionup.seoul_story.R;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter{
    private ArrayList<NewsCardItem> newsCardItem ;
    Context m_context;

    public NewsAdapter(Context context) { //기본 생성자
        m_context = context;
        newsCardItem = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return newsCardItem.size();
    }

    @Override
    public Object getItem(int position) {
        return newsCardItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(m_context).inflate(R.layout.news_card_item, parent, false);
        }


        final NewsCardItem item = (NewsCardItem)this.getItem(position);

        ImageView cardImage = (ImageView) convertView.findViewById(R.id.newsBannerImage);

        if( item.getImageCard() != null ) {
            cardImage.setImageBitmap(item.getImageCard());

            cardImage.getLayoutParams().height = convertView.getResources().getDimensionPixelSize(R.dimen.news_card_item_image_height);
            cardImage.requestLayout();
            cardImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String clickWebURL = item.getImageCardURL();

                    if( clickWebURL.equals("nourl") ) {
                        return;
                    }

                    Intent intent = new Intent(m_context
                            , ArticleActivity.class);
                    intent.putExtra("ARTICLE_HREF", clickWebURL);

                    m_context.startActivity(intent);
                }
            });
            return convertView;
        }

        TextView categoryTitleTextView = (TextView) convertView.findViewById(R.id.news_category_title_TextView);
        TextView newsTitleTextView = (TextView) convertView.findViewById(R.id.news_title_TextView);
        TextView newsDatetextView = (TextView) convertView.findViewById(R.id.news_date);
//        ImageView cardImage = (ImageView) convertView.findViewById(R.id.news_card_ImageView);

        categoryTitleTextView.setText(item.getCategoryTitle());
        newsTitleTextView.setText(item.getNewsTitle());
        newsDatetextView.setText(item.getDate());

        cardImage.getLayoutParams().height = convertView.getResources().getDimensionPixelSize(R.dimen.news_card_item_blank_image_height);

//        if(position ==0) {
//            cardImage.setColorFilter(R.color.cardFilter); // 이미지 어둡게 필터처리
//            cardImage.setImageResource(item.getBgImage());
//            cardImage.getLayoutParams().height = convertView.getResources().getDimensionPixelSize(R.dimen.news_card_item_image_height);
//
//            // 이미지가 있는 카드는 흰색 글씨로 설정
//            NewsDAO.setTextColorWhite(categoryTitleTextView);
//            NewsDAO.setTextColorWhite(newsTitleTextView);
//        }else {
//            // 이미지가 없는 카드의 높이 조절
//            cardImage.getLayoutParams().height = convertView.getResources().getDimensionPixelSize(R.dimen.news_card_item_blank_image_height);
//        }
//        cardImage.getLayoutParams().height = convertView.getResources().getDimensionPixelSize(R.dimen.news_card_item_blank_image_height);
//        cardImage.requestLayout(); // cardImage 의 크기에 대한 변경사항 요청

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(m_context
                        , ArticleActivity.class);
                intent.putExtra("ARTICLE_HREF", item.getHref());

                m_context.startActivity(intent);
            }
        });

        return convertView;
    }

    public void addItem(String ctTitle, String nTitle, String date, String href) {
        newsCardItem.add(NewsDAO.addItem(m_context, ctTitle, nTitle, date, href));
    }

    public void addItem(Bitmap img, String url) {
        newsCardItem.add(NewsDAO.addItem(img, url));
    }


    public void clearData() {
        newsCardItem.clear();
    }
}
