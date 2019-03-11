package com.eltamiuzcom.kafu.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.eltamiuzcom.kafu.Fragment.AuctionFragment;
import com.eltamiuzcom.kafu.Fragment.BestFragment;
import com.eltamiuzcom.kafu.Fragment.HomeFragment;
import com.eltamiuzcom.kafu.Fragment.moreFragment;
import com.eltamiuzcom.kafu.Fragment.oneAuctionFragment;
import com.eltamiuzcom.kafu.R;
import com.eltamiuzcom.kafu.Utils.contants;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView navigation;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor editor;
    private String auctiontype,role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = getSharedPreferences(contants.pref_account,MODE_PRIVATE);
        role = mSharedPreferences.getString(contants.role,"0");
        if(role.equals("1") || role.equals("2")){
            auctiontype = "1";
        }else{
            auctiontype = "2";
        }
        if(getIntent().hasExtra("auctionId")){
            oneAuctionFragment nextFrag= oneAuctionFragment.newInstance(getIntent().getStringExtra("auctionId"),auctiontype);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, nextFrag, "findThisFragment")
                    .addToBackStack(null)
                    .commit();
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, HomeFragment.newInstance())
                    .commitNow();
        }
        navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.navigation_home){
            startActivity(new Intent(this,MainActivity.class));
            finish();
            return true;
        }else if(id == R.id.navigation_majad){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AuctionFragment.newInstance())
                    .commitNow();
            return true;
        }else if(id == R.id.navigation_offers){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, BestFragment.newInstance())
                    .commitNow();
            return true;
        }else{
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, moreFragment.newInstance())
                    .commitNow();
            return true;
        }
    }
}
