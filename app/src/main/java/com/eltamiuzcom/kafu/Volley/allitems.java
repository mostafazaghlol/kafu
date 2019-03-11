package com.eltamiuzcom.kafu.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class allitems extends StringRequest {
    private static final String images_url= "https://aelaf-nuqil.com/api/allitems";
    private Map<String, String> params;

    public allitems(Response.Listener<String> listener,String category) {
        super(Method.POST, images_url, listener, null);
        params = new HashMap<>();
        params.put("category",category);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}