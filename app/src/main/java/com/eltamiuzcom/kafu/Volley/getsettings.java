package com.eltamiuzcom.kafu.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class getsettings extends StringRequest {
    private static final String images_url= "https://aelaf-nuqil.com/api/settinginfo";
    private Map<String, String> params;

    public getsettings(Response.Listener<String> listener) {
        super(Method.POST, images_url, listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}