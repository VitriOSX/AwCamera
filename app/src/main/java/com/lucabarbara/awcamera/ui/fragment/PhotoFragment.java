package com.lucabarbara.awcamera.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lucabarbara.awcamera.R;

import butterknife.ButterKnife;

/**
 * Created by luca1897 on 23/06/17.
 */

public class PhotoFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public PhotoFragment() {
    }

    public static PhotoFragment newInstance(int sectionNumber) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photo, container, false);
        ButterKnife.bind(this,rootView);

        return rootView;
    }
}