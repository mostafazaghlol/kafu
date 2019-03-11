package com.eltamiuzcom.kafu.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.eltamiuzcom.kafu.Model.UserLogin.User;
import com.eltamiuzcom.kafu.R;
import com.eltamiuzcom.kafu.Utils.contants;
import com.eltamiuzcom.kafu.Volley.login;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.transform.Result;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.Edname)
    EditText EdName;
    @BindView(R.id.Edemail)
    EditText Edemail;
    @BindView(R.id.Edpassword)
    EditText EdPass;
    @BindView(R.id.Edpassword2)
    EditText EdPassagain;
    @BindView(R.id.EdphoneNumber)
    EditText EdPhone;
    @BindView(R.id.locationxx)
    LinearLayout locationlinearLayout;
    @BindView(R.id.textlocation)
    TextView TxtLocation;
    @BindView(R.id.buyer)
    CheckBox RaBuer;
    @BindView(R.id.addiveser)
    CheckBox RaAddvisr;
    @BindView(R.id.mandoop)
    CheckBox RaMandoop;
    @BindView(R.id.Btsignup)
    Button Btsignupp;
    private String pass2;
    private String phone;
    private String email;
    private String xaddress;
    private ArrayAdapter<String> dataAdapter;
    private ArrayList<String> list;
    private Integer cat_id=0;
    private String device_id;
    private String filepath;

    @OnClick(R.id.Btsignup)
    public void login() {
        if(init()) {
            if (gocontinue) {
                startActivityForResult(new Intent(this, Register2Activity.class), 100);
            } else {
                if(userType.equals("1")){
                    startordnaryuserRegister();
                }else if(userType.equals("2")){
                    startmoshtaryuserRegister();
                }else  {
                    startRegister();
                }
            }
        }

    }




    String price,typeheml,model,location24,lat,lng,safty,security,userlat,userlng;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
//                String toastMsg = String.format("Place: %s", place.getName());
//                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                userlat = String.valueOf(place.getLatLng().latitude);
                userlng = String.valueOf(place.getLatLng().longitude);
                // location.setText(place.getName().toString());
                Geocoder geocoder = new Geocoder(this);
                try
                {
                    List<Address> addresses = geocoder.getFromLocation(place.getLatLng().latitude,place.getLatLng().longitude, 1);
                    if(addresses.size()>0) {
                        xaddress = addresses.get(0).getAddressLine(0);
//                    xcity = addresses.get(0).getAddressLine(1);
                        //String country = addresses.get(0).getAddressLine(2);
                        TxtLocation.setText(xaddress);
                    }
                } catch (IOException e)
                {

                    e.printStackTrace();
                }
            }
        }
        if(requestCode == 100){
            if(resultCode == RESULT_OK) {
                gocontinue = false;
                price = (String) data.getExtras().getString(contants.price);
                typeheml = (String) data.getExtras().getString(contants.typeheml);
                model = (String) data.getExtras().getString(contants.model);
                location24 = (String) data.getExtras().getString(contants.location24);
                lat = (String) data.getExtras().getString(contants.lat);
                lng = (String) data.getExtras().getString(contants.lng);
                safty = (String) data.getExtras().getString(contants.safty);
                security = (String) data.getExtras().getString(contants.security);
                filepath = (String)data.getExtras().getString("image");
                startRegister();
            }
        }
    }

    private ProgressDialog progressDialog;
    private String name,pass,firebaseToken;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;
    public static String userType;
    public  boolean gocontinue = false;
    @BindView(R.id.SpCities)
    Spinner SpCats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mSharedPreferences = getSharedPreferences(contants.pref_account,MODE_PRIVATE);
        editor = mSharedPreferences.edit();
        progressDialog = new ProgressDialog(this);
        list = new ArrayList<>();
        for(int i = 0;i<SplachActivity.mGetinfo.getData().getCats().size();i++){
            list.add(SplachActivity.mGetinfo.getData().getCats().get(i).getName());
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpCats.setAdapter(dataAdapter);
        SpCats.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cat_id = SplachActivity.mGetinfo.getData().getCats().get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        return;
                    }
                    String token = task.getResult().getToken();
                    firebaseToken = token;
                });
        RaBuer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                userType = "1";
                Btsignupp.setText("تسجيل حساب جديد");
                gocontinue = false;
            }
        });
        RaAddvisr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                userType = "2";
                Btsignupp.setText("تسجيل حساب جديد");
                gocontinue = false;
            }
        });
        RaMandoop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    userType = "3";
                    Btsignupp.setText("استمرار");
                    gocontinue = true;
                }else{
                    Btsignupp.setText("تسجيل حساب جديد");
                    gocontinue = false;
                }
            }
        });
        locationlinearLayout.setOnClickListener(v->{
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            try {
                startActivityForResult(builder.build(this), 1);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        });

    }
    private boolean init() {
        if(EdName.getText().toString().isEmpty()){
            EdName.setError(getString(R.string.Editerror));
            return false;
        }
        if(EdPass.getText().toString().isEmpty()){
            EdPass.setError(getString(R.string.Editerror));
            return false;
        }
        if(EdPassagain.getText().toString().isEmpty()){
            EdPassagain.setError(getString(R.string.Editerror));
            return false;
        }
        if(EdPhone.getText().toString().isEmpty()){
            EdPhone.setError(getString(R.string.Editerror));
            return false;
        }
        if(Edemail.getText().toString().isEmpty()){
            Edemail.setError(getString(R.string.Editerror));
            return false;
        }
        email = Edemail.getText().toString();
        name = EdName.getText().toString();
        pass = EdPass.getText().toString();
        pass2 = EdPassagain.getText().toString();
        if(!pass.equals(pass2)){
            EdPassagain.setError(getString(R.string.passerror));
            return false;
        }
        phone = EdPhone.getText().toString();
        if(userlat.isEmpty()){
            Toast.makeText(this, "اختر الموقع الخاص بك !", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    //start Register
    private void startRegister() {
        try {
            progressDialog.setIcon(R.drawable.logo);
            progressDialog.setTitle(getString(R.string.app_name));
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show();
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                device_id = "0";
            } else {
                device_id = telephonyManager.getDeviceId();
            }
            String uploadId = UUID.randomUUID().toString();
            Log.e("O Register", "MultiRequest");
            //Log.e("Location", filePath.toString());
            //Creating a multi part request
            MultipartUploadRequest multipartUploadRequest = new MultipartUploadRequest(RegisterActivity.this, uploadId, "https://aelaf-nuqil.com/api/register")
                    .addParameter("email", email)
                    .addParameter("name", name)
                    .addParameter("phone", phone)
                    .addParameter("password", pass)
                    .addParameter("confirmpass", pass)
                    .addParameter("firebase_token", firebaseToken)
                    .addParameter("device_id", device_id)
                    .addParameter("role", String.valueOf(userType))
                    .addParameter("lat", userlat)
                    .addParameter("lng", userlng)
                    .addParameter("category_id", String.valueOf(cat_id))
                    .addParameter("price",price)
                    .addParameter("type",typeheml)
                    .addParameter("modal",model)
                    .addParameter("possibility","متاح")
                    .addParameter("lat",lat)
                    .addParameter("lng",lng)
                    .addParameter("safety",safty)
                    .addParameter("percent",security);
            if (!filepath.equals("0")) {
                multipartUploadRequest.addFileToUpload(filepath, "image");
            }
            if(Register2Activity.filePath != null) {
                if (Register2Activity.filePath.size() > 0) {
                    for (int i = 0; i < Register2Activity.filePath.size(); i++) {
                        multipartUploadRequest.addFileToUpload(Register2Activity.filePath.get(i), "items[" + String.valueOf(i) + "]");
                    }
                }
            }

            UploadNotificationConfig mUploadNotificationConfig = new UploadNotificationConfig()
                    .setRingToneEnabled(true)
                    .setTitleForAllStatuses("جارى التسجيل");
            mUploadNotificationConfig.getCompleted().autoClear = true;
            mUploadNotificationConfig.setIconForAllStatuses(R.drawable.logo);
            String request = multipartUploadRequest
                    .setUtf8Charset()
                    .setNotificationConfig(mUploadNotificationConfig)
                    .setMaxRetries(10)
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {
                            // your code here
                        }

                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse,
                                            Exception exception) {
                            // your code here
//                            Toast.makeText(RegisterActivity.this, getString(R.string.Error), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            try {
                                JSONObject jsonObject = new JSONObject(serverResponse.getBodyAsString());
                                boolean success = jsonObject.getBoolean("message");
                                if (success) {
                                    User mUser = new Gson().fromJson(serverResponse.getBodyAsString(), User.class);
                                    if(mUser.getData().getRole().equals(1)) {
                                        editor.putString(contants.username, mUser.getData().getName());
                                        editor.putString(contants.email, mUser.getData().getEmail());
                                        editor.putString(contants.phone, mUser.getData().getPhone());
                                        editor.putString(contants.role,mUser.getData().getRole().toString());
                                        editor.putInt(contants.id, mUser.getData().getId());
                                        editor.apply();
                                        editor.commit();
                                        progressDialog.dismiss();
                                        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                                        finish();
                                    }else {
                                        Toast.makeText(context, " تم تسجيل الحساب برجاء إنتظار تأكيد الإدارة", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                            finish();
                                        }
                                    } else {
                                    Toast.makeText(context, "لم يتم تسجيل الحساب", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                    if (jsonObject1.has("email")) {
                                        Edemail.setError("هذا البريد مستخدم من قبل");
                                    }
                                    if (jsonObject1.has("phone")) {
                                        EdPhone.setError("هذا الرقم مستخدم من قبل");
                                    }
                                    if (jsonObject1.has("name")) {
                                        EdName.setError("هذا الاسم مستخدم من قبل");
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {
                            // your code here
                        }
                    })
                    .startUpload();
        } catch (Exception e) {

        }
    }

    //start Register
    private void startmoshtaryuserRegister() {
        try {
            progressDialog.setIcon(R.drawable.logo);
            progressDialog.setTitle(getString(R.string.app_name));
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show();
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                device_id = "0";
            } else {
                device_id = telephonyManager.getDeviceId();
            }
            String uploadId = UUID.randomUUID().toString();
            Log.e("O Register", "MultiRequest");
            //Log.e("Location", filePath.toString());
            //Creating a multi part request
            MultipartUploadRequest multipartUploadRequest = new MultipartUploadRequest(RegisterActivity.this, uploadId, "https://aelaf-nuqil.com/api/register")
                    .addParameter("email", email)
                    .addParameter("name", name)
                    .addParameter("phone", phone)
                    .addParameter("password", pass)
                    .addParameter("confirmpass", pass)
                    .addParameter("firebase_token", firebaseToken)
                    .addParameter("device_id", device_id)
                    .addParameter("role", "2")
                    .addParameter("lat", userlat)
                    .addParameter("lng", userlng)
                    .addParameter("category_id",String.valueOf(cat_id));

            UploadNotificationConfig mUploadNotificationConfig = new UploadNotificationConfig()
                    .setRingToneEnabled(true)
                    .setTitleForAllStatuses("جارى التسجيل");
            mUploadNotificationConfig.getCompleted().autoClear = true;
            mUploadNotificationConfig.setIconForAllStatuses(R.drawable.logo);
            String request = multipartUploadRequest
                    .setUtf8Charset()
                    .setNotificationConfig(mUploadNotificationConfig)
                    .setMaxRetries(10)
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {
                            // your code here
                        }

                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse,
                                            Exception exception) {
                            // your code here
//                            Toast.makeText(RegisterActivity.this, getString(R.string.Error), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            try {
                                JSONObject jsonObject = new JSONObject(serverResponse.getBodyAsString());
                                boolean success = jsonObject.getBoolean("message");
                                if (success) {
                                    User mUser = new Gson().fromJson(serverResponse.getBodyAsString(), User.class);
                                    if(mUser.getData().getRole().equals(1)) {
                                        editor.putString(contants.username, mUser.getData().getName());
                                        editor.putString(contants.email, mUser.getData().getEmail());
                                        editor.putString(contants.phone, mUser.getData().getPhone());
                                        editor.putString(contants.role,mUser.getData().getRole().toString());
                                        editor.putInt(contants.id, mUser.getData().getId());
                                        editor.apply();
                                        editor.commit();
                                        progressDialog.dismiss();
                                        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                                        finish();
                                    }else {
                                        Toast.makeText(context, " تم تسجيل الحساب برجاء إنتظار تأكيد الإدارة", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        finish();
                                    }
                                } else {
                                    Toast.makeText(context, "لم يتم تسجيل الحساب", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                    if (jsonObject1.has("email")) {
                                        Edemail.setError("هذا البريد مستخدم من قبل");
                                    }
                                    if (jsonObject1.has("phone")) {
                                        EdPhone.setError("هذا الرقم مستخدم من قبل");
                                    }
                                    if (jsonObject1.has("name")) {
                                        EdName.setError("هذا الاسم مستخدم من قبل");
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {
                            // your code here
                        }
                    })
                    .startUpload();
        } catch (Exception e) {

        }
    }//start Register
    private void startordnaryuserRegister() {
        try {
            progressDialog.setIcon(R.drawable.logo);
            progressDialog.setTitle(getString(R.string.app_name));
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show();
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                device_id = "0";
            } else {
                device_id = telephonyManager.getDeviceId();
            }
            String uploadId = UUID.randomUUID().toString();
            Log.e("O Register", "MultiRequest");
            //Log.e("Location", filePath.toString());
            //Creating a multi part request
            MultipartUploadRequest multipartUploadRequest = new MultipartUploadRequest(RegisterActivity.this, uploadId, "https://aelaf-nuqil.com/api/register")
                    .addParameter("email", email)
                    .addParameter("name", name)
                    .addParameter("phone", phone)
                    .addParameter("password", pass)
                    .addParameter("confirmpass", pass)
                    .addParameter("firebase_token", firebaseToken)
                    .addParameter("device_id", device_id)
                    .addParameter("role", "1")
                    .addParameter("lat", userlat)
                    .addParameter("lng", userlng);

            UploadNotificationConfig mUploadNotificationConfig = new UploadNotificationConfig()
                    .setRingToneEnabled(true)
                    .setTitleForAllStatuses("جارى التسجيل");
            mUploadNotificationConfig.getCompleted().autoClear = true;
            mUploadNotificationConfig.setIconForAllStatuses(R.drawable.logo);
            String request = multipartUploadRequest
                    .setUtf8Charset()
                    .setNotificationConfig(mUploadNotificationConfig)
                    .setMaxRetries(10)
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {
                            // your code here
                        }

                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse,
                                            Exception exception) {
                            // your code here
//                            Toast.makeText(RegisterActivity.this, getString(R.string.Error), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            try {
                                JSONObject jsonObject = new JSONObject(serverResponse.getBodyAsString());
                                boolean success = jsonObject.getBoolean("message");
                                if (success) {
                                    User mUser = new Gson().fromJson(serverResponse.getBodyAsString(), User.class);
                                    if(mUser.getData().getRole().equals(1)) {
                                        editor.putString(contants.username, mUser.getData().getName());
                                        editor.putString(contants.email, mUser.getData().getEmail());
                                        editor.putString(contants.phone, mUser.getData().getPhone());
                                        editor.putString(contants.role,mUser.getData().getRole().toString());
                                        editor.putInt(contants.id, mUser.getData().getId());
                                        editor.apply();
                                        editor.commit();
                                        progressDialog.dismiss();
                                        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                                        finish();
                                    }else {
                                        Toast.makeText(context, " تم تسجيل الحساب برجاء إنتظار تأكيد الإدارة", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        finish();
                                    }
                                } else {
                                    Toast.makeText(context, "لم يتم تسجيل الحساب", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                    if (jsonObject1.has("email")) {
                                        Edemail.setError("هذا البريد مستخدم من قبل");
                                    }
                                    if (jsonObject1.has("phone")) {
                                        EdPhone.setError("هذا الرقم مستخدم من قبل");
                                    }
                                    if (jsonObject1.has("name")) {
                                        EdName.setError("هذا الاسم مستخدم من قبل");
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {
                            // your code here
                        }
                    })
                    .startUpload();
        } catch (Exception e) {

        }
    }
}
