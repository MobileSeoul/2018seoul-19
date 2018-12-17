package com.tensionup.seoul_story.search;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ListView;

import com.tensionup.seoul_story.R;

import java.util.ArrayList;
import java.util.List;


public class SearchResultFragment extends Fragment {

    private List<Integer> searchList;          // 데이터를 넣은 리스트변수
    private ListView searchListView;          // 검색을 보여줄 리스트변수
    private SearchAdapter adapter;            // 리스트뷰에 연결할 아답터
    private ArrayList<Integer> arraylist;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = (ConstraintLayout)inflater.inflate( R.layout.layout_search_list, container, false );
        searchListView = (ListView) view.findViewById(R.id.search_list);

        // 리스트를 생성한다.
        searchList = new ArrayList<Integer>();

        // 검색에 사용할 데이터을 미리 저장한다.
        settingList(getContext());

        // 리스트의 모든 데이터를 arraylist에 복사한다.// list 복사본을 만든다.
        arraylist = new ArrayList<Integer>();
        arraylist.addAll(searchList);

        // 리스트에 연동될 아답터를 생성한다.
        adapter = new SearchAdapter(searchList, getContext());

        // 리스트뷰에 아답터를 연결한다.
        searchListView.setAdapter(adapter);

        return view;
    }

    public void search(String charText) {
        adapter.search(charText, searchList, arraylist);
    }

    private void settingList(Context context){
        searchList.add(R.string.category_title_en_welfare);
        searchList.add(R.string.category_title_en_woman);
        searchList.add(R.string.category_title_en_economy);
        searchList.add(R.string.category_title_en_safe);
        searchList.add(R.string.category_title_en_citybuild);
        searchList.add(R.string.category_title_en_env);
        searchList.add(R.string.category_title_en_sculture);
        searchList.add(R.string.category_title_en_health);
        searchList.add(R.string.category_title_en_traffic);
        searchList.add(R.string.category_title_en_infra);
        searchList.add(R.string.category_title_en_finance);
        searchList.add(R.string.category_title_en_gov);
    }
}