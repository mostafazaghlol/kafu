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
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.eltamiuzcom.kafu.Adapters.NotificationsAdapter;
import com.eltamiuzcom.kafu.Adapters.catsAdapter;
import com.eltamiuzcom.kafu.Adapters.offersAdapter;
import com.eltamiuzcom.kafu.Model.getsettings.Cat;
import com.eltamiuzcom.kafu.Model.montagat.Datum;
import com.eltamiuzcom.kafu.Model.mynotifications.Mynotifications;
import com.eltamiuzcom.kafu.R;
import com.eltamiuzcom.kafu.Utils.contants;
import com.eltamiuzcom.kafu.Volley.mynotifications;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link NotificationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationsFragment extends Fragment {
    @BindView(R.id.Renotification)
    RecyclerView recyclerViewOffers;
    @BindView(R.id.empty)
    TextView Txt;
    ProgressDialog progressDialog;
    private ArrayList<String> notifications;
    private NotificationsAdapter notificationsAdapter;
    private String id;
    SharedPreferences mSharedPreferences;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.notificationfragment, container, false);
        ButterKnife.bind(this, view);
        notifications = new ArrayList<>();
        progressDialog = new ProgressDialog(getContext());
        mSharedPreferences= getActivity().getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);
        id = String.valueOf(mSharedPreferences.getInt(contants.id,0));
        return view;
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager VlayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager HlayoutManager2
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        notificationsAdapter = new NotificationsAdapter(getContext(),notifications);
        recyclerViewOffers.setAdapter(notificationsAdapter);
        recyclerViewOffers.setLayoutManager(VlayoutManager);
        addfackdata();
    }

    private void addfackdata() {
        progressDialog.setIcon(R.drawable.logo);
        progressDialog.setTitle(getString(R.string.app_name));
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();
        mynotifications mynotifications = new mynotifications(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("message");
                    if (success) {
                        progressDialog.dismiss();
                        Mynotifications myorders1 = new Gson().fromJson(response, Mynotifications.class);
                        for (com.eltamiuzcom.kafu.Model.mynotifications.Datum in : myorders1.getData()) {
                            notifications.add(in.getNotification());
                        }
                        notificationsAdapter.notifyDataSetChanged();
                        //swipeRefreshLayout.setRefreshing(false);
                    } else {
                       // swipeRefreshLayout.setRefreshing(false);
                        progressDialog.dismiss();
                        recyclerViewOffers.setVisibility(View.INVISIBLE);
                        Txt.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    //swipeRefreshLayout.setRefreshing(false);
                    progressDialog.dismiss();
                    recyclerViewOffers.setVisibility(View.INVISIBLE);
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
