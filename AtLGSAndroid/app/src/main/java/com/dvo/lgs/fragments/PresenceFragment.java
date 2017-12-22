package com.dvo.lgs.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dvo.lgs.R;

/**
 * Created by Dennis van Opstal on 1-12-2017.
 */

public class PresenceFragment extends Fragment {

    public static PresenceFragment newInstance() {
        PresenceFragment presenceFragment = new PresenceFragment();
        return presenceFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_presence, container, false);
    }
}
