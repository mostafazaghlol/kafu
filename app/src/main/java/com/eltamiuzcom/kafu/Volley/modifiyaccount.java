package com.eltamiuzcom.kafu.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class modifiyaccount extends StringRequest {
    private static final String images_url= "https://aelaf-nuqil.com/api/updateprofile";
    private Map<String, String> params;

    public modifiyaccount(Response.Listener<String> listener, String email, String phone, String user_id,String name) {
        super(Method.POST, images_url, listener, null);
        params = new HashMap<>();
        params.put("email",email);
        params.put("phone",phone);
        params.put("user_id",user_id);
        params.put("name",name);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}