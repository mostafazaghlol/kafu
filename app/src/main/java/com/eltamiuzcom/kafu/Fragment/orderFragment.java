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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.eltamiuzcom.kafu.Activity.MainActivity;
import com.eltamiuzcom.kafu.Adapters.fawteersAdapter;
import com.eltamiuzcom.kafu.Model.Fawteer.Datum;
import com.eltamiuzcom.kafu.Model.Fawteer.Fawteer;
import com.eltamiuzcom.kafu.Model.montagat.Montagat;
import com.eltamiuzcom.kafu.Model.montage.Montage;
import com.eltamiuzcom.kafu.R;
import com.eltamiuzcom.kafu.Utils.contants;
import com.eltamiuzcom.kafu.Volley.makeorder;
import com.eltamiuzcom.kafu.Volley.myfawteer;
import com.eltamiuzcom.kafu.Volley.showitem;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link orderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class orderFragment extends Fragment {
    private ProgressDialog progressDialog;
    @OnClick(R.id.orderback)
    public void back(){
        startActivity(new Intent(getContext(),MainActivity.class));
        getActivity().finish();
    }
    @BindView(R.id.montagehname) TextView TxtTitle;
    @BindView(R.id.montagephoto) ImageView imageView;
    @BindView(R.id.Txtmontagedate) TextView Txtmontagedate;
    @BindView(R.id.TxtmontageDes)
    HtmlTextView Txtmontagedesc;
    @BindView(R.id.TxtmontageLanbanatype) TextView Txtmontagelabanatype;
    @BindView(R.id.TxtmontageLength) TextView TxtmontageLength;
    @BindView(R.id.TxtmontageTypexx) TextView TxtmontageType;
    @BindView(R.id.TxtmontageType) TextView TxtmontageType2;
    @BindView(R.id.Txtmontageweight) TextView TxtWeight;
    @BindView(R.id.Txtbuyername) TextView TxtBuyerName;
    @BindView(R.id.Txtmontageprice) TextView TxtmontagePrice;
    @BindView(R.id.Btorder)
    Button BtOrder;
    @OnClick(R.id.Btorder)
    public void order(){
        progressDialog.show();
        makeorder mMakeOrder = new makeorder(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean message = jsonObject.getBoolean("message");
                    if(message){
                        Toast.makeText(getContext(), "تم الطلب بنجاح", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },m.getData().getId().toString(),id,"0",m.getData().getPrice().toString());
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(mMakeOrder);
    }
    public static String orderid;
    public SharedPreferences mSharedPreferences;
    public String id, role;
    public orderFragment() {
        // Required empty public constructor
    }


    public static orderFragment newInstance(String orderid) {
        orderFragment.orderid = orderid;
        return new orderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.singleorderfragment, container, false);
        ButterKnife.bind(this, view);
        progressDialog = new ProgressDialog(getContext());
        mSharedPreferences = getActivity().getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        id = String.valueOf(mSharedPreferences.getInt(contants.id,0));
        role = String.valueOf(mSharedPreferences.getString(contants.role,"0"));
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(role.equals("3")||role.equals("2")){
            BtOrder.setVisibility(View.INVISIBLE);
        }
        addfackdata();
    }

    private void addfackdata() {
        progressDialog.setIcon(R.drawable.logo);
        progressDialog.setTitle(getString(R.string.app_name));
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();
        showitem mShowitem = new showitem(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean state = jsonObject.getBoolean("message");
                    if(state){
                        Montage montage = new Gson().fromJson(response,Montage.class);
                        updateUI(montage);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        },orderid);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(mShowitem);
    }
    public  Montage m;
    private void updateUI(Montage montage) {
        m = montage;
        progressDialog.dismiss();
        if(montage.getData().getImages().size() > 0) {
            Picasso.get().load(contants.url + montage.getData().getImages().get(0).getImage()).into(imageView);
        }
        TxtBuyerName.setText(montage.getData().getUsername());
        TxtmontagePrice.setText(montage.getData().getPrice()+"ريال");
        Txtmontagedate.setText(montage.getData().getCreatedAt());
        Txtmontagedesc.setHtml(montage.getData().getItemDesc());
        Txtmontagelabanatype.setText(montage.getData().getType());
        TxtmontageType.setText(montage.getData().getType());
        TxtmontageType2.setText(montage.getData().getType());
        TxtmontageLength.setText(montage.getData().getLang());
        TxtWeight.setText(montage.getData().getWeight());
        TxtTitle.setText(montage.getData().getTitle());
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


}
