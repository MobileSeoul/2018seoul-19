package com.tensionup.seoul_story.search;

import android.content.Context;
import android.widget.ImageView;

import com.roka.rokasearchkorean.RokaSearchKorean;
import com.tensionup.seoul_story.R;
import com.tensionup.seoul_story.star.FavoriteListItem;
import com.tensionup.seoul_story.util.FileManagerUtil;

import java.util.ArrayList;
import java.util.List;

public class SearchDAO {
    public static void addAll(Context context, ArrayList list) {
        list.add(addItem(context.getResources().getString(R.string.category_title_en_welfare), context));
        list.add(addItem(context.getResources().getString(R.string.category_title_en_woman), context));
        list.add(addItem(context.getResources().getString(R.string.category_title_en_economy), context));
        list.add(addItem(context.getResources().getString(R.string.category_title_en_safe), context));
        list.add(addItem(context.getResources().getString(R.string.category_title_en_citybuild), context));
        list.add(addItem(context.getResources().getString(R.string.category_title_en_env), context));
        list.add(addItem(context.getResources().getString(R.string.category_title_en_sculture), context));
        list.add(addItem(context.getResources().getString(R.string.category_title_en_health), context));
        list.add(addItem(context.getResources().getString(R.string.category_title_en_traffic), context));
        list.add(addItem(context.getResources().getString(R.string.category_title_en_infra), context));
        list.add(addItem(context.getResources().getString(R.string.category_title_en_finance), context));
        list.add(addItem(context.getResources().getString(R.string.category_title_en_gov), context));
    }

    public static FavoriteListItem addItem(String theme, Context context) {
        FavoriteListItem item = new FavoriteListItem();

        int resImgID = context.getResources().getIdentifier("category_" + theme, "drawable", context.getPackageName());
        int resTitleID = context.getResources().getIdentifier("category_title_" + theme, "string", context.getPackageName());
        int resTitleID_en = context.getResources().getIdentifier("category_title_en_" + theme, "string", context.getPackageName());

        item.setBgImage(resImgID);
        item.setTitle(resTitleID);
        item.setTitle_en(resTitleID_en);

        return item;
    }

    public static void addFavoriteCategoryList(Context context, int title, List<String> categoryList) {
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

    public static void deleteFavoriteCategoryList(Context context, int title, List<String> categoryList) {
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

    public static void isList(Context context, ImageView favoriteBtn,FavoriteListItem item, List<String> categoryList) {
        try {
            boolean isExist = false;
            if (FileManagerUtil.IsExistCategoryState(context)) {
                categoryList = FileManagerUtil.getFavoriteCategory();
                for (String data : categoryList) {
                    if (data.equals(context.getResources().getString(item.getTitle_en()))) {
                        isExist = true;
                        break;
                    } else {
                        continue;
                    }
                }
                if (isExist) {
                    item.setChecked(true);
                    favoriteBtn.setTag(R.id.IsClicked, true);
                    favoriteBtn.setBackgroundResource(R.drawable.ic_favorite_checked);
                } else {
                    item.setChecked(false);
                    favoriteBtn.setTag(R.id.IsClicked, false);
                    favoriteBtn.setBackgroundResource(R.drawable.ic_favorite_before_checking);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setTag(FavoriteListItem favoriteListItem, ImageView favoriteBtn) {
        if(favoriteListItem.isChecked()) {
            favoriteBtn.setTag(R.id.IsClicked, true);
        }else {
            favoriteBtn.setTag(R.id.IsClicked, false);
        }
    }

    public static ArrayList<Boolean> settingBoolList(ArrayList<Boolean> check, Context context, List<Integer> list_en, List<String> categoryList) {
        int index;
        if(check == null) {
            check = new ArrayList<Boolean>();
            for(index=0; index<list_en.size(); index++) {
                check.add(false);
            }
        }
        index=0;
        for(Integer data : list_en) {
            check.set(index,setBool(context, context.getResources().getString(data),categoryList));
            index +=1;
        }
        return check;
    }

    static boolean setBool(Context context, String title, List<String> categoryList) {
        boolean check = false;
        try {
            if (FileManagerUtil.IsExistCategoryState((context))) {
                categoryList = FileManagerUtil.readXmlCategoryState(context);
                FileManagerUtil.setFavoriteCategory((ArrayList<String>)categoryList);

                for(String data : categoryList) {
                    if (data.equals(title)) {
                        check = true;
                        break;
                    }else {
                        continue;
                    }
                }
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public static void search(String charText, List<Integer> searchList, ArrayList<Integer> arraylist, Context context) {
        RokaSearchKorean rokaSearchKorean = new RokaSearchKorean();

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        searchList.clear();

        // 문자 입력이 없을때는 안보여 준다.
        if (charText.length() == 0) {
            //searchList.addAll(arraylist);
        }
        // 문자 입력을 할때..
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < arraylist.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.   한글도 가능
                if (rokaSearchKorean.speedHangleCheck(charText, convertKor(arraylist.get(i), context)))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    searchList.add(arraylist.get(i));
                }
            }
        }
    }

    public static String convertKor(int title, Context context) {
        int resTitleID = context.getResources().getIdentifier("category_title_" + context.getResources().getString(title), "string", context.getPackageName());

        return context.getResources().getString(resTitleID);
    }

}