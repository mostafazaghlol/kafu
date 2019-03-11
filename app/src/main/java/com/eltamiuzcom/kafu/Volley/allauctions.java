package com.eltamiuzcom.kafu.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class allauctions extends StringRequest {
    private static final String images_url= "https://aelaf-nuqil.com/api/allauctions";
    private Map<String, String> params;

    public allauctions(Response.Listener<String> listener,String auctiontype) {
        super(Method.POST, images_url, listener, null);
        params = new HashMap<>();
        params.put("auctiontype",auctiontype);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}