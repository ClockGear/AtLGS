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
import android.widget.Toast;

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
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private static final String VOLLEY_TAG = "VOLLEY - REGISTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view) {
        String password = String.valueOf(((TextView)findViewById(R.id.etRegisterPassword)).getText());
        String password2 = String.valueOf(((TextView)findViewById(R.id.etRegisterPassword2)).getText());
        if (!password.equals(password2)) {
            createNewErrorDialog(R.string.password_mismatch);
        } else {
            if (password.isEmpty()) {
                Toast.makeText(this, R.string.err_req_password,Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() < 8) {
                Toast.makeText(this, R.string.err_min_8_char,Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isLegalPassword(password)) {
                Toast.makeText(this, R.string.err_password_contain,Toast.LENGTH_SHORT).show();
            }
            final String firstName = String.valueOf(((TextView)findViewById(R.id.etRegisterFirstName)).getText());
            if (firstName.isEmpty()) {
                Toast.makeText(this, R.string.err_req_first_name, Toast.LENGTH_SHORT).show();
                return;
            }
            final String lastName = String.valueOf(((TextView)findViewById(R.id.etRegisterLastName)).getText());
            if (lastName.isEmpty()) {
                Toast.makeText(this, R.string.err_req_last_name, Toast.LENGTH_SHORT).show();
                return;
            }
            final String email = String.valueOf(((TextView)findViewById(R.id.etRegisterEmail)).getText());
            if (email.isEmpty()) {
                Toast.makeText(this, R.string.err_req_email,Toast.LENGTH_SHORT).show();
                return;
            }
            if (!email.contains("@")) {
                Toast.makeText(this, R.string.err_invalid_email, Toast.LENGTH_SHORT).show();
            }
            final String hashedPassword = hash(password);
            String requestTag = "register_request";
            String url = getString(R.string.api_url) + "/user";

            BetterStringRequest registerRequest = new BetterStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(VOLLEY_TAG,response);
                    try {
                        JSONObject json = new JSONObject(response);
                        if (!json.getBoolean("error")) {
                            login(email, hashedPassword);
                        } else {
                            createNewErrorDialog(R.string.err_something_wrong);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        createNewErrorDialog(R.string.err_something_wrong);
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
                                case "Something went wrong while creating user.":
                                    createNewErrorDialog(R.string.err_wrong_create_user);
                                    break;
                                case "Something went wrong while hashing the password.":
                                    createNewErrorDialog(R.string.err_wrong_password_hash);
                                    break;
                                case "User with the given email already exists.":
                                    createNewErrorDialog(R.string.err_email_exists);
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
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("firstName",firstName);
                    params.put("lastName",lastName);
                    params.put("email",email);
                    params.put("password",hashedPassword);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(registerRequest, requestTag);
        }
    }

    private void login(final String email, final String password) {
        String requestTag = "login_request";
        String url = getString(R.string.api_url) + "/user/login";
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
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
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
                params.put("password",password);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(loginRequest, requestTag);
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

    //region Password Constraints
    private static final Pattern[] passwordRegexes = new Pattern[4];{
        passwordRegexes[0] = Pattern.compile(".*[A-Z].*");
        passwordRegexes[1] = Pattern.compile(".*[a-z].*");
        passwordRegexes[2] = Pattern.compile(".*\\d.*");
        passwordRegexes[3] = Pattern.compile(".*[~!].*");
    }
    public boolean isLegalPassword(String password) {
        for (Pattern passwordRegex : passwordRegexes) {
            if (!passwordRegex.matcher(password).matches())
                return false;
        }
        return true;
    }
    //endregion
}