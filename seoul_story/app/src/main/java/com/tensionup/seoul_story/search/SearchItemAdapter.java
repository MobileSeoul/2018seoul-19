package com.tensionup.seoul_story.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tensionup.seoul_story.R;
import com.tensionup.seoul_story.star.FavoriteListItem;
import com.tensionup.seoul_story.util.FileManagerUtil;

import java.util.ArrayList;
import java.util.List;

public class SearchItemAdapter extends BaseAdapter {
    private Context context;
    private boolean favoriteState;
    private ArrayList<FavoriteListItem> items;
    private List<String> categoryList;

    public SearchItemAdapter() {
        items = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int pos) {
        return items.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();

        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.search_card_item, parent, false);
        }

        final FavoriteListItem favoriteListItem = (FavoriteListItem)this.getItem(position);

        ImageView favoriteImage = (ImageView) convertView.findViewById(R.id.search_card_imageView);
        final TextView titleTextView = (TextView) convertView.findViewById(R.id.search_card_title_textView);
        final ImageView favoriteBtn = (ImageView) convertView.findViewById(R.id.search_card_check_imageView);

        favoriteImage.setColorFilter(R.color.cardFilter); // 이미지 어둡게 필터처리
        favoriteImage.setImageBitmap(FileManagerUtil.getCategoryBitmap(favoriteListItem.getBgImage()));
        titleTextView.setText(favoriteListItem.getTitle());
        favoriteBtn.setImageResource(favoriteListItem.getFavoriteBtn());

        isList(context, favoriteBtn, favoriteListItem); // 리스트에 있을 때 체크 이미지 표시 메소드
        setTag(favoriteListItem,favoriteBtn); // favoriteBtn 에 setTag 메소드

        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                favoriteState = (boolean)favoriteBtn.getTag(R.id.IsClicked);
                if(favoriteState == false) {
                    favoriteBtn.setTag(R.id.IsClicked, true);
                    favoriteBtn.setBackgroundResource(R.drawable.ic_favorite_checked);
                    favoriteListItem.setChecked(true);
                    addFavoriteCategoryList(context,favoriteListItem.getTitle_en());
                } else {
                    favoriteBtn.setTag(R.id.IsClicked, false);
                    favoriteBtn.setBackgroundResource(R.drawable.ic_favorite_before_checking);
                    favoriteListItem.setChecked(false);
                    deleteFavoriteCategoryList(context,favoriteListItem.getTitle_en());
                }
            }
        });

        return convertView;
    }

    void addAllItems(Context pContext) {
        SearchDAO.addAll(pContext, items);
    }

    private void addFavoriteCategoryList(Context context, int title) {
        SearchDAO.addFavoriteCategoryList(context,title,categoryList);
    }

    private void deleteFavoriteCategoryList(Context context, int title) {
        SearchDAO.deleteFavoriteCategoryList(context,title,categoryList);
    }

    private void isList(Context context, ImageView favoriteBtn,FavoriteListItem item) {
        SearchDAO.isList(context, favoriteBtn, item, categoryList);
    }

    private void setTag(FavoriteListItem favoriteListItem, ImageView favoriteBtn) {
        SearchDAO.setTag(favoriteListItem, favoriteBtn);
    }
}
