package com.eltamiuzcom.kafu.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class forgetcode extends StringRequest {
    private static final String images_url= "https://aelaf-nuqil.com/api/activcode";
    private Map<String, String> params;

    public forgetcode(Response.Listener<String> listener, String email,String forgetcode){
        super(Method.POST, images_url, listener, null);
        params = new HashMap<>();
        params.put("email",email);
        params.put("forgetcode",forgetcode);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}