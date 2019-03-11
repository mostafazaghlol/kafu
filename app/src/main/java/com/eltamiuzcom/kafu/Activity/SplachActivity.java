package com.eltamiuzcom.kafu.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.eltamiuzcom.kafu.Model.getsettings.Getsetttings;
import com.eltamiuzcom.kafu.R;
import com.eltamiuzcom.kafu.Utils.screenwithoutAction;
import com.eltamiuzcom.kafu.Volley.getsettings;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class SplachActivity extends AppCompatActivity {

    private final int RC_CAMERA_AND_LOCATION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach);
        ButterKnife.bind(this);
        screenwithoutAction.FullScreen(this);
        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){
            new AlertDialog.Builder(SplachActivity.this)
                    .setTitle(getResources().getString(R.string.app_name))
                    .setMessage(getResources().getString(R.string.internet_error))
                    .setPositiveButton("OK", null).show();
        }else{
            methodRequiresTwoPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};

        if(EasyPermissions.hasPermissions(SplachActivity.this,perms)){
            sendRequest();
        }else{
            methodRequiresTwoPermission();
        }
    }
    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};

        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...

            sendRequest();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.request),
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }
   public static Getsetttings mGetinfo;
    private void sendRequest() {
        Response.Listener<String> responseListener  = response -> {
            JSONObject jsonResponse = null;
            try {
                jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean("message");
                if(success){
                    mGetinfo = new Gson().fromJson(response, Getsetttings.class);
                    startActivity(new Intent(this,LoginActivity.class));
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        };
        getsettings AccountRequest = new getsettings(responseListener);
        RequestQueue queue = Volley.newRequestQueue(SplachActivity.this);
        queue.add(AccountRequest);
    }
}
