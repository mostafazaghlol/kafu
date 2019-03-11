package com.eltamiuzcom.kafu.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class myorder extends StringRequest {
    private static final String images_url= "https://aelaf-nuqil.com/api/myorders";
    private Map<String, String> params;

    public myorder(Response.Listener<String> listener, String user_id, String user_role, String status) {
        super(Method.POST, images_url, listener, null);
        params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("user_role",user_role);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}