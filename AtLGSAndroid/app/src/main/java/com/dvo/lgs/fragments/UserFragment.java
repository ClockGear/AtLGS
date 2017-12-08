package com.dvo.lgs.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dvo.lgs.R;

/**
 * Created by Dennis van Opstal on 8-12-2017.
 */

public class UserFragment extends Fragment {

    public static UserFragment newInstance() {
        UserFragment userFragment = new UserFragment();
        return userFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_users, container, false);
    }
}
