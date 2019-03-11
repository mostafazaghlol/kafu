package com.eltamiuzcom.kafu.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class showitem extends StringRequest {
    private static final String images_url= "https://aelaf-nuqil.com/api/showitem";
    private Map<String, String> params;

    public showitem(Response.Listener<String> listener,String it) {
        super(Method.POST, images_url, listener, null);
        params = new HashMap<>();
        params.put("item_id",it);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}