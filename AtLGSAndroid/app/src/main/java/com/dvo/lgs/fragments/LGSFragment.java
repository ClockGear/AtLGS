package com.dvo.lgs.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dvo.lgs.R;
import com.dvo.lgs.adapters.LGSAdapter;
import com.dvo.lgs.domain.LGS;
import com.dvo.lgs.enums.Role;
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
 * Created by Dennis van Opstal on 15-12-2017.
 */

public class LGSFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fabAddLGS;
    private LGSAdapter adapter;
    private List<LGS> lgsList;
    private AlertDialog alertDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String VOLLEY_TAG = "VOLLEY - LGS";
    private SharedPreferences sharedPref;
    private String token;

    public static LGSFragment newInstance() {
        LGSFragment lgsFragment = new LGSFragment();
        return lgsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lgs, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lgsList = new ArrayList<>();
        adapter = new LGSAdapter(lgsList);
        fabAddLGS = getActivity().findViewById(R.id.fabAddLGS);
        fabAddLGS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_add_lgs, null);
                builder.setView(dialogView);
                final EditText etNameLGS = dialogView.findViewById(R.id.etNameLGS);
                final EditText etStreetLGS = dialogView.findViewById(R.id.etStreetLGS);
                final EditText etCityLGS = dialogView.findViewById(R.id.etCityLGS);
                Button confirmButton = dialogView.findViewById(R.id.btnAddLGS);
                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String lgsName = String.valueOf(etNameLGS.getText());
                        String lgsAddress = String.valueOf(etStreetLGS.getText() + ", " + etCityLGS.getText());
                        addLGS(lgsName,lgsAddress);
                        alertDialog.dismiss();
                    }
                });
                alertDialog = builder.show();
            }
        });
        recyclerView = getActivity().findViewById(R.id.rvLGS);
        swipeRefreshLayout = getActivity().findViewById(R.id.srlLGS);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllLGSs();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        sharedPref = getContext().getSharedPreferences(getString(R.string.def_pref), Context.MODE_PRIVATE);
        token = sharedPref.getString(getString(R.string.login_token), "");
        getAllLGSs();
        roleCheck();
    }

    private void getAllLGSs() {
        String requestTag = "get_all_lgs_request";
        String url = getString(R.string.api_url) + "/lgs";
        BetterStringRequest validationRequest = new BetterStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(VOLLEY_TAG, response);
                try {
                    JSONObject json = new JSONObject(response);
                    JSONArray lgsJsonArray = json.getJSONArray("object");
                    lgsList.clear();
                    for (int i = 0; i < lgsJsonArray.length(); i++) {
                        JSONObject lgsJsonObject = (JSONObject) lgsJsonArray.get(i);
                        LGS lgs = new Gson().fromJson(lgsJsonObject.toString(),LGS.class);
                        lgsList.add(lgs);
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
                    JSONObject jsonUser = json.getJSONObject("object");
                    Role role = Role.valueOf(jsonUser.getString("role"));
                    if (role.equals(Role.ADMIN)) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fabAddLGS.setVisibility(View.VISIBLE);
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

    private void addLGS(final String name, final String address) {
        String requestTag = "add_lgs_request";
        String url = getString(R.string.api_url) + "/lgs";
        BetterStringRequest validationRequest = new BetterStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(VOLLEY_TAG, response);
                getAllLGSs();
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

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",name);
                params.put("address",address);
                return params;
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