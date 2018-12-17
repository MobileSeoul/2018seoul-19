package com.tensionup.seoul_story.star;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tensionup.seoul_story.R;
import com.tensionup.seoul_story.util.FileManagerUtil;

import org.askerov.dynamicgrid.BaseDynamicGridAdapter;


public class FavoriteListAdapter extends BaseDynamicGridAdapter {
    private Context context;
    private boolean favoriteState;

    public FavoriteListAdapter(Context context, int columnCount) {
        super(context, columnCount);
    }

    //position에 위치한 데이터 출력될 View return
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();

        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false);
        }

        final FavoriteListItem favoriteListItem = (FavoriteListItem)this.getItem(position);

        ImageView favoriteImage = (ImageView) convertView.findViewById(R.id.card_imageView);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.card_title_textView);
        final ImageView favoriteBtn = (ImageView) convertView.findViewById(R.id.card_check_imageView);

        favoriteImage.setColorFilter(R.color.cardFilter); // 이미지 어둡게 필터처리
        favoriteImage.setImageBitmap(FileManagerUtil.getCategoryBitmap(favoriteListItem.getBgImage()));
        titleTextView.setText(favoriteListItem.getTitle());
        favoriteBtn.setImageResource(favoriteListItem.getFavoriteBtn());

        favoriteBtn.setTag(R.id.IsClicked, true);
        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //각 카드마다 버튼 반응을 만들어 줘야 하기 때문에 adapter에서 listener를 만들어줌
                favoriteState = (boolean)favoriteBtn.getTag(R.id.IsClicked);

                if(favoriteState == true) {
                    favoriteBtn.setTag(R.id.IsClicked, false);
                    favoriteBtn.setBackgroundResource(R.drawable.ic_favorite_before_checking);
                    favoriteListItem.setChecked(false);

                    StarDAO.deleteFavoriteCategoryList(context, favoriteListItem.getTitle_en());
                } else {
                    favoriteBtn.setTag(R.id.IsClicked, true);
                    favoriteBtn.setBackgroundResource(R.drawable.ic_favorite_checked);
                    favoriteListItem.setChecked(true);

                    StarDAO.addFavoriteCategoryList(context, favoriteListItem.getTitle_en());
                }
            }
        });
        return convertView;
    }

    public void addItem(String theme, ViewGroup parent) {
        context = parent.getContext();
        this.add(StarDAO.addItem(context, theme));
    }

    public void setChange() {
        StarDAO.setChange(this.getItems(), getContext());
    }
}