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
import com.eltamiuzcom.kafu.Adapters.BestordersAdapter;
import com.eltamiuzcom.kafu.Adapters.catsAdapter;
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
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    @BindView(R.id.RecyclerAqsam)
    RecyclerView recyclerViewCats;
    @BindView(R.id.RecyclerBestAdds)
    RecyclerView recyclerViewOffers;
    @BindView(R.id.RecyclerRecentAdds)
    RecyclerView recyclerViewRecentadds;
    @BindView(R.id.TxtallBest)
    TextView textViewallbest;
    @BindView(R.id.TxtallRecent)
    TextView textViewallrecent;
    @BindView(R.id.emptybestoffers)
    TextView Txtempty;
    @BindView(R.id.emptyoffers)
    TextView Txtempty2;
    List<Datum> montagat,Bestmontagat;
    List<Cat> cats;
    private ordersAdapter AllAdapter;
    private BestordersAdapter BestAdapter;
    private catsAdapter mcatsAdapter;
    ProgressDialog progressDialog;
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.homefragment, container, false);
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
        LinearLayoutManager HlayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        LinearLayoutManager HlayoutManager2
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        LinearLayoutManager HlayoutManager3
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        AllAdapter = new ordersAdapter(getContext(), montagat, (view12, position) -> {
            orderFragment nextFrag= orderFragment.newInstance(montagat.get(position).getId().toString());
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
        });
        BestAdapter = new BestordersAdapter(getContext(), Bestmontagat, (view1, position) -> {
            orderFragment nextFrag= orderFragment.newInstance(Bestmontagat.get(position).getId().toString());
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
        });
        mcatsAdapter = new catsAdapter(getContext(), cats, new catsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Cat cat = cats.get(position);
                progressDialog.show();
                addfackdata(cat.getId().toString());
            }
        });
        cats.addAll(SplachActivity.mGetinfo.getData().getCats());
        mcatsAdapter.notifyDataSetChanged();
        recyclerViewOffers.setAdapter(BestAdapter);
        recyclerViewRecentadds.setAdapter(AllAdapter);
        recyclerViewCats.setAdapter(mcatsAdapter);
        recyclerViewOffers.setLayoutManager(HlayoutManager);
        recyclerViewCats.setLayoutManager(HlayoutManager2);
        recyclerViewRecentadds.setLayoutManager(HlayoutManager3);
        addfackdata("0");
    }

    private void addfackdata(String category) {
        montagat.clear();
        Bestmontagat.clear();
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
                        for(int i=0;i<montagatObject.getData().size();i++){
                            if(montagatObject.getData().get(i).getSpecial().equals(1)){
                                Bestmontagat.add(montagatObject.getData().get(i));
                            }
                        }
                        AllAdapter.notifyDataSetChanged();
                        BestAdapter.notifyDataSetChanged();

                        Txtempty.setVisibility(View.INVISIBLE);
                        Txtempty2.setVisibility(View.INVISIBLE);
                        if(Bestmontagat.size() ==  0){
                            Txtempty.setVisibility(View.INVISIBLE);
                        }
                    }else{
                         progressDialog.dismiss();
                        AllAdapter.notifyDataSetChanged();
                        BestAdapter.notifyDataSetChanged();
                        Txtempty.setVisibility(View.VISIBLE);
                        Txtempty2.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        },category);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(allitems);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


}
