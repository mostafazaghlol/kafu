package com.eltamiuzcom.kafu.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eltamiuzcom.kafu.Model.montagat.Datum;
import com.eltamiuzcom.kafu.R;
import com.eltamiuzcom.kafu.Utils.contants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BestordersAdapter extends RecyclerView.Adapter<BestordersAdapter.ViewHolder> {


    private List<Datum> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public BestordersAdapter(Context context, List<Datum> data, ItemClickListener mClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickListener = mClickListener;
    }
// data is passed into the constructor
    public BestordersAdapter(Context context, List<Datum> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.offeritem, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Datum datum = mData.get(position);
        if(datum.getSpecial() == 1) {
            holder.title.setText(datum.getTitle());
            holder.price.setText(datum.getPrice() + " ريال");
            if (datum.getImages().size() > 0) {
                Picasso.get().load(contants.url + datum.getImages().get(0).getImage()).into(holder.imageView);
            }
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }




    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title,price;
        ImageView imageView;
        ViewHolder(final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.offer_title);
            price = itemView.findViewById(R.id.offer_price);
            imageView = itemView.findViewById(R.id.offer_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Datum getItem(int id) {
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
