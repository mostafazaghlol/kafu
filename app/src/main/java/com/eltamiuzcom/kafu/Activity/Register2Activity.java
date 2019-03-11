package com.eltamiuzcom.kafu.Activity;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eltamiuzcom.kafu.R;
import com.eltamiuzcom.kafu.Utils.RealPathUtil;
import com.eltamiuzcom.kafu.Utils.contants;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Register2Activity extends AppCompatActivity {
    @BindView(R.id.personalphoto)
    LinearLayout personalimage;
    @BindView(R.id.markbaphoto)
    LinearLayout maikbaphoto;
    @BindView(R.id.Edpricex)
    EditText EdPrice;
    @BindView(R.id.EdTypeheml)
    EditText EdTypeHeml;
    @BindView(R.id.EdModel)
    EditText EdModel;
    @BindView(R.id.EdLocation24)
    EditText EdLocation24;
    @BindView(R.id.markbalocation)
    LinearLayout linearLayoutLocaion;
    @BindView(R.id.Edsafty)
    EditText EdSafty;
    @BindView(R.id.Edsecurityofmontage)
    EditText EdSecurityofmontage;
    @BindView(R.id.Btfinish)
    Button finish;
    int personalcode = 11,markbacode = 12;
    String price,typeheml,model,location24,lat,lng,safty,security;
    private String Onefilepath,personalImage;
    public static ArrayList<String> filePath;
    private String xaddress;
    private String xcity;
    private TextView location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_shohna);
        ButterKnife.bind(this);
        filePath = new ArrayList<>();
        location = findViewById(R.id.location);
        personalimage.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), personalcode);
        });
        maikbaphoto.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), markbacode);
        });
        linearLayoutLocaion.setOnClickListener(v->{
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            try {
                startActivityForResult(builder.build(this), 1);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        });
        finish.setOnClickListener(v->{
            if(init()){
                Intent intent = new Intent();
                intent.putExtra(contants.price,this.price);
                intent.putExtra(contants.typeheml,this.typeheml);
                intent.putExtra(contants.model,this.model);
                intent.putExtra(contants.location24,this.location24);
                intent.putExtra(contants.lat,this.lat);
                intent.putExtra(contants.lng,this.lng);
                intent.putExtra(contants.safty,this.safty);
                intent.putExtra(contants.security,this.security);
                intent.putExtra("image",personalImage);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
//                String toastMsg = String.format("Place: %s", place.getName());
//                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                lat = String.valueOf(place.getLatLng().latitude);
                lng = String.valueOf(place.getLatLng().longitude);
                // location.setText(place.getName().toString());
                Geocoder geocoder = new Geocoder(this);
                try
                {
                    List<Address> addresses = geocoder.getFromLocation(place.getLatLng().latitude,place.getLatLng().longitude, 1);
                    xaddress = addresses.get(0).getAddressLine(0);
                    xcity = addresses.get(0).getAddressLine(1);
                    //String country = addresses.get(0).getAddressLine(2);
                    location.setText(xaddress);

                } catch (IOException e)
                {

                    e.printStackTrace();
                }
            }
        }
        if (requestCode == personalcode) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    //  Toast.makeText(getActivity(), "" + String.valueOf(count), Toast.LENGTH_SHORT).show();
                    //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        if (Build.VERSION.SDK_INT < 11) {
                            Onefilepath = RealPathUtil.getRealPathFromURI_BelowAPI11(Register2Activity.this, imageUri);
                        } else if (Build.VERSION.SDK_INT < 19) {
                            // SDK >= 11 && SDK < 19
                            Onefilepath = RealPathUtil.getRealPathFromURI_API11to18(Register2Activity.this, imageUri);
                        } else {
                            // SDK > 19 (Android 4.4)
                            Onefilepath = RealPathUtil.getRealPathFromURI_API19(Register2Activity.this, imageUri);

                        }
                        //filePath.add(Onefilepath);
                        Log.e("file" + String.valueOf(Onefilepath), imageUri.toString());
                    }
                    //  Toast.makeText(getActivity(), "" + imagesList.size(), Toast.LENGTH_SHORT).show();

                    //do something with the image (save it to some directory or whatever you need to do with it here)
                } else if (data.getData() != null) {
                    //filePath = getUriRealPath(getContext(),picUri);
                    // SDK < API11
                    if (Build.VERSION.SDK_INT < 11) {
                        Onefilepath = RealPathUtil.getRealPathFromURI_BelowAPI11(Register2Activity.this, data.getData());
                    } else if (Build.VERSION.SDK_INT < 19) {
                        // SDK >= 11 && SDK < 19

                        Onefilepath = RealPathUtil.getRealPathFromURI_API11to18(Register2Activity.this, data.getData());
                    } else {
                        // SDK > 19 (Android 4.4)
                        Onefilepath = RealPathUtil.getRealPathFromURI_API19(Register2Activity.this, data.getData());
//                        Onefilepath = data.getData().getPath();

                    }
                    // filePath.add(Onefilepath);
                    personalImage = Onefilepath;
                    Log.e("file", Onefilepath.toString());

                    //do something with the image (save it to some directory or whatever you need to do with it here)
                }

            }

        }
        if (requestCode == markbacode) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        if (Build.VERSION.SDK_INT < 11) {
                            Onefilepath = RealPathUtil.getRealPathFromURI_BelowAPI11(Register2Activity.this, imageUri);
                        } else if (Build.VERSION.SDK_INT < 19) {
                            // SDK >= 11 && SDK < 19
                            Onefilepath = RealPathUtil.getRealPathFromURI_API11to18(Register2Activity.this, imageUri);
                        } else {
                            // SDK > 19 (Android 4.4)
                            Onefilepath = RealPathUtil.getRealPathFromURI_API19(Register2Activity.this, imageUri);

                        }
                        filePath.add(Onefilepath);
                        Log.e("file" + String.valueOf(Onefilepath), imageUri.toString());
                    }
                    //  Toast.makeText(getActivity(), "" + imagesList.size(), Toast.LENGTH_SHORT).show();

                    //do something with the image (save it to some directory or whatever you need to do with it here)
                } else if (data.getData() != null) {
                    //filePath = getUriRealPath(getContext(),picUri);
                    // SDK < API11
                    if (Build.VERSION.SDK_INT < 11) {
                        Onefilepath = RealPathUtil.getRealPathFromURI_BelowAPI11(Register2Activity.this, data.getData());
                    } else if (Build.VERSION.SDK_INT < 19) {
                        // SDK >= 11 && SDK < 19

                        Onefilepath = RealPathUtil.getRealPathFromURI_API11to18(Register2Activity.this, data.getData());
                    } else {
                        // SDK > 19 (Android 4.4)
                        Onefilepath = RealPathUtil.getRealPathFromURI_API19(Register2Activity.this, data.getData());
//                        Onefilepath = data.getData().getPath();

                    }
                    filePath.add(Onefilepath);
                    Log.e("file", Onefilepath.toString());

                    //do something with the image (save it to some directory or whatever you need to do with it here)
                }

            }
        }
    }

    public boolean init(){
        if(filePath.size()  == 0){
            Toast.makeText(this, "اختر صور المركبه ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(personalImage.isEmpty()){
            Toast.makeText(this, "اختر صوره الشخصيه ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(EdTypeHeml.getText().toString().isEmpty()){
            EdTypeHeml.setError(getString(R.string.Editerror));
            return false;
        }
        if(EdLocation24.getText().toString().isEmpty()){
            EdLocation24.setError(getString(R.string.Editerror));
            return false;
        }
        if(EdModel.getText().toString().isEmpty()){
            EdModel.setError(getString(R.string.Editerror));
            return false;
        }
        if(EdPrice.getText().toString().isEmpty()){
            EdPrice.setError(getString(R.string.Editerror));
            return false;
        }
        if(EdSafty.getText().toString().isEmpty()){
            EdSafty.setError(getString(R.string.Editerror));
            return false;
        }
        if(EdSecurityofmontage.getText().toString().isEmpty()){
            EdSecurityofmontage.setError(getString(R.string.Editerror));
            return false;
        }
        if(lat.isEmpty()){
            Toast.makeText(this, "اختر الموقع", Toast.LENGTH_SHORT).show();
            return false;
        }
        price = EdPrice.getText().toString();
        typeheml = EdTypeHeml.getText().toString();
        model = EdModel.getText().toString();
        location24 = EdLocation24.getText().toString();
        safty = EdSafty.getText().toString();
        security = EdSecurityofmontage.getText().toString();
        return true;
    }
}
