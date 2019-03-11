package com.eltamiuzcom.kafu.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.eltamiuzcom.kafu.Model.UserLogin.User;
import com.eltamiuzcom.kafu.R;
import com.eltamiuzcom.kafu.Utils.contants;
import com.eltamiuzcom.kafu.Volley.login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.fName)
    EditText EdName;
    @BindView(R.id.fPass)
    EditText EdPass;
    @OnClick(R.id.BtLoginlogin1)
    public void login() {
        if(init()){
            progressDialog.setTitle(getString(R.string.app_name));
            progressDialog.setTitle(getString(R.string.loading));
            progressDialog.show();
            login mLogin = new login(response -> {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean message = jsonObject.getBoolean("message");
                    if(message){
                        User mUser = new Gson().fromJson(response,User.class);
                        editor.putString(contants.username, mUser.getData().getName());
                        editor.putString(contants.email, mUser.getData().getEmail());
                        editor.putString(contants.phone, mUser.getData().getPhone());
                        editor.putString(contants.role,mUser.getData().getRole().toString());
                        editor.putInt(contants.id, mUser.getData().getId());
                        editor.apply();
                        editor.commit();
                        progressDialog.dismiss();
                        startActivity(new Intent(this,MainActivity.class));
                        finish();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(this, getString(R.string.loginerorr), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            },name,pass,firebaseToken);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(mLogin);
        }
    }
    @OnClick(R.id.BtLoginlogin2)
    public void register(){
        startActivity(new Intent(this,RegisterActivity.class));
        finish();
    }
    private ProgressDialog progressDialog;
    private String name,pass,firebaseToken;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean init() {
        if(EdName.getText().toString().isEmpty()){
            EdName.setError(getString(R.string.Editerror));
            return false;
        }
        if(EdPass.getText().toString().isEmpty()){
            EdPass.setError(getString(R.string.Editerror));
            return false;
        }
        name = EdName.getText().toString();
        pass = EdPass.getText().toString();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mSharedPreferences = getSharedPreferences(contants.pref_account,MODE_PRIVATE);
        editor = mSharedPreferences.edit();
        progressDialog = new ProgressDialog(this);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        return;
                    }
                    String token = task.getResult().getToken();
                    firebaseToken = token;
                });
        if(mSharedPreferences.contains(contants.id)){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
    }

    @OnClick(R.id.forgetpasww)
    public void forgetpassww(){
        startActivity(new Intent(this,forget_password.class));
    }
}
