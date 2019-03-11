package com.eltamiuzcom.kafu.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.eltamiuzcom.kafu.Activity.MainActivity;
import com.eltamiuzcom.kafu.Activity.MyAuctionsActivity;
import com.eltamiuzcom.kafu.Activity.SplachActivity;
import com.eltamiuzcom.kafu.Adapters.catsAdapter;
import com.eltamiuzcom.kafu.Adapters.offersAdapter;
import com.eltamiuzcom.kafu.Model.Fawteer.Fawteer;
import com.eltamiuzcom.kafu.Model.UserLogin.User;
import com.eltamiuzcom.kafu.Model.getsettings.Cat;
import com.eltamiuzcom.kafu.Model.montagat.Datum;
import com.eltamiuzcom.kafu.Model.montagat.Montagat;
import com.eltamiuzcom.kafu.Model.profile.CarInfo;
import com.eltamiuzcom.kafu.Model.profile.Profile;
import com.eltamiuzcom.kafu.R;
import com.eltamiuzcom.kafu.Utils.contants;
import com.eltamiuzcom.kafu.Volley.allitems;
import com.eltamiuzcom.kafu.Volley.modifiyaccount;
import com.eltamiuzcom.kafu.Volley.profile;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    @BindView(R.id.Titleusername)
    TextView TitleUsername;
    @BindView(R.id.TxtUsername)
    TextView Txtusername;
    @BindView(R.id.TxtUserPhone)
    TextView userphone;
    @BindView(R.id.EdModName)
    EditText Edname;
    @BindView(R.id.EdModPhone)
    EditText Edphone;
    @OnClick(R.id.accountaboutback)
    public void back(){
        startActivity(new Intent(getContext(),MainActivity.class));
        getActivity().finish();
    }

    @BindView(R.id.Btmodifiyaccount)
    Button v;
    ProgressDialog progressDialog;
    private boolean first=true;
    private String email;
    private String id;
    private SharedPreferences.Editor editor;

    @OnClick(R.id.BtFawteer)
    public void goFawteer(){
        FawteerFragment nextFrag= FawteerFragment.newInstance();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }
    @OnClick(R.id.Btmodifiyaccount)
    public void goModifiy(){
        if(first) {
            Txtusername.setVisibility(View.GONE);
            userphone.setVisibility(View.GONE);
            Edname.setVisibility(View.VISIBLE);
            Edphone.setVisibility(View.VISIBLE);
            v.setText("قم تعديل");
            first = false;
        }else{
            if(init()){
                progressDialog.setIcon(R.drawable.logo);
                progressDialog.setTitle(getString(R.string.app_name));
                progressDialog.setMessage(getString(R.string.loading));
                progressDialog.show();
                modifiyaccount mModifiyaccount = new modifiyaccount(response -> {
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
                            finishupdate();
//                            startActivity(new Intent(this,MainActivity.class));
//                            finish();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), getString(R.string.loginerorr), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },email,Edphone.getText().toString(),id,Edname.getText().toString());
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(mModifiyaccount);
            }
        }
    }

    private void finishupdate() {
        Edname.setVisibility(View.GONE);
        Edphone.setVisibility(View.GONE);
        Txtusername.setVisibility(View.VISIBLE);
        userphone.setVisibility(View.VISIBLE);
        username = mSharedPreferences.getString(contants.username,"");
        phone = mSharedPreferences.getString(contants.phone,"");
        email = mSharedPreferences.getString(contants.email,"");
        id = String.valueOf(mSharedPreferences.getInt(contants.id,0));
        TitleUsername.setText(username);
        Txtusername.setText(username);
        userphone.setText(phone);
    }

    private boolean init() {
        if(Edname.getText().toString().isEmpty()){
            Edname.setError(getString(R.string.Editerror));
            return false;
        }
        if(Edphone.getText().toString().isEmpty()){
            Edphone.setError(getString(R.string.Editerror));
            return false;
        }
        return true;
    }

    @OnClick(R.id.BtMyOrders)
    public void goMyOrder(){
        MyOrdersFragment nextFrag= MyOrdersFragment.newInstance();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }
    @BindView(R.id.mandoopimage)
    ImageView mandoopImage;
    @BindView(R.id.markbaimage)
    ImageView MarkbaImage;
    @BindView(R.id.TxtTawseelmodel)
    TextView TxtModel;
    @BindView(R.id.TxtTawseelmohafzarate)
    TextView TxtMohafzeRate;
    @BindView(R.id.TxtTawseelnumberofseats)
    TextView TxtNumberOfSeats;
    @BindView(R.id.TxtTawseelPlace)
    TextView TxtPlace;
    @BindView(R.id.TxtTawseelPrice)
    TextView TxtPrice;
    @BindView(R.id.TxtTawseelSecurity)
    TextView TxtSecurity;
    @BindView(R.id.mandooplinear)
    LinearLayout MandoopLinear;
    SharedPreferences mSharedPreferences;
    String role,username,phone;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.accountfragment, container, false);
        ButterKnife.bind(this, view);
        mSharedPreferences = getActivity().getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
        progressDialog = new ProgressDialog(getContext());
        role = mSharedPreferences.getString(contants.role,"0");
        if(role.equals("1")||role.equals("2")){
            MandoopLinear.setVisibility(View.GONE);
            mandoopImage.setVisibility(View.GONE);
        }
        username = mSharedPreferences.getString(contants.username,"");
        phone = mSharedPreferences.getString(contants.phone,"");
        email = mSharedPreferences.getString(contants.email,"");
        id = String.valueOf(mSharedPreferences.getInt(contants.id,0));
        TitleUsername.setText(username);
        Txtusername.setText(username);
        userphone.setText(phone);
        if(role.equals("3")){
            addfackdata();
        }
        return view;
    }

    private void addfackdata() {
        profile mprofile = new profile(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean message = jsonObject.getBoolean("message");
                    if(message){
                        Profile mProfile = new Gson().fromJson(response,Profile.class);
                        CarInfo carInfo = mProfile.getData().getCarInfo();
                        Picasso.get().load(contants.url+carInfo.getImages().get(0).getImage()).placeholder(R.drawable.fackphoto).into(MarkbaImage);
                        Picasso.get().load(contants.url+carInfo.getImage()).placeholder(R.drawable.fackphoto).into(mandoopImage);
                        TxtModel.setText(carInfo.getModal());
                        TxtMohafzeRate.setText(carInfo.getSafety());
                        TxtNumberOfSeats.setText("0");
                        TxtPrice.setText(carInfo.getPrice()+" ريال سعودي");
                        TxtSecurity.setText(carInfo.getPercent());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },id);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(mprofile);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


}
