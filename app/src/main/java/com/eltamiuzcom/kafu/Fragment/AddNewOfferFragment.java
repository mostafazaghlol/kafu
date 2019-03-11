package com.eltamiuzcom.kafu.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.MediaRouteButton;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.eltamiuzcom.kafu.Activity.LoginActivity;
import com.eltamiuzcom.kafu.Activity.MainActivity;
import com.eltamiuzcom.kafu.Activity.Register2Activity;
import com.eltamiuzcom.kafu.Activity.RegisterActivity;
import com.eltamiuzcom.kafu.Activity.SplachActivity;
import com.eltamiuzcom.kafu.Adapters.imagesAdapterForImages;
import com.eltamiuzcom.kafu.Model.UserLogin.User;
import com.eltamiuzcom.kafu.R;
import com.eltamiuzcom.kafu.Utils.RealPathUtil;
import com.eltamiuzcom.kafu.Utils.contants;
import com.google.gson.Gson;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AddNewOfferFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewOfferFragment extends Fragment {
    @BindView(R.id.EdAdress)
    EditText EdAddress;
    @BindView(R.id.SpCats)
    Spinner SpCats;
    @BindView(R.id.Sptypes)
    Spinner SpTypes;
    @BindView(R.id.SpLabanatypes)
    Spinner SpLabanatTypes;
    @BindView(R.id.EdLength)
    EditText EdLength;
    @BindView(R.id.EdWazin)
    EditText EdWazin;
    @BindView(R.id.Rephotos)
    RecyclerView RcPhotos;
    List<File> files;
    List<String> ImagePathes;
    private String Onefilepath;
    private ProgressDialog progressDialog;
    private SharedPreferences mSharedPreferences;
    private imagesAdapterForImages imagesAdapterForImages;
    private String id;
    @BindView(R.id.BtaddImages)
    Button BtAddImages;
    private ArrayAdapter<String> dataAdapter,dataAdapter2,dataAdapter3;
    private List<String> list,list2,list3;
    private Integer cat_id;
    private Integer lab_id;
    private Integer type_id;
    private String address;

    @OnClick(R.id.BtaddImages)
    public void OpenImage(){
        choosePhotoFromGallary();
    }
    @BindView(R.id.EdDescription)
    EditText EdDescription;
    @BindView(R.id.EdPrice)
    EditText EdPrice;

    String price,description,wazin,length;
    private boolean init() {
        if(EdLength.getText().toString().isEmpty()){
            EdLength.setError(getString(R.string.Editerror));
            return false;
        }
        if(EdWazin.getText().toString().isEmpty()){
            EdWazin.setError(getString(R.string.Editerror));
            return false;
        }
        if(EdDescription.getText().toString().isEmpty()){
            EdDescription.setError(getString(R.string.Editerror));
            return false;
        }
        if(EdPrice.getText().toString().isEmpty()){
            EdPrice.setError(getString(R.string.Editerror));
            return false;
        }
        if(EdAddress.getText().toString().isEmpty()){
            EdAddress.setError(getString(R.string.Editerror));
            return false;
        }
        address = EdAddress.getText().toString();
        length = EdLength.getText().toString();
        wazin = EdWazin.getText().toString();
        description = EdDescription.getText().toString();
        price = EdPrice.getText().toString();
        return true;
    }

    @OnClick(R.id.addnewback)
    public void back(){
        startActivity(new Intent(getContext(),MainActivity.class));
        getActivity().finish();
    }
    public AddNewOfferFragment() {
        // Required empty public constructor
    }

    public static AddNewOfferFragment newInstance() {
        return new AddNewOfferFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.addnewfragment, container, false);
        ButterKnife.bind(this, view);
        ImagePathes = new ArrayList<>();
        progressDialog = new ProgressDialog(getContext());
        mSharedPreferences = getActivity().getSharedPreferences(contants.pref_account,MODE_PRIVATE);
        id = String.valueOf(mSharedPreferences.getInt(contants.id,0));
        return view;
    }

    private void choosePhotoFromGallary() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager HlayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        files = new ArrayList<>();
        imagesAdapterForImages = new imagesAdapterForImages(getContext(),files,(view1, position) -> {
            new AlertDialog.Builder(getContext())
                    .setTitle(getString(R.string.app_name))
                    .setIcon(R.drawable.logo)
                    .setPositiveButton("مسح الصوره",
                            (dialog, id) -> {
                                files.remove(position);
                                imagesAdapterForImages.notifyDataSetChanged();
                                dialog.dismiss();
                            }).setNegativeButton("تغير الصوره", (dialog, id) -> {
                files.remove(position);
                imagesAdapterForImages.notifyDataSetChanged();
                choosePhotoFromGallary();
                dialog.dismiss();
            }).setNeutralButton("اضافة صور جديده", (dialog, which) -> {
                choosePhotoFromGallary();
                dialog.dismiss();
            }).show();
        });
        RcPhotos.setLayoutManager(HlayoutManager);
        RcPhotos.setAdapter(imagesAdapterForImages);
        list = new ArrayList<>();
        for(int i = 0;i<SplachActivity.mGetinfo.getData().getCats().size();i++){
            list.add(SplachActivity.mGetinfo.getData().getCats().get(i).getName());
        }
        list2 = new ArrayList<>();
        for(int i = 0;i<SplachActivity.mGetinfo.getData().getLabanatypes().size();i++){
            list2.add(SplachActivity.mGetinfo.getData().getLabanatypes().get(i).getName());
        }
        list3 = new ArrayList<>();
        for(int i = 0;i<SplachActivity.mGetinfo.getData().getItemtypes().size();i++){
            list3.add(SplachActivity.mGetinfo.getData().getItemtypes().get(i).getName());
        }
        dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, list2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter3 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, list3);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpLabanatTypes.setAdapter(dataAdapter2);
        SpTypes.setAdapter(dataAdapter3);
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
        SpLabanatTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lab_id = SplachActivity.mGetinfo.getData().getLabanatypes().get(position).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        SpTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type_id = SplachActivity.mGetinfo.getData().getItemtypes().get(position).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    //  Toast.makeText(this, "" + String.valueOf(count), Toast.LENGTH_SHORT).show();
                    //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        if (Build.VERSION.SDK_INT < 11) {
                            Onefilepath = RealPathUtil.getRealPathFromURI_BelowAPI11(getContext(), imageUri);
                        } else if (Build.VERSION.SDK_INT < 19) {
                            // SDK >= 11 && SDK < 19
                            Onefilepath = RealPathUtil.getRealPathFromURI_API11to18(getContext(), imageUri);
                        } else {
                            // SDK > 19 (Android 4.4)
                            Onefilepath = RealPathUtil.getRealPathFromURI_API19(getContext(), imageUri);

                        }
                        ImagePathes.add(Onefilepath);
                        File file = new File(Onefilepath);
                        files.add(file);
                        if (files.size() > 0) {
                            RcPhotos.setVisibility(View.VISIBLE);
                            BtAddImages.setVisibility(View.INVISIBLE);
                        }
                        imagesAdapterForImages.notifyDataSetChanged();
                        Log.e("file" + String.valueOf(Onefilepath), imageUri.toString());
                    }
                } else if (data.getData() != null) {
                    //filePath = getUriRealPath(getContext(),picUri);
                    // SDK < API11
                    if (Build.VERSION.SDK_INT < 11) {
                        Onefilepath = RealPathUtil.getRealPathFromURI_BelowAPI11(getContext(), data.getData());
                    } else if (Build.VERSION.SDK_INT < 19) {
                        // SDK >= 11 && SDK < 19

                        Onefilepath = RealPathUtil.getRealPathFromURI_API11to18(getContext(), data.getData());
                    } else {
                        // SDK > 19 (Android 4.4)
                        Onefilepath = RealPathUtil.getRealPathFromURI_API19(getContext(), data.getData());
//                        Onefilepath = data.getData().getPath();

                    }
                    ImagePathes.add(Onefilepath);
                    File file = new File(Onefilepath);
                    files.add(file);
                    if (files.size() > 0) {
                        RcPhotos.setVisibility(View.VISIBLE);
                        BtAddImages.setVisibility(View.INVISIBLE);
                    }
                    imagesAdapterForImages.notifyDataSetChanged();
                    //do something with the image (save it to some directory or whatever you need to do with it here)
                }
            }
        }
    }


    @OnClick(R.id.BtaddSure)
    public void addsure(){
        if(init()){

        sendRequest();

        }
    }

    private void sendRequest() {
        try {
            progressDialog.setIcon(R.drawable.logo);
            progressDialog.setTitle(getString(R.string.app_name));
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show();
            String uploadId = UUID.randomUUID().toString();
            Log.e("O Register", "MultiRequest");
            //Log.e("Location", filePath.toString());
            //Creating a multi part request
            MultipartUploadRequest multipartUploadRequest = new MultipartUploadRequest(getContext(), uploadId, "https://aelaf-nuqil.com/api/saveitem")
                    .addParameter("user_id", id)
                    .addParameter("title", address)
                    .addParameter("item_desc", description)
                    .addParameter("price", price)
                    .addParameter("category", String.valueOf(cat_id))
                    .addParameter("type", String.valueOf(type_id))
                    .addParameter("lang", length)
                    .addParameter("weight", wazin)
                    .addParameter("labanatype", String.valueOf(lab_id));

                if (ImagePathes.size() > 0) {
                    for (int i = 0; i < ImagePathes.size(); i++) {
                        multipartUploadRequest.addFileToUpload(ImagePathes.get(i), "items[" + String.valueOf(i) + "]");
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
                                progressDialog.dismiss();

                                JSONObject jsonObject = new JSONObject(serverResponse.getBodyAsString());
                                boolean success = jsonObject.getBoolean("message");
                                if(success){
                                    Toast.makeText(context, "تم اضافة المنتج بنجاح", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getContext(),MainActivity.class));
                                    getActivity().finish();
                                }else {
                                    Toast.makeText(context, "لم يتم اضافة المنتج", Toast.LENGTH_SHORT).show();
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
