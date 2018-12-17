package com.tensionup.seoul_story.search;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.tensionup.seoul_story.MainActivity;
import com.tensionup.seoul_story.R;

public class SearchFragment extends Fragment {

    private final static int searchCheckListPageNum = 0;
    private final static int searchListPageNum =1;
    private final static int viewPageNumCnt = 2;

    private EditText searchEditText;              //Search의 EditText 부분
    private String searchText;                    //검색어
    private ViewPager listViewPager;             //검색밑에 있는 뷰페이저
    private Button clearButton;                  //EditText안의 X버튼
    private Button cancelButton;                 //취소버튼

    private SearchResultFragment m_searchResult;

    public EditText getSearchEditText() {
        return searchEditText;
    }

    public String getSearchText() {
        return searchText;
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchEditText = (EditText) view.findViewById(R.id.SearchEditText);
        listViewPager = (ViewPager) view.findViewById(R.id.search_viewpager);
        clearButton = (Button) view.findViewById(R.id.clearButton);
        cancelButton = (Button) view.findViewById(R.id.cancelButton);

        BtnOnClickListener onClickListener = new BtnOnClickListener();
        clearButton.setVisibility(View.INVISIBLE);

        clearButton.setOnClickListener(onClickListener);
        cancelButton.setOnClickListener(onClickListener);

        listViewPager.setAdapter(new pagerAdapter(getActivity().getSupportFragmentManager()));

        searchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                cancelButton.setLayoutParams(new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        (b  ? 1 : 0) * 0.15f));                 //Edit텍스트의 포커스가 있냐 없냐로 취소버튼의 레이아웃의 비율을 바꿔줌
            }
        });


        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ((MainActivity)getActivity()).windowState.setWindowState(getResources().getString(R.string.window_state_search_root));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // input창에 문자를 입력할때마다 호출된다.
                // 검색어의 길이에 따라서 clearButton을 보여주고 안보여주고!
                int textLength = searchEditText.getText().length();

                if(textLength > 0) {
                    clearButton.setVisibility(View.VISIBLE);
                }
                else{
                    clearButton.setVisibility(View.INVISIBLE);
                }
                //검색어가 어떤게 찾아지고 있는지 계속해서 확인
                searchText = searchEditText.getText().toString();
                listViewPager.setCurrentItem(searchListPageNum);


                // SearchResultFrament
                m_searchResult.search(searchText);
                ((MainActivity)getActivity()).windowState.setWindowState(getResources().getString(R.string.window_state_search_child));
            }
        });

        return view;
    }

    //키보드 숨기는 함수?
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //버튼 클래스
    class BtnOnClickListener implements  Button.OnClickListener{
        @Override
        public void onClick(View view){
            switch (view.getId()){
                case R.id.cancelButton:
                    searchEditText.setText(null);               //검색 지우고
                    searchEditText.clearFocus();                //포커스 없애고
                    hideKeyboardFrom(getContext(),view);        //키보드 없애주고
                    ((MainActivity)getActivity()).windowState.setWindowState(getResources().getString(R.string.window_state_search_root));
                    listViewPager.setCurrentItem(searchCheckListPageNum);            //뷰페이져 전환
                    break;

                case R.id.clearButton:
                    searchEditText.setText(null);
                    ((MainActivity)getActivity()).windowState.setWindowState(getResources().getString(R.string.window_state_search_child));
                    break;
            }

        }
    }

    private class pagerAdapter extends FragmentStatePagerAdapter
    {
        public pagerAdapter(android.support.v4.app.FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            switch(position)
            {
                case searchCheckListPageNum:
                    return new SearchCheckListFragment();

                case searchListPageNum:
                    SearchResultFragment  fragment = new SearchResultFragment();
                    m_searchResult = fragment;

                    return fragment;

                default:
                    return null;
            }
        }
        public int getCount()
        {
            return viewPageNumCnt;
        }
    }
}