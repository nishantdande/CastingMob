package com.castingmob.ui.view;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 26/01/16
 ==============================================
 */

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.castingmob.ui.adapter.DividerItemDecoration;
import com.castingmob.ui.adapter.RecyclerItemClickListener;

public class CastingPhotosRecycleView extends RecyclerView {

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    private LayoutManager mLayoutManager;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public CastingPhotosRecycleView(Context context) {
        this(context, null);
    }

    public CastingPhotosRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        this.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(context, 3);
        this.setLayoutManager(mLayoutManager);

        // this is the default;
        // this call is actually only necessary with custom ItemAnimators
        this.setItemAnimator(new DefaultItemAnimator());

        //Add the divider between two items of list
        ItemDecoration itemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST);
        this.addItemDecoration(itemDecoration);

        addOnItemTouchListener(recyclerItemClickListener);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener){
        this.onItemClickListener = itemClickListener;
    }

    RecyclerItemClickListener recyclerItemClickListener = new RecyclerItemClickListener(context,
            this, new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            onItemClickListener.onItemClick(view,position);
        }

        @Override
        public void onItemLongClick(View view, int position) {
            onItemClickListener.onItemLongClick(view,position);
        }
    });
}
