package com.eltamiuzcom.kafu.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class showaudation extends StringRequest {
    private static final String images_url= "https://aelaf-nuqil.com/api/showaudation";
    private Map<String, String> params;

    public showaudation(Response.Listener<String> listener, String it) {
        super(Method.POST, images_url, listener, null);
        params = new HashMap<>();
        params.put("audation_id",it);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}