package com.dvo.lgs.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;

/**
 * Created by Dennis van Opstal on 7-11-2017.
 */

public class BetterStringRequest extends Request<String> {
    private final Response.Listener<String> listener;

    protected BetterStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = listener;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    json,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError){
        if(volleyError.networkResponse != null && volleyError.networkResponse.data != null){
            volleyError = new VolleyError(new String(volleyError.networkResponse.data));
        }
        return volleyError;
    }

    @Override
    protected void deliverResponse(String response) {
        listener.onResponse(response);
    }
}
