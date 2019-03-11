package com.eltamiuzcom.kafu.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class profile extends StringRequest {
    private static final String images_url= "https://aelaf-nuqil.com/api/profile";
    private Map<String, String> params;

    public profile(Response.Listener<String> listener, String userid) {
        super(Method.POST, images_url, listener, null);
        params = new HashMap<>();
        params.put("user_id",userid);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}