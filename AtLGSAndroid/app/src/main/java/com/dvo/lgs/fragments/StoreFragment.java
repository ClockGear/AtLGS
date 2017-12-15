package com.dvo.lgs.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dvo.lgs.R;

/**
 * Created by Dennis van Opstal on 15-12-2017.
 */

public class StoreFragment extends Fragment {

    public static StoreFragment newInstance() {
        StoreFragment storeFragment = new StoreFragment();
        return storeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_store, container, false);
    }
}