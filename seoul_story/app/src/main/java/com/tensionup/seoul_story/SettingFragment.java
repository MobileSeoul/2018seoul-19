package com.tensionup.seoul_story;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tensionup.seoul_story.star.FavoriteListItem;
import com.tensionup.seoul_story.star.StarDAO;

import java.util.List;

public class SettingFragment extends Fragment {

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate( R.layout.fragment_setting, container, false );

        ((MainActivity)getActivity()).windowState.setWindowState(getResources().getString(R.string.window_state_setting_root));
        return view;
    }
}