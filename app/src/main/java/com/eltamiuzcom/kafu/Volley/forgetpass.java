package com.eltamiuzcom.kafu.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class forgetpass extends StringRequest {
    private static final String images_url= "https://aelaf-nuqil.com/api/forgetpassword";
    private Map<String, String> params;

    public forgetpass(Response.Listener<String> listener, String email){
        super(Method.POST, images_url, listener, null);
        params = new HashMap<>();
        params.put("email",email);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}