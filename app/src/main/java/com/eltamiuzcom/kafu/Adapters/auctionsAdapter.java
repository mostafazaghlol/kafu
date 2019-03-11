package com.eltamiuzcom.kafu.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eltamiuzcom.kafu.Model.auctionmodel;
import com.eltamiuzcom.kafu.Model.myauctions2.Datum;
import com.eltamiuzcom.kafu.R;
import com.eltamiuzcom.kafu.Utils.contants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class auctionsAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Datum> books;
    // 1
    public auctionsAdapter(Context context, ArrayList<Datum> books) {
        this.mContext = context;
        this.books = books;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Datum mAuctionmodel  = books.get(position);
        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.offeritem2, null);
        }
// 3
        final ImageView imageView = (ImageView)convertView.findViewById(R.id.offer_image_offer2);
        final TextView nameTextView = (TextView)convertView.findViewById(R.id.offer_title_offer2);
        final TextView timeTextView = (TextView)convertView.findViewById(R.id.offer_time_offer2);
        final TextView priceTextView = (TextView)convertView.findViewById(R.id.offer_price_offer2);

        // 4
        Picasso.get().load(contants.url+mAuctionmodel.getImage()).placeholder(R.drawable.fackphoto).into(imageView);
        nameTextView.setText(mAuctionmodel.getTitle());
        timeTextView.setText(mAuctionmodel.getCreatedAt());
        priceTextView.setText("يبدأ من "+mAuctionmodel.getPrice()+" ريال سعودى ");

        return convertView;
    }
}
