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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.eltamiuzcom.kafu.Activity.MainActivity;
import com.eltamiuzcom.kafu.Activity.SplachActivity;
import com.eltamiuzcom.kafu.Adapters.catsAdapter;
import com.eltamiuzcom.kafu.Adapters.myordersAdapter;
import com.eltamiuzcom.kafu.Adapters.offersAdapter;
import com.eltamiuzcom.kafu.Model.getsettings.Cat;
import com.eltamiuzcom.kafu.Model.montagat.Datum;
import com.eltamiuzcom.kafu.Model.montagat.Montagat;
import com.eltamiuzcom.kafu.Model.myorders.Myorders;
import com.eltamiuzcom.kafu.R;
import com.eltamiuzcom.kafu.Utils.contants;
import com.eltamiuzcom.kafu.Volley.allitems;
import com.eltamiuzcom.kafu.Volley.myorder;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MyOrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyOrdersFragment extends Fragment {
    @BindView(R.id.RecyclerOffer3)
    RecyclerView recyclerViewOffers;
    List<com.eltamiuzcom.kafu.Model.myorders.Datum> montagat,Bestmontagat;
    List<Cat> cats;
    private myordersAdapter AllAdapter;
    private catsAdapter mcatsAdapter;
    ProgressDialog progressDialog;
    private String userid,userrole,status;
    SharedPreferences mSharedPreferences;
    public MyOrdersFragment() {
        // Required empty public constructor
    }

    @OnClick(R.id.myordersback)
    public void goback(){
        startActivity(new Intent(getContext(),MainActivity.class));
        getActivity().finish();
    }
    public static MyOrdersFragment newInstance() {
        return new MyOrdersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.myorderfragment, container, false);
        ButterKnife.bind(this, view);
        montagat = new ArrayList<>();
        Bestmontagat = new ArrayList<>();
        cats = new ArrayList<>();
        progressDialog = new ProgressDialog(getContext());
        mSharedPreferences = getActivity().getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        userid = String.valueOf(mSharedPreferences.getInt(contants.id,0));
        userrole = String.valueOf(mSharedPreferences.getString(contants.role,"0"));
        return view;
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager VlayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager HlayoutManager2
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);

        AllAdapter = new myordersAdapter(getContext(), montagat, new myordersAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                orderFragment nextFrag= orderFragment.newInstance(montagat.get(position).getItemId().toString());
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        mcatsAdapter = new catsAdapter(getContext(),cats);
        recyclerViewOffers.setAdapter(AllAdapter);
        //recyclerViewCats.setAdapter(mcatsAdapter);
        recyclerViewOffers.setLayoutManager(VlayoutManager);
        //recyclerViewCats.setLayoutManager(HlayoutManager2);
        addfackdata();
    }

    private void addfackdata() {
        cats.addAll(SplachActivity.mGetinfo.getData().getCats());
        mcatsAdapter.notifyDataSetChanged();
        progressDialog.setTitle(getString(R.string.app_name));
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setIcon(R.drawable.logo);
        progressDialog.show();
        myorder allitems = new myorder(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean message = jsonObject.getBoolean("message");
                    if(message){
                        progressDialog.dismiss();
                        Myorders montagatObject = new Gson().fromJson(response,Myorders.class);
                        montagat.addAll(montagatObject.getData());
                        AllAdapter.notifyDataSetChanged();
                    }else{
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        },userid,userrole,status);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(allitems);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


}
