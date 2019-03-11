package com.eltamiuzcom.kafu.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class volleyclass extends StringRequest {
    private static final String images_url= "https://aelaf-nuqil.com/api/login";
    private Map<String, String> params;

    public volleyclass(Response.Listener<String> listener, String email, String pasword, String firebase) {
        super(Method.POST, images_url, listener, null);
        params = new HashMap<>();
        params.put("email",email);
        params.put("password",pasword);
        params.put("firebase_token",firebase);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}