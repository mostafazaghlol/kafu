package com.eltamiuzcom.kafu.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eltamiuzcom.kafu.Model.Fawteer.Datum;
import com.eltamiuzcom.kafu.R;
import com.eltamiuzcom.kafu.Utils.contants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class fawteersAdapter extends RecyclerView.Adapter<fawteersAdapter.ViewHolder> {


    private List<Datum> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public fawteersAdapter(Context context, List<Datum> data, ItemClickListener mClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mClickListener = mClickListener;
    }
// data is passed into the constructor
    public fawteersAdapter(Context context, List<Datum> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fawteeritem, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Datum datum = mData.get(position);
        holder.Dealtitle.setText(datum.getItemname());
        holder.type1.setText(datum.getType());
        holder.weight.setText(datum.getWeight());
        holder.length.setText(datum.getLang());
        holder.date.setText(datum.getDate());
        holder.montagetype.setText(datum.getType());
        holder.mandoopname.setText(datum.getDelivernamename());
        holder.mandoopprice.setText(datum.getDeliverprice());
        holder.toname.setText(datum.getOwnername());
        holder.priceto.setText(String.valueOf(datum.getTotalprice()-Integer.parseInt(datum.getDeliverprice())));
        holder.fullprice.setText(datum.getTotalprice().toString());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }




    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Dealtitle,type1,weight,length,date,labanatype,montagetype,priceto,toname,mandoopname,mandoopprice,fullprice;
        ViewHolder(final View itemView) {
            super(itemView);
            Dealtitle = itemView.findViewById(R.id.Txtmontagename);
            type1 = itemView.findViewById(R.id.Txtfatweertype1);
            weight = itemView.findViewById(R.id.Txtfatweerweight);
            length = itemView.findViewById(R.id.TxtfatweerLength);
            date = itemView.findViewById(R.id.Txtfatweerdate);
            labanatype = itemView.findViewById(R.id.TxtfatweerlabanaType);
            montagetype = itemView.findViewById(R.id.TxtFatweertype1x);
            priceto = itemView.findViewById(R.id.Txtfatweerpricetpo);
            toname = itemView.findViewById(R.id.Txtfatweerpricetponame);
            mandoopname = itemView.findViewById(R.id.Txtfatweermandoopname);
            mandoopprice = itemView.findViewById(R.id.Txtfatweermandoopprice);
            fullprice = itemView.findViewById(R.id.Txtfatweerfullprice);
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
