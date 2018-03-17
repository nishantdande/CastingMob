package com.castingmob.ui.adapter;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 26/01/16
 ==============================================
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.castingmob.CastingMob;
import com.castingmob.R;

import java.util.List;

public class PhotosAdapter  extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {

    private static final PhotosAdapter photosAdapter = new PhotosAdapter();

    private List<String> photoUrls;

    private PhotosAdapter(){}
    public static PhotosAdapter getInstance() {
        return photosAdapter;
    }

    public void setPhotos(List<String> photoUrls){
        this.photoUrls = photoUrls;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_photo_view, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (photoUrls != null && photoUrls.size()>0) {
            Glide.with(CastingMob.getInstance().getContext())
                    .load(photoUrls.get(position))
                    .centerCrop()
                    .placeholder(R.drawable.ic_logo_final_black)
                    .crossFade()
                    .into(holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return photoUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.photo);
        }
    }
}
