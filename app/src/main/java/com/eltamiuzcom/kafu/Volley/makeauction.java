package com.eltamiuzcom.kafu.Volley;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class makeauction extends StringRequest {
    private static final String images_url= "https://aelaf-nuqil.com/api/makeaudation";
    private Map<String, String> params;

    public makeauction(Response.Listener<String> listener, String user_id, String auctiontype, String auction_id, String price) {
        super(Method.POST, images_url, listener, null);
        params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("auctiontype",auctiontype);
        params.put("auction_id",auction_id);
        params.put("price",price);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}