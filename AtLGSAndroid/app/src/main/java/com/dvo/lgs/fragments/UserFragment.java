package com.dvo.lgs.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dvo.lgs.R;
import com.dvo.lgs.adapters.UserAdapter;
import com.dvo.lgs.domain.User;
import com.dvo.lgs.enums.Role;
import com.dvo.lgs.util.OnItemLongClickListener;
import com.dvo.lgs.volley.AppController;
import com.dvo.lgs.volley.BetterStringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dennis van Opstal on 8-12-2017.
 */

public class UserFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private List<User> users;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String VOLLEY_TAG = "VOLLEY - USERS";
    private SharedPreferences sharedPref;
    private String token;
    private boolean isAdmin;
    private long ownUserId;

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
                final User user = users.get(position);
                if (isAdmin && user.getId() != ownUserId) {
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
                            deleteUser(user.getId());
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
        recyclerView = getActivity().findViewById(R.id.rvUsers);
        swipeRefreshLayout = getActivity().findViewById(R.id.srlUsers);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllUsers();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        sharedPref = getContext().getSharedPreferences(getString(R.string.def_pref), Context.MODE_PRIVATE);
        token = sharedPref.getString(getString(R.string.login_token), "");
        getAllUsers();
        roleCheck();
    }

    private void getAllUsers() {
        String requestTag = "get_all_users_request";
        String url = getString(R.string.api_url) + "/user";
        BetterStringRequest validationRequest = new BetterStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(VOLLEY_TAG, response);
                try {
                    JSONObject json = new JSONObject(response);
                    JSONArray userJsonArray = json.getJSONArray("object");
                    users.clear();
                    for (int i = 0; i < userJsonArray.length(); i++) {
                        JSONObject lgsJsonObject = (JSONObject) userJsonArray.get(i);
                        User user = new Gson().fromJson(lgsJsonObject.toString(), User.class);
                        users.add(user);
                    }
                    swipeRefreshLayout.setRefreshing(false);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String jsonString = error.getMessage();
                if (jsonString == null || jsonString.isEmpty()) {
                    createNewErrorDialog(R.string.err_something_wrong);
                } else {
                    Log.e(VOLLEY_TAG, jsonString);
                    try {
                        JSONObject json = new JSONObject(jsonString);
                        String message = json.getString("message");
                        switch (message) {
                            default:
                                createNewErrorDialog(R.string.err_something_wrong);
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        createNewErrorDialog(R.string.err_something_wrong);
                    }
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization","Bearer " + token);
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(validationRequest, requestTag);
    }

    private void roleCheck() {
        final String requestTag = "role_check_request";
        String url = getString(R.string.api_url) + "/user/validation";
        BetterStringRequest validationRequest = new BetterStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(VOLLEY_TAG, response);
                try {
                    JSONObject json = new JSONObject(response);
                    final JSONObject jsonUser = json.getJSONObject("object");
                    Role role = Role.valueOf(jsonUser.getString("role"));
                    ownUserId = jsonUser.getLong("id");
                    if (role.equals(Role.ADMIN)) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                isAdmin = true;
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String jsonString = error.getMessage();
                if (jsonString == null || jsonString.isEmpty()) {
                    createNewErrorDialog(R.string.err_something_wrong);
                } else {
                    Log.e(VOLLEY_TAG, jsonString);
                    try {
                        JSONObject json = new JSONObject(jsonString);
                        String message = json.getString("message");
                        switch (message) {
                            default:
                                createNewErrorDialog(R.string.err_something_wrong);
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        createNewErrorDialog(R.string.err_something_wrong);
                    }
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization","Bearer " + token);
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(validationRequest, requestTag);
    }

    private void deleteUser(long id) {
        String requestTag = "delete_user_request";
        String url = getString(R.string.api_url) + "/user?id=" + id;
        BetterStringRequest validationRequest = new BetterStringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(VOLLEY_TAG, response);
                getAllUsers();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String jsonString = error.getMessage();
                if (jsonString == null || jsonString.isEmpty()) {
                    createNewErrorDialog(R.string.err_something_wrong);
                } else {
                    Log.e(VOLLEY_TAG, jsonString);
                    try {
                        JSONObject json = new JSONObject(jsonString);
                        String message = json.getString("message");
                        switch (message) {
                            case "Something went wrong while deleting the User.":
                                createNewErrorDialog(R.string.err_something_wrong);
                                break;
                            case "User with the given id doesn't exist.":
                                createNewErrorDialog(R.string.err_user_id_exist);
                                break;
                            default:
                                createNewErrorDialog(R.string.err_something_wrong);
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        createNewErrorDialog(R.string.err_something_wrong);
                    }
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization","Bearer " + token);
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(validationRequest, requestTag);
    }

    private void createNewErrorDialog(int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.error);
        builder.setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
