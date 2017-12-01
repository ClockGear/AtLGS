package com.dvo.lgs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dvo.lgs.volley.AppController;
import com.dvo.lgs.volley.BetterStringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String VOLLEY_TAG = "VOLLEY - LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        String requestTag = "login_request";
        String url = getString(R.string.api_url) + "/user/login";
        final String email = String.valueOf(((TextView)findViewById(R.id.etLoginEmail)).getText());
        String password = String.valueOf(((TextView)findViewById(R.id.etLoginPassword)).getText());
        final String hashedPassword = hash(password);
        BetterStringRequest loginRequest = new BetterStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(VOLLEY_TAG, response);
                try {
                    JSONObject json = new JSONObject(response);
                    if (!json.getBoolean("error")) {
                        String token = json.getString("object");
                        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.def_pref), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(getString(R.string.login_token), token);
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        createNewErrorDialog(R.string.err_something_wrong);
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
                            case "The given password was incorrect.":
                                createNewErrorDialog(R.string.err_password_wrong);
                                break;
                            case "User with the given email doesn't exist.":
                                createNewErrorDialog(R.string.err_user_email_not_exist);
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",email);
                params.put("password",hashedPassword);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(loginRequest, requestTag);
    }

    public void toRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void toForgot(View view) {
        //TODO Create forgot password screen
        throw new UnsupportedOperationException(getString(R.string.err_not_implemented));
    }

    private void createNewErrorDialog(int message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.error);
        builder.setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //region Hash
    private static String hash(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes());
            return bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
    //endregion
}
