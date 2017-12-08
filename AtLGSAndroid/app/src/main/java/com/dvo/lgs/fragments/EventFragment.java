package com.dvo.lgs.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dvo.lgs.R;

/**
 * Created by Dennis van Opstal on 1-12-2017.
 */

public class EventFragment extends Fragment {

    public static EventFragment newInstance() {
        EventFragment eventFragment = new EventFragment();
        return eventFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_events, container, false);
    }
}
