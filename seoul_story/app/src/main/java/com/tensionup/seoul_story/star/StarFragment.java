package com.tensionup.seoul_story.star;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.tensionup.seoul_story.MainActivity;
import com.tensionup.seoul_story.R;
import com.tensionup.seoul_story.SelectItemActivity;
import com.tensionup.seoul_story.util.FileManagerUtil;

import org.askerov.dynamicgrid.DynamicGridView;

import java.util.ArrayList;

public class StarFragment extends Fragment {
    private final static String TAG = StarFragment.class.getSimpleName() + "/DEV";

    private FavoriteListAdapter adapter;
    private DynamicGridView gridView;

    private ImageView checkImg;
    private boolean check =true;

    public void setCategoryList(ArrayList<String> list, ViewGroup container) {
        for(String data : list) {
            adapter.addItem(data, container);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_star, container, false );

        adapter = new FavoriteListAdapter(getContext(),getResources().getInteger(R.integer.column_vertical));

        setCategoryList(FileManagerUtil.getFavoriteCategory(), container);

        gridView = view.findViewById(R.id.star_gridView);
        gridView.setAdapter(adapter);

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                gridView.startEditMode(position);
                return true;
            }
        });

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

        gridView.setOnDropListener(new DynamicGridView.OnDropListener() {
            @Override
            public void onActionDrop() {
                gridView.stopEditMode();
                adapter.setChange();
            }
        });

        /**
         * Activity 실행시 화면모드 확인 하기
         */
        if (view.getResources().getConfiguration().orientation == Configuration.
                ORIENTATION_PORTRAIT) { //세로 화면
            adapter.setColumnCount(getResources().getInteger(R.integer.column_vertical));
            gridView.setNumColumns(getResources().getInteger(R.integer.column_vertical));

        } else if(view.getResources().getConfiguration().orientation == Configuration.
                ORIENTATION_LANDSCAPE) { //가로 화면
            adapter.setColumnCount(getResources().getInteger(R.integer.column_horizontal));
            gridView.setNumColumns(getResources().getInteger(R.integer.column_horizontal));
        }

        ((MainActivity)getActivity()).windowState.setWindowState(getResources().getString(R.string.window_state_star_root));

        return view;
    }

    //화면 회전시 gridView의 Column을 5로 변경
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {//세로화면
            adapter.setColumnCount(getResources().getInteger(R.integer.column_vertical)); // 순서 수정시, adapter에 변경된 column을 넘겨줘서 인식할 수 있게 함.
            gridView.setNumColumns(getResources().getInteger(R.integer.column_vertical)); // 화면에 뿌려지는 갯수 수정
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) { //가로화면
            adapter.setColumnCount(getResources().getInteger(R.integer.column_horizontal));
            gridView.setNumColumns(getResources().getInteger(R.integer.column_horizontal));
        }
    }
}