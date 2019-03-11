package com.eltamiuzcom.kafu.Fragment;

import android.app.ActionBar;
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
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.eltamiuzcom.kafu.Activity.MainActivity;
import com.eltamiuzcom.kafu.Adapters.catsAdapter;
import com.eltamiuzcom.kafu.Adapters.fawteersAdapter;
import com.eltamiuzcom.kafu.Adapters.offersAdapter;
import com.eltamiuzcom.kafu.Model.Fawteer.Datum;
import com.eltamiuzcom.kafu.Model.Fawteer.Fawteer;
import com.eltamiuzcom.kafu.Model.mynotifications.Mynotifications;
import com.eltamiuzcom.kafu.R;
import com.eltamiuzcom.kafu.Utils.contants;
import com.eltamiuzcom.kafu.Volley.myfawteer;
import com.eltamiuzcom.kafu.Volley.mynotifications;
import com.google.gson.Gson;

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
 * Use the {@link FawteerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FawteerFragment extends Fragment {
    @BindView(R.id.ReFawteer)
    RecyclerView Recycler;
    List<Datum> fawteers;
    fawteersAdapter mFawteersAdapter;
    private ProgressDialog progressDialog;
    @BindView(R.id.emptyfawteer)
    TextView Txt;
    @OnClick(R.id.fatweerback)
    public void back(){
        startActivity(new Intent(getContext(),MainActivity.class));
        getActivity().finish();
    }
    SharedPreferences mSharedPreferences;
    public FawteerFragment() {
        // Required empty public constructor
    }
    private String id;
    public static FawteerFragment newInstance() {
        return new FawteerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fawteerfragment, container, false);
        ButterKnife.bind(this, view);
        progressDialog = new ProgressDialog(getContext());
        fawteers = new ArrayList<>();
        mFawteersAdapter = new fawteersAdapter(getContext(),fawteers);
        mSharedPreferences = getActivity().getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        id = String.valueOf(mSharedPreferences.getInt(contants.id,0));
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager VlayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);

        Recycler.setAdapter(mFawteersAdapter);
        Recycler.setLayoutManager(VlayoutManager);
        addfackdata();
    }

    private void addfackdata() {
        progressDialog.setIcon(R.drawable.logo);
        progressDialog.setTitle(getString(R.string.app_name));
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();
        myfawteer mynotifications = new myfawteer(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("message");
                    if (success) {
                        progressDialog.dismiss();
                        Fawteer myorders1 = new Gson().fromJson(response, Fawteer.class);
                        fawteers.addAll(myorders1.getData());
                        mFawteersAdapter.notifyDataSetChanged();
                        //swipeRefreshLayout.setRefreshing(false);
                    } else {
                        // swipeRefreshLayout.setRefreshing(false);
                        progressDialog.dismiss();
                        Recycler.setVisibility(View.INVISIBLE);
                        Txt.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    //swipeRefreshLayout.setRefreshing(false);
                    progressDialog.dismiss();
                    Recycler.setVisibility(View.INVISIBLE);
                    Txt.setVisibility(View.VISIBLE);
                }
            }
        },id);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(mynotifications);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


}
