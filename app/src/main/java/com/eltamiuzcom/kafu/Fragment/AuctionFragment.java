package com.eltamiuzcom.kafu.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.eltamiuzcom.kafu.Activity.SplachActivity;
import com.eltamiuzcom.kafu.Adapters.auctionsAdapter;
import com.eltamiuzcom.kafu.Adapters.catsAdapter;
import com.eltamiuzcom.kafu.Adapters.ordersAdapter;
import com.eltamiuzcom.kafu.Model.auctionmodel;
import com.eltamiuzcom.kafu.Model.getsettings.Cat;

import com.eltamiuzcom.kafu.Model.myauctions2.Datum;
import com.eltamiuzcom.kafu.Model.myauctions2.MyAuctions;
import com.eltamiuzcom.kafu.R;
import com.eltamiuzcom.kafu.Utils.contants;
import com.eltamiuzcom.kafu.Volley.allauctions;
import com.eltamiuzcom.kafu.Volley.allitems;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AuctionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AuctionFragment extends Fragment {
    @BindView(R.id.emauction)
    TextView Txtempty;
    @BindView(R.id.gridview)
    GridView gridView;
    List<Datum> montagat,Bestmontagat;
    List<Cat> cats;
    private auctionsAdapter auctionsAdapter;
    ProgressDialog progressDialog;
    String role;
    SharedPreferences mSharedPreferences;
    private String auctiontype;

    public AuctionFragment() {
        // Required empty public constructor
    }

    public static AuctionFragment newInstance() {
        return new AuctionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.auctionsfragment, container, false);
        ButterKnife.bind(this, view);
        mSharedPreferences = getActivity().getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        montagat = new ArrayList<>();
        Bestmontagat = new ArrayList<>();
        cats = new ArrayList<>();
        progressDialog = new ProgressDialog(getContext());
        role = mSharedPreferences.getString(contants.role,"");
        if(role.equals("1") || role.equals("2")){
            auctiontype = "1";
        }else{
            auctiontype = "2";
        }
        return view;
    }



    ArrayList<Datum> auctionmodels;
    auctionsAdapter booksAdapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auctionmodels = new ArrayList<>();
        auctionsAdapter = new auctionsAdapter(getContext(),auctionmodels);
        gridView.setAdapter(auctionsAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Datum datum = auctionmodels.get(position);
                startFragmetn(datum.getId());
            }
        });
        addfackdata();
    }

    private void startFragmetn(Integer id) {
        oneAuctionFragment nextFrag= oneAuctionFragment.newInstance(id.toString(),auctiontype);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }

    private void addfackdata() {
        cats.addAll(SplachActivity.mGetinfo.getData().getCats());
        progressDialog.setTitle(getString(R.string.app_name));
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setIcon(R.drawable.logo);
        progressDialog.show();
//        booksAdapter.notifyDataSetChanged();
        allauctions allitems = new allauctions(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean message = jsonObject.getBoolean("message");
                    if(message){
                        progressDialog.dismiss();
                        MyAuctions montagatObject = new Gson().fromJson(response,MyAuctions.class);
                        auctionmodels.addAll(montagatObject.getData());
                        auctionsAdapter.notifyDataSetChanged();
                    }else{
                        Txtempty.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        },auctiontype);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(allitems);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


}
