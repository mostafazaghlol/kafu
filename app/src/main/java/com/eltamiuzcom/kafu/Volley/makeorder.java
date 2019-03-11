package com.eltamiuzcom.kafu.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class makeorder extends StringRequest {
    private static final String images_url= "https://aelaf-nuqil.com/api/makeorder";
    private Map<String, String> params;

    public makeorder(Response.Listener<String> listener, String item_id,String owner,String deliverman,String price) {
        super(Method.POST, images_url, listener, null);
        params = new HashMap<>();
        params.put("item_id",item_id);
        params.put("owner",owner);
        params.put("deliverman",deliverman);
        params.put("price",price);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}