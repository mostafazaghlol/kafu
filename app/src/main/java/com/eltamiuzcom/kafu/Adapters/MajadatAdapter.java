package com.eltamiuzcom.kafu.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eltamiuzcom.kafu.Model.more;
import com.eltamiuzcom.kafu.Model.myacutions.Bidder;
import com.eltamiuzcom.kafu.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MajadatAdapter extends RecyclerView.Adapter<MajadatAdapter.ViewHolder>  {


    private List<Bidder> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MajadatAdapter(Context context, List<Bidder> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }// data is passed into the constructor
    public MajadatAdapter(Context context, List<Bidder> data, ItemClickListener mClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickListener =mClickListener;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.majadaitem, parent, false);
        return new ViewHolder(view);
    }

    private void applyFontToMenuItem(TextView mi) {
//        Typeface font = Typeface.createFromAsset(mi.getContext().getAssets(), "cairo.ttf");
//        mi.setTypeface(font);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Bidder mSearchresult = mData.get(position);
        holder.NameTextView.setText(mSearchresult.getUserName());
        holder.PriceTextView.setText(mSearchresult.getPrice());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView NameTextView,PriceTextView;

        ViewHolder(View itemView) {
            super(itemView);
            NameTextView = itemView.findViewById(R.id.majadnama);
            PriceTextView = itemView.findViewById(R.id.majadprice);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Bidder getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
