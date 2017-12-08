package com.dvo.lgs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dvo.lgs.volley.AppController;
import com.dvo.lgs.volley.BetterStringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    private static final String VOLLEY_TAG = "VOLLEY - SPLASH";
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPref = getSharedPreferences(getString(R.string.def_pref), Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.login_token), "");
        if (!token.isEmpty()) {
            quickLogin(token);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void quickLogin(final String token) {
        String requestTag = "token_check_request";
        String url = getString(R.string.api_url) + "/user/validation";
        BetterStringRequest validationRequest = new BetterStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(VOLLEY_TAG, response);
                Intent intent;
                try {
                    JSONObject json = new JSONObject(response);
                    if (!json.getBoolean("error")) {
                        intent = new Intent(SplashActivity.this, MainActivity.class);
                    } else {
                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                    }
                } catch (JSONException e) {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() != null && !error.getMessage().isEmpty()) {
                    Log.e(VOLLEY_TAG,error.getMessage());
                }
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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
}
