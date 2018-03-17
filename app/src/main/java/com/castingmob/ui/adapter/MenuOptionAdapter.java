package com.castingmob.ui.adapter;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 24/01/16
 ==============================================
 */

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.castingmob.R;

public class MenuOptionAdapter extends RecyclerView.Adapter<MenuOptionAdapter.ViewHolder> {

    String[] menuOptions;
    Activity activity;

    public MenuOptionAdapter(Activity activity) {
        this.activity = activity;
        menuOptions = activity.getResources().getStringArray(R.array.menu_option);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType){
            case 1:
            case 2:
            case 5:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_menu_option_with_images, null);
                break;

            case 0:
            case 3:
            case 4:
            case 6:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_menu_option, null);
                break;
        }


        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Setting text view title
        holder.textView.setText(menuOptions[position]);
        if (position==5) {
            holder.imageView.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.post_new_cast));
            holder.imageView.setColorFilter(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return menuOptions.length;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView textView;
        protected ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.title);
            this.imageView = (ImageView) view.findViewById(R.id.icon);

        }
    }
}
