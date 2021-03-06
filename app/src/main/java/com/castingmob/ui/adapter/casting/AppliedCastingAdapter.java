package com.castingmob.ui.adapter.casting;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 26/01/16
 ==============================================
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.castingmob.CastingMob;
import com.castingmob.R;
import com.castingmob.casting.model.CastingItem;

import java.util.List;

public class AppliedCastingAdapter extends RecyclerView.Adapter<AppliedCastingAdapter.ViewHolder> {

    private static final AppliedCastingAdapter feedAdapter = new AppliedCastingAdapter();

    AppliedCastingAdapter(){}

    public static AppliedCastingAdapter getInstance() {
        return feedAdapter;
    }

    private List<CastingItem> castingItems;
    private Context context;

    public void setcasting(Context context,List<CastingItem> castingItems){
        this.context = context;
        if (this.castingItems!=null && this.castingItems.size()>0) {
            this.castingItems.clear();
            this.notifyDataSetChanged();
        }
        this.castingItems = castingItems;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_applied_casting, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (castingItems != null && castingItems.size()>0) {
            CastingItem castingItem = castingItems.get(position);

            Glide.with(CastingMob.getInstance().getContext())
                    .load(castingItem.getPhotoUrl())
                    .centerCrop()
                    .placeholder(R.drawable.background)
                    .crossFade()
                    .into(holder.imageView);

            holder.postName.setText(castingItem.getTitle());
            holder.postDescp.setText(castingItem.getDescription());
            holder.locationName.setText(String.format("%s,%s,%s",castingItem.getCity(),
                    castingItem.getState(),
                    castingItem.getCountry()));
            if (castingItem.getPaid()){
                holder.paidLayout.setVisibility(View.VISIBLE);
            }else {
                holder.paidLayout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return castingItems == null ? 0 : castingItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView postName;
        protected TextView postDescp;
        protected TextView locationName;
        protected TextView paidLayout;
        protected ImageView mSendMessage;


        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.postImage);
            this.postName = (TextView) view.findViewById(R.id.postName);
            this.postDescp = (TextView) view.findViewById(R.id.postDescp);
            this.locationName = (TextView) view.findViewById(R.id.locationName);
            this.paidLayout = (TextView) view.findViewById(R.id.paidLayout);
            this.mSendMessage = (ImageView) view.findViewById(R.id.sendMessage);
        }
    }

    /**
     * Get the Feed By Position
     * @param position
     * @return
     */
    public CastingItem getCastByPosition(int position){
        return castingItems.get(position);
    }

}
