package com.eltamiuzcom.kafu.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.eltamiuzcom.kafu.Adapters.auctionsAdapter;
import com.eltamiuzcom.kafu.Adapters.auctionsAdapter2;
import com.eltamiuzcom.kafu.Model.AccountAcutions.AccountAuctions;
import com.eltamiuzcom.kafu.Model.getsettings.Cat;
import com.eltamiuzcom.kafu.Model.montagat.Montagat;
import com.eltamiuzcom.kafu.Model.myauctions2.Datum;
import com.eltamiuzcom.kafu.Model.myauctions2.MyAuctions;
import com.eltamiuzcom.kafu.R;
import com.eltamiuzcom.kafu.Utils.contants;
import com.eltamiuzcom.kafu.Volley.allauctions;
import com.eltamiuzcom.kafu.Volley.allitems;
import com.eltamiuzcom.kafu.Volley.myauctions;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAuctionsActivity extends AppCompatActivity {
    @BindView(R.id.emauction1)
    TextView Txtempty;
    @BindView(R.id.gridview1)
    GridView gridView;
    List<Datum> montagat,Bestmontagat;
    List<Cat> cats;
    ArrayList<com.eltamiuzcom.kafu.Model.AccountAcutions.Datum> auctionmodels;
    auctionsAdapter2 booksAdapter;
    private String auctiontype,role;
    SharedPreferences mSharedPreferences;
    private String id;

    @OnClick(R.id.myauctionback)
    public void back(){
        onBackPressed();
    }

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_auctions);
        ButterKnife.bind(this);
        montagat = new ArrayList<>();
        Bestmontagat = new ArrayList<>();
        cats = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        auctionmodels = new ArrayList<>();
        booksAdapter = new auctionsAdapter2(this,auctionmodels);
        gridView.setAdapter(booksAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                com.eltamiuzcom.kafu.Model.AccountAcutions.Datum datum = auctionmodels.get(position);
                startFragmetn(datum.getAuctionId());
            }
        });
        mSharedPreferences = getSharedPreferences(contants.pref_account,MODE_PRIVATE);
        id = String.valueOf(mSharedPreferences.getInt(contants.id,0));
        role = mSharedPreferences.getString(contants.role,"1");
        if(role.equals("1")){
            auctiontype = "1";
        }else{
            auctiontype = "2";
        }
        addfackdata();
    }

    private void startFragmetn(Integer auctionId) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("auctionId",auctionId.toString());
        startActivity(intent);
    }

    private void addfackdata() {
        cats.addAll(SplachActivity.mGetinfo.getData().getCats());
//        mcatsAdapter.notifyDataSetChanged();
        progressDialog.setTitle(getString(R.string.app_name));
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setIcon(R.drawable.logo);
        progressDialog.show();
//        for(int i=0;i<20;i++){
//            auctionmodels.add(new auctionmodel(
//                    "محصول حديث الانتاج",
//                    "سينتهى فى 20:18",
//                    "يبدا ب 200 ريال",R.drawable.fackphoto));
//        }
        booksAdapter.notifyDataSetChanged();
        progressDialog.dismiss();
        myauctions allitems = new myauctions(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean message = jsonObject.getBoolean("message");
                    if(message){
                        progressDialog.dismiss();
                        AccountAuctions montagatObject = new Gson().fromJson(response,AccountAuctions.class);
                        auctionmodels.addAll(montagatObject.getData());
                        booksAdapter.notifyDataSetChanged();
//                        llAdapter.notifyDataSetChanged();
//                        BestAdapter.notifyDataSetChanged();
                    }else{
                        Txtempty.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        },id);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(allitems);
    }
}
