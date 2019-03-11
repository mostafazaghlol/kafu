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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.eltamiuzcom.kafu.Activity.MainActivity;
import com.eltamiuzcom.kafu.Adapters.MajadatAdapter;
import com.eltamiuzcom.kafu.Model.montage.Montage;
import com.eltamiuzcom.kafu.Model.myacutions.Bidder;
import com.eltamiuzcom.kafu.Model.myacutions.Myauction;
import com.eltamiuzcom.kafu.R;
import com.eltamiuzcom.kafu.Utils.contants;
import com.eltamiuzcom.kafu.Volley.makeauction;
import com.eltamiuzcom.kafu.Volley.makeorder;
import com.eltamiuzcom.kafu.Volley.showaudation;
import com.eltamiuzcom.kafu.Volley.showitem;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link oneAuctionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class oneAuctionFragment extends Fragment {
    Double maxprize=0.0;
    Double currentprize;
    private ProgressDialog progressDialog;
    private ArrayList<Bidder> mBidders;
    public static String auctiontype;
    private String role;

    @OnClick(R.id.acutionorderback)
    public void back(){
        startActivity(new Intent(getContext(),MainActivity.class));
        getActivity().finish();
    }
    @BindView(R.id.ReLasetMujadeen)
    RecyclerView ReLast;
    @BindView(R.id.acutionhname) TextView TxtTitle;
    @BindView(R.id.Txtacutionfrom) TextView TxtFrom;
    @BindView(R.id.Txtauctionto) TextView TxtTo;
    @BindView(R.id.acutionphoto) ImageView imageView;
    @BindView(R.id.Txtacutiondate) TextView Txtmontagedate;
    @BindView(R.id.TxtacutionDes)
    HtmlTextView Txtmontagedesc;
    @BindView(R.id.TxtacutionLanbanatypex) TextView Txtmontagelabanatype;
    @BindView(R.id.TxtacutionLength) TextView TxtmontageLength;
    @BindView(R.id.TxtacutionTypexxx) TextView TxtmontageType;
    @BindView(R.id.TxtacutionType) TextView TxtmontageType2;
    @BindView(R.id.Txtacutionweight) TextView TxtWeight;
    @BindView(R.id.Txtacutionbuyername) TextView TxtBuyerName;
    @BindView(R.id.Txtacutionprice) TextView TxtmontagePrice;
    @BindView(R.id.Edmajadtime) EditText EdTime;
    @BindView(R.id.Edenterprice) EditText EdPrice;
    @BindView(R.id.auction)
    LinearLayout linearLayout;
    @BindView(R.id.mandoopcc)
    RelativeLayout linearLayoutm;
    @BindView(R.id.normal)
    LinearLayout linearLayoutn;
    @BindView(R.id.nono)
    FrameLayout frameLayout;
    @OnClick(R.id.Btentermajad2)
    public void gomajad(){
        if(init()){
            progressDialog.show();
            makeauction mMakeauction = new makeauction(new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Boolean message = jsonObject.getBoolean("message");
                        if(message){
                            addfackdata();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "حدث خطأ", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },id,auctiontype,orderid,price);
            RequestQueue queue = Volley.newRequestQueue(getContext());
            queue.add(mMakeauction);
        }
    }
    @BindView(R.id.Ednormalprice) EditText EdPric2e;
    @OnClick(R.id.Btordranyentermajad)
    public void gomajadnormal(){
        if(!EdPric2e.getText().toString().isEmpty()){
            progressDialog.show();
            makeauction mMakeauction = new makeauction(new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Boolean message = jsonObject.getBoolean("message");
                        if(message){
                            addfackdata();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "حدث خطأ", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },id,auctiontype,orderid,EdPric2e.getText().toString());
            RequestQueue queue = Volley.newRequestQueue(getContext());
            queue.add(mMakeauction);
        }
    }
    String price,time;
    MajadatAdapter majadatAdapter;
    private boolean init() {
        if(EdPrice.getText().toString().isEmpty()){
            EdPrice.setError(getString(R.string.Editerror));
            return false;
        }
        if(EdTime.getText().toString().isEmpty()){
            EdTime.setError(getString(R.string.Editerror));
            return false;
        }
        price = EdPrice.getText().toString();
        time = EdTime.getText().toString();
        return true;
    }

    //    @OnClick(R.id.Btorder)
//    public void order(){
//        progressDialog.show();
//        makeorder mMakeOrder = new makeorder(new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    Boolean message = jsonObject.getBoolean("message");
//                    if(message){
//                        Toast.makeText(getContext(), "تم الطلب بنجاح", Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        },m.getData().getId().toString(),id,"0",m.getData().getPrice().toString());
//        RequestQueue queue = Volley.newRequestQueue(getActivity());
//        queue.add(mMakeOrder);
//    }
    public static String orderid;
    public SharedPreferences mSharedPreferences;
    public String id;
    public oneAuctionFragment() {
        // Required empty public constructor
    }


    public static oneAuctionFragment newInstance(String orderid,String auction_id) {
        oneAuctionFragment.orderid = orderid;
        oneAuctionFragment.auctiontype = auction_id;
        return new oneAuctionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @BindView(R.id.vvvv) View  v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.singleauctionfragment, container, false);
        ButterKnife.bind(this, view);
        progressDialog = new ProgressDialog(getContext());
        mSharedPreferences = getActivity().getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        id = String.valueOf(mSharedPreferences.getInt(contants.id,0));
        role = mSharedPreferences.getString(contants.role,"0");
        if(role.equals("2")){
            frameLayout.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);
            v.setVisibility(View.GONE);
        }
        if(auctiontype.equals("1")){
            linearLayout.setVisibility(View.INVISIBLE);
            linearLayoutm.setVisibility(View.GONE);
            linearLayoutn.setVisibility(View.VISIBLE);
            v.setVisibility(View.GONE);
        }else{
            linearLayout.setVisibility(View.VISIBLE);
            linearLayoutm.setVisibility(View.VISIBLE);
            linearLayoutn.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBidders = new ArrayList<>();
        majadatAdapter = new MajadatAdapter(getContext(), mBidders);
        ReLast.setAdapter(majadatAdapter);
        LinearLayoutManager HlayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        ReLast.setLayoutManager(HlayoutManager);
        addfackdata();
    }

    private void addfackdata() {
        mBidders.clear();
        progressDialog.setIcon(R.drawable.logo);
        progressDialog.setTitle(getString(R.string.app_name));
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();
        showaudation mShowitem = new showaudation(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean state = jsonObject.getBoolean("message");
                    if(state){
                        Myauction montage = new Gson().fromJson(response,Myauction.class);
                        mBidders.addAll(montage.getData().getBidders());
                        majadatAdapter.notifyDataSetChanged();
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
    @BindView(R.id.cxcxc) TextView TxtNoMajad;
    public Myauction m;
    private void updateUI(Myauction montage) {
        m = montage;
        progressDialog.dismiss();
        if(montage.getData().getImages().size() > 0) {
            Picasso.get().load(contants.url + montage.getData().getImages().get(0).getImage()).into(imageView);
        }
        TxtBuyerName.setText(getBest(m));
        TxtmontagePrice.setText(maxprize.toString()+"ريال");
        Txtmontagedate.setText(montage.getData().getCreatedAt());
        Txtmontagedesc.setHtml(montage.getData().getItemDesc());
        Txtmontagelabanatype.setText(montage.getData().getLabanatype());
        TxtmontageType.setText(montage.getData().getCategory());
        TxtmontageType2.setText(montage.getData().getItemtype());
        TxtmontageLength.setText(montage.getData().getItemlang());
        TxtWeight.setText(montage.getData().getItemweight());
        TxtTitle.setText(montage.getData().getTitle());
        if(mBidders.size() == 0){
          TxtNoMajad.setVisibility(View.GONE);
        }
    }

    private String getBest(Myauction m) {
        String username = "لا يوجد مزايدين حتى الان" ;

        if(m.getData().getBidders().size()>0)
            maxprize = Double.parseDouble(m.getData().getBidders().get(0).getPrice());

        for(int i =0;i<m.getData().getBidders().size();i++){
            currentprize = Double.parseDouble(m.getData().getBidders().get(i).getPrice());
            username = m.getData().getBidders().get(i).getUserName();
            if(currentprize>maxprize){
                maxprize=currentprize;
                username = m.getData().getBidders().get(i).getUserName();
            }
        }
        return username;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


}
