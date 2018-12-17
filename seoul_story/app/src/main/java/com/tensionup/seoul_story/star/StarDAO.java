package com.tensionup.seoul_story.star;

import android.content.Context;
import android.widget.Toast;

import com.tensionup.seoul_story.R;
import com.tensionup.seoul_story.util.FileManagerUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StarDAO {
    public static FavoriteListItem addItem(Context context, String theme) {
        FavoriteListItem item = new FavoriteListItem();

        int resImgID = context.getResources().getIdentifier("category_"+theme, "drawable", context.getPackageName());
        int resTitleID = context.getResources().getIdentifier("category_title_"+theme, "string", context.getPackageName());
        int resTitleID_en = context.getResources().getIdentifier("category_title_en_" + theme, "string", context.getPackageName());

        item.setBgImage(resImgID);
        item.setTitle(resTitleID);
        item.setTitle_en(resTitleID_en);

        return item;
    }

    public static void deleteFavoriteCategoryList(Context context, int title) {
        List<String> categoryList;
        try {
            boolean isExist = false;
            if (FileManagerUtil.IsExistCategoryState((context))) {
                categoryList = FileManagerUtil.readXmlCategoryState(context);
                FileManagerUtil.setFavoriteCategory((ArrayList<String>)categoryList);
                for(String data : categoryList) {
                    if (data.equals(context.getResources().getString(title))) {
                        isExist = true;
                        break;
                    }else {
                        continue;
                    }
                }
                if(isExist) {
                    categoryList.remove(context.getString(title));
                    FileManagerUtil.writeXmlCategoryState(context, categoryList);
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void addFavoriteCategoryList(Context context, int title) {
        List<String> categoryList;
        try {
            boolean isExist = false;
            if (FileManagerUtil.IsExistCategoryState((context))) {
                categoryList = FileManagerUtil.readXmlCategoryState(context);
                FileManagerUtil.setFavoriteCategory((ArrayList<String>)categoryList);
                for(String data : categoryList) {
                    if (data.equals(context.getResources().getString(title))) {
                        isExist = true;
                        break;
                    }else {
                        continue;
                    }
                }
                if(!isExist) {
                    categoryList.add((context.getString(title)));
                    FileManagerUtil.writeXmlCategoryState(context, categoryList);
                }
            } else {
                categoryList = new ArrayList<>();
                categoryList.add(context.getString(title));
                FileManagerUtil.writeXmlCategoryState(context, categoryList);
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void setChange(List<Object> items, Context context) {
        List<String> categoryList = new ArrayList<String>();

        for(Object item : items) {
            FavoriteListItem card = (FavoriteListItem)item;
            categoryList.add(context.getResources().getString(card.getTitle_en()));
        }
        try {
            FileManagerUtil.writeXmlCategoryState(context, categoryList);
            Toast.makeText(context,R.string.star_edit_ordered,Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(context,R.string.star_fail_edit_ordered,Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}