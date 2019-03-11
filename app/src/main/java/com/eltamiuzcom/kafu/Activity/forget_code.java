package com.eltamiuzcom.kafu.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.eltamiuzcom.kafu.R;
import com.eltamiuzcom.kafu.Utils.contants;
import com.eltamiuzcom.kafu.Utils.screenwithoutAction;
import com.eltamiuzcom.kafu.Volley.forgetcode;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class forget_code extends AppCompatActivity {
    @BindView(R.id.EdCodeMissed)
    EditText EdCode;
    @BindView(R.id.Btmissedx)
    Button button;
    private String code;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code);
        ButterKnife.bind(this);
        screenwithoutAction.FullScreen(this);
        Intent intent = new Intent();
        email = intent.getStringExtra(contants.email);
        button.setOnClickListener(v->{
            if(EdCode.getText().toString().isEmpty()){
                EdCode.setError(getString(R.string.Editerror));
            }else{
                code = EdCode.getText().toString();
                sendnumber(code,email);
            }
        });
    }

    private void sendnumber(String number, String email) {
        forgetcode mForget_code = new forgetcode(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getBoolean("message")){

                    }else{
                        Toast.makeText(forget_code.this, "هذا الكود خطأ", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },email,number);
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        mRequestQueue.add(mForget_code);
    }
}
