package com.dvo.lgs.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dvo.lgs.R;
import com.dvo.lgs.adapters.UserAdapter;
import com.dvo.lgs.domain.User;
import com.dvo.lgs.util.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dennis van Opstal on 8-12-2017.
 */

public class UserFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAddUser;
    private UserAdapter adapter;
    private List<User> users;
    private AlertDialog alertDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String VOLLEY_TAG = "VOLLEY - USERS";
    private SharedPreferences sharedPref;
    private String token;
    private boolean isAdmin;

    public static UserFragment newInstance() {
        UserFragment userFragment = new UserFragment();
        return userFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isAdmin = false;
        users = new ArrayList<>();
        adapter = new UserAdapter(users, new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                if (isAdmin) {
                    final User user = users.get(position);
                    Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                    if (vibrator != null) {
                        vibrator.vibrate(50);
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(getString(R.string.deletingFront) + getString(R.string.user) + getString(R.string.deletingBack));
                    builder.setMessage(getString(R.string.deletingFront2) + user.getDisplayName() + getString(R.string.deletingBack2));
                    builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //TODO DELETE USER
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });
        recyclerView = getActivity().findViewById(R.id.rvLGS);
        swipeRefreshLayout = getActivity().findViewById(R.id.srlLGS);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //TODO GET ALL USERS
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        sharedPref = getContext().getSharedPreferences(getString(R.string.def_pref), Context.MODE_PRIVATE);
        token = sharedPref.getString(getString(R.string.login_token), "");
        //TODO GET ALL USERS AND CHECK ROLE
    }
}
