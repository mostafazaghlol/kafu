package com.eltamiuzcom.kafu.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class myfawteer extends StringRequest {
    private static final String images_url= "https://aelaf-nuqil.com/api/mybills";
    private Map<String, String> params;

    public myfawteer(Response.Listener<String> listener, String id) {
        super(Method.POST, images_url, listener, null);
        params = new HashMap<>();
        params.put("user_id",id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}