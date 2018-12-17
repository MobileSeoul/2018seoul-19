package com.tensionup.seoul_story.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.tensionup.seoul_story.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends BaseAdapter {

    private Context context;
    private List<Integer> list;
    private List<String> favoriteList;
    private LayoutInflater inflate;
    private ViewHolder searchViewHolder;
    private ArrayList<Boolean> check;

    public SearchAdapter(List<Integer> list, Context context){
        this.list = list;
        this.context = context;
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        if(convertView == null){
            convertView = inflate.inflate(R.layout.layout_search_row,null);

            searchViewHolder = new ViewHolder();
            searchViewHolder.label = (TextView) convertView.findViewById(R.id.search_row_text);
            searchViewHolder.check = (ImageSwitcher) convertView.findViewById(R.id.search_row_check);

            Animation in = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
            Animation out = AnimationUtils.loadAnimation(context,android.R.anim.slide_out_right);

            searchViewHolder.check.setInAnimation(in);
            searchViewHolder.check.setOutAnimation(out);

            searchViewHolder.check.setFactory(new ViewSwitcher.ViewFactory() {
                @Override
                public View makeView() {
                    ImageView myView = new ImageView(context);
                    myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    return myView;
                }
            });

            convertView.setTag(searchViewHolder);

        }else{
            searchViewHolder = (ViewHolder)convertView.getTag();
        }

        check = SearchDAO.settingBoolList(check,context,list,favoriteList);

        if(check.get(position)) {
            searchViewHolder.check.setImageResource(R.drawable.ic_check_circle_regular);
        }else {
            searchViewHolder.check.setImageResource(R.drawable.ic_plus_circle_solid);
        }

        searchViewHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.search_row_check:
                        ImageSwitcher imgSwitcher = (ImageSwitcher)view;
                        if(check.get(position)) {
                            imgSwitcher.setImageResource(R.drawable.ic_plus_circle_solid);
                            SearchDAO.deleteFavoriteCategoryList(context,list.get(position),favoriteList);
                            check.set(position,false);

                        } else {
                            imgSwitcher.setImageResource(R.drawable.ic_check_circle_regular);
                            SearchDAO.addFavoriteCategoryList(context,list.get(position),favoriteList);
                            check.set(position,true);
                        }
                        break;
                }
            }
        });

        // 리스트에 있는 데이터를 리스트뷰 셀에 뿌린다.
        searchViewHolder.label.setText(SearchDAO.convertKor(list.get(position), context));

        searchViewHolder.check.setTag(position);
        return convertView;
    }

    class ViewHolder{
        public TextView label;
        public ImageSwitcher check;
    }

    public void search(String charText, List<Integer> searchList, ArrayList<Integer> arraylist) {
        SearchDAO.search(charText, searchList, arraylist, context);
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        this.notifyDataSetChanged();
    }
}