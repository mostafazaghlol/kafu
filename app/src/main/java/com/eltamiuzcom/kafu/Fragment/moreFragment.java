package com.eltamiuzcom.kafu.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eltamiuzcom.kafu.Activity.LoginActivity;
import com.eltamiuzcom.kafu.Activity.MyAuctionsActivity;
import com.eltamiuzcom.kafu.Adapters.MoresAdapter;
import com.eltamiuzcom.kafu.Model.more;
import com.eltamiuzcom.kafu.R;
import com.eltamiuzcom.kafu.Utils.contants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link moreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class moreFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<more> mores;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;
    String username,userimage,userphone;
    private String role;

    public moreFragment() {
        // Required empty public constructor
    }

    public static moreFragment newInstance() {
        moreFragment fragment = new moreFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerMore);
        ButterKnife.bind(this,view);
        mores = new ArrayList<>();
        mSharedPreferences = getActivity().getSharedPreferences(contants.pref_account,Context.MODE_PRIVATE);

//        username = mSharedPreferences.getString(contants.username,"");
//        userimage = mSharedPreferences.getString(contants.image,"");
//        userphone = mSharedPreferences.getString(contants.phone,"");
//        Txt.setText(username);
//        TxtPhone.setText(userphone);
//        Picasso.get().load(contants.url+userimage).placeholder(R.drawable.logo).into(imageView);
//        Typeface font = Typeface.createFromAsset(Txt.getContext().getAssets(), "cairo.ttf");
//        Txt.setTypeface(font);
        editor = mSharedPreferences.edit();
        role = mSharedPreferences.getString(contants.role,"0");
        preparedata();
        return view;
    }



    private void preparedata() {
        if(role.equals("2")){
              mores.add(new more(getContext().getResources().getString(R.string.account),R.drawable.user_profile));
              mores.add(new more(getContext().getResources().getString(R.string.nesadd),R.drawable.addicon));
              mores.add(new more(getContext().getResources().getString(R.string.orders),R.drawable.shopping_bag));
              mores.add(new more(getContext().getResources().getString(R.string.majadaty),R.drawable.group1176));
              mores.add(new more(getContext().getResources().getString(R.string.fawteer),R.drawable.padnote));
              mores.add(new more(getContext().getResources().getString(R.string.notifications),R.drawable.group1177));
              mores.add(new more(getContext().getResources().getString(R.string.aboutapp),R.drawable.order));
              mores.add(new more(getContext().getResources().getString(R.string.shroot),R.drawable.path));
              mores.add(new more(getContext().getResources().getString(R.string.settings),R.drawable.settings));
              mores.add(new more(getContext().getResources().getString(R.string.logout),R.drawable.exit));
        }else{
            mores.add(new more(getContext().getResources().getString(R.string.account),R.drawable.user_profile));
            mores.add(new more(getContext().getResources().getString(R.string.orders),R.drawable.shopping_bag));
            mores.add(new more(getContext().getResources().getString(R.string.majadaty),R.drawable.group1176));
            mores.add(new more(getContext().getResources().getString(R.string.fawteer),R.drawable.padnote));
            mores.add(new more(getContext().getResources().getString(R.string.notifications),R.drawable.group1177));
            mores.add(new more(getContext().getResources().getString(R.string.aboutapp),R.drawable.order));
            mores.add(new more(getContext().getResources().getString(R.string.shroot),R.drawable.path));
            mores.add(new more(getContext().getResources().getString(R.string.settings),R.drawable.settings));
            mores.add(new more(getContext().getResources().getString(R.string.logout),R.drawable.exit));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager VlayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        MoresAdapter moresAdapter = new MoresAdapter(getContext(), mores, (view1, position) -> {
                    if(role.equals("2")){
                        if(position == 0){
                            getActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.container, AccountFragment.newInstance())
                                    .commitNow();
                        }else if(position == 1){
                            getActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.container, AddNewOfferFragment.newInstance())
                                    .commitNow();
                        }else if(position == 2){
                            getActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.container, MyOrdersFragment.newInstance())
                                    .commitNow();
                        }else if(position == 3){
//                            getActivity()
//                                    .getSupportFragmentManager()
//                                    .beginTransaction()
//                                    .replace(R.id.container, AuctionFragment.newInstance())
//                                    .commitNow();
                            startActivity(new Intent(getContext(),MyAuctionsActivity.class));
                        }else if(position == 4){
                            getActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.container, FawteerFragment.newInstance())
                                    .commitNow();
                        }else if(position == 5){
                            getActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.container, NotificationsFragment.newInstance())
                                    .commitNow();
                        }else if(position == 6){
                            getActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.container, AboutFragment.newInstance())
                                    .commitNow();
                        }else if(position == 9){
                        AlertDialog.Builder builder;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Light_Dialog_Alert);
                            } else {
                                builder = new AlertDialog.Builder(getContext());
                            }
                            builder.setTitle("تريد الخروج")
                                    .setMessage("هل تريد الخروج ؟")
                                    .setIcon(R.drawable.logo)
                                    .setPositiveButton("نعم", (dialog, which) -> {

                                        editor.clear();
                                        editor.commit();
                                        getActivity().finish();
                                    })
                                    .setNegativeButton("لا", (dialog, which) -> {
                                        dialog.dismiss();
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }else if(position == 7){
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, shrootFragment.newInstance(), "findThisFragment")
                                    .addToBackStack(null)
                                    .commit();
                        }else if(position == 8){
//                       getActivity().getSupportFragmentManager().beginTransaction()
//                               .replace(R.id.container, CatResultsFragment.newInstance(true), "findThisFragment")
//                               .addToBackStack(null)
//                               .commit();
                        }else if(position == 10){
//                       Intent intent = new Intent(getActivity(),AboutDeveloper.class);
//                       intent.putExtra("color","#6c3f96");
//                       startActivity(intent);
                        }else if(position == 11){

                        }

                    }else{
                        if(position == 0){
                            getActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.container, AccountFragment.newInstance())
                                    .commitNow();
                        }else if(position == 1){
                            getActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.container, MyOrdersFragment.newInstance())
                                    .commitNow();
                        }else if(position == 2){
                            startActivity(new Intent(getContext(),MyAuctionsActivity.class));
//                            getActivity()
//                                    .getSupportFragmentManager()
//                                    .beginTransaction()
//                                    .replace(R.id.container, AuctionFragment.newInstance())
//                                    .commitNow();
                        }else if(position == 3){
                            getActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.container, FawteerFragment.newInstance())
                                    .commitNow();
                        }else if(position == 4){
                            getActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.container, NotificationsFragment.newInstance())
                                    .commitNow();
                        }else if(position == 5){
                            getActivity()
                                    .getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.container, AboutFragment.newInstance())
                                    .commitNow();
                        }else if(position == 8){
                       AlertDialog.Builder builder;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Light_Dialog_Alert);
                            } else {
                                builder = new AlertDialog.Builder(getContext());
                            }
                            builder.setTitle("تريد الخروج")
                                    .setMessage("هل تريد الخروج ؟")
                                    .setIcon(R.drawable.logo)
                                    .setPositiveButton("نعم", (dialog, which) -> {
//
                                        editor.clear();
                                        editor.commit();
                                        getActivity().finish();
                                    })
                                    .setNegativeButton("لا", (dialog, which) -> {
                                        dialog.dismiss();
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }else if(position == 6){
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, shrootFragment.newInstance(), "findThisFragment")
                                    .addToBackStack(null)
                                    .commit();
                        }else if(position == 7){
//                       getActivity().getSupportFragmentManager().beginTransaction()
//                               .replace(R.id.container, CatResultsFragment.newInstance(true), "findThisFragment")
//                               .addToBackStack(null)
//                               .commit();
                        }else if(position == 9){
//                       Intent intent = new Intent(getActivity(),AboutDeveloper.class);
//                       intent.putExtra("color","#6c3f96");
//                       startActivity(intent);
                        }else if(position == 10){
                            AlertDialog.Builder builder;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Light_Dialog_Alert);
                            } else {
                                builder = new AlertDialog.Builder(getContext());
                            }
                            builder.setTitle("تريد الخروج")
                                    .setMessage("هل تريد الخروج ؟")
                                    .setIcon(R.drawable.logo)
                                    .setPositiveButton("نعم", (dialog, which) -> {
//                                   // continue with delete
//                                   OneSignal.setSubscription(false);
//                                   String userid = String.valueOf(mSharedPreferences.getInt(contants.id,0));
//                                   FirebaseDatabase.getInstance().getReference().child("user").child(userid).child("notifictionID").setValue("0");
                                        editor.clear();
                                        editor.commit();
                                        getActivity().finish();
                                    })
                                    .setNegativeButton("لا", (dialog, which) -> {
                                        dialog.dismiss();
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();

                        }

                    }

        });
        recyclerView.setAdapter(moresAdapter);
        recyclerView.setLayoutManager(VlayoutManager);

    }
}
