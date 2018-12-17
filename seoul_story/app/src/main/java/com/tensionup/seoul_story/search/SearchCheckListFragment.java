package com.tensionup.seoul_story.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.tensionup.seoul_story.MainActivity;
import com.tensionup.seoul_story.R;
import com.tensionup.seoul_story.SelectItemActivity;
import com.tensionup.seoul_story.star.FavoriteListItem;

import java.util.ArrayList;

public class SearchCheckListFragment extends Fragment{
    SearchItemAdapter adapter;
    GridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.layout_search_checklist, container, false);
        adapter = new SearchItemAdapter();
        adapter.addAllItems(getContext());
        gridView = view.findViewById(R.id.favorite_gridView);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //have to do : 아이템 클릭시 개별 카드 화면으로 바뀌는 부분
                FavoriteListItem item = (FavoriteListItem)adapter.getItem(position);
                Intent intent = new Intent(getContext(), SelectItemActivity.class);
                intent.putExtra("Category_Title", getResources().getString(item.getTitle_en()));
                startActivity(intent);
            }
        });

        ((MainActivity)getActivity()).windowState.setWindowState(getResources().getString(R.string.window_state_search_root));

        return view;
    }
}
