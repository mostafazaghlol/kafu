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
import com.eltamiuzcom.kafu.Volley.forgetpass;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class forget_password extends AppCompatActivity {
    @BindView(R.id.EdEmailMissed)
    EditText EdEmail;
    @BindView(R.id.Btmissed)
    Button BtMissed;
    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        screenwithoutAction.FullScreen(this);
        ButterKnife.bind(this);
        BtMissed.setOnClickListener(v->{
            if(EdEmail.getText().toString().isEmpty()){
                EdEmail.setText(getString(R.string.Editerror));
            }else{
                number = EdEmail.getText().toString();
                sendpass(number);
            }
        });
    }

    private void sendpass(String number) {
        forgetpass mForgetpass = new forgetpass(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean message = jsonObject.getBoolean("message");
                    if(message){
                        Intent intent = new Intent(forget_password.this,forget_code.class);
                        intent.putExtra(contants.email,number);
                        startActivity(intent);
                    }else{
                        Toast.makeText(forget_password.this, "هذا البريد غير مسجل لدينا", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },number);
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        mRequestQueue.add(mForgetpass);
    }
}
