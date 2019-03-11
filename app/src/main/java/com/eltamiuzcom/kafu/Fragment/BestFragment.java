package com.eltamiuzcom.kafu.Fragment;

import android.app.ProgressDialog;
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
import com.eltamiuzcom.kafu.Activity.SplachActivity;
import com.eltamiuzcom.kafu.Adapters.catsAdapter;
import com.eltamiuzcom.kafu.Adapters.offersAdapter;
import com.eltamiuzcom.kafu.Adapters.ordersAdapter;
import com.eltamiuzcom.kafu.Model.getsettings.Cat;
import com.eltamiuzcom.kafu.Model.montagat.Datum;
import com.eltamiuzcom.kafu.Model.montagat.Montagat;
import com.eltamiuzcom.kafu.R;
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
 * Use the {@link BestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BestFragment extends Fragment {
    @BindView(R.id.RecyclerAqsam2)
    RecyclerView recyclerViewCats;
    @BindView(R.id.RecyclerOffer2)
    RecyclerView recyclerViewOffers;
    List<Datum> montagat,Bestmontagat;
    List<Cat> cats;
    private offersAdapter AllAdapter;
    private catsAdapter mcatsAdapter;
    ProgressDialog progressDialog;
    @BindView(R.id.emptybestoffersz) TextView txy;
    public BestFragment() {
        // Required empty public constructor
    }

    public static BestFragment newInstance() {
        return new BestFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bestfragment, container, false);
        ButterKnife.bind(this, view);
        montagat = new ArrayList<>();
        Bestmontagat = new ArrayList<>();
        cats = new ArrayList<>();
        progressDialog = new ProgressDialog(getContext());
        return view;
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager VlayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager HlayoutManager2
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);

        AllAdapter = new offersAdapter(getContext(), montagat, new offersAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                orderFragment nextFrag= orderFragment.newInstance(montagat.get(position).getId().toString());
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        mcatsAdapter = new catsAdapter(getContext(), cats, new catsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Cat cat = cats.get(position);
                progressDialog.show();
                addfackdata(cat.getId().toString());
            }
        });
        recyclerViewOffers.setAdapter(AllAdapter);
        recyclerViewCats.setAdapter(mcatsAdapter);
        recyclerViewOffers.setLayoutManager(VlayoutManager);
        recyclerViewCats.setLayoutManager(HlayoutManager2);
        cats.addAll(SplachActivity.mGetinfo.getData().getCats());
        mcatsAdapter.notifyDataSetChanged();
        addfackdata("0");
    }

    private void addfackdata(String cat) {
        montagat.clear();
        progressDialog.setTitle(getString(R.string.app_name));
        progressDialog.setTitle(getString(R.string.loading));
        progressDialog.setIcon(R.drawable.logo);
        progressDialog.show();
        allitems allitems = new allitems(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean message = jsonObject.getBoolean("message");
                    if(message){
                        progressDialog.dismiss();
                        Montagat montagatObject = new Gson().fromJson(response,Montagat.class);
                        montagat.addAll(montagatObject.getData());
                        AllAdapter.notifyDataSetChanged();
                        txy.setVisibility(View.INVISIBLE);
                    }else{
                        progressDialog.dismiss();
                        AllAdapter.notifyDataSetChanged();
                        txy.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        },cat);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(allitems);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


}
