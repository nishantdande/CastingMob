package com.castingmob.ui.adapter.feeds;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 26/01/16
 ==============================================
 */

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.castingmob.CastingMob;
import com.castingmob.Feed.model.FeedItem;
import com.castingmob.R;
import com.castingmob.account.model.Profile;

import java.util.ArrayList;
import java.util.List;

public class MUAsAdapter extends RecyclerView.Adapter<MUAsAdapter.ViewHolder> {

    private static final MUAsAdapter feedAdapter = new MUAsAdapter();

    public static MUAsAdapter getInstance() {
        return feedAdapter;
    }

    private List<FeedItem> feedItems;
    private Context context;

    public void setFeeds(Context context,List<FeedItem> feedItems){
        this.context = context;
        if (this.feedItems != null && this.feedItems.size()>0){
            this.feedItems.addAll(feedItems);
        }else {
            this.feedItems = feedItems;
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home_screen, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (feedItems != null && feedItems.size()>0) {
            Glide.with(CastingMob.getInstance().getContext())
                    .load(feedItems.get(position).getPhotoUrl())
                    .centerCrop()
                    .placeholder(R.drawable.ic_logo_final_black)
                    .crossFade()
                    .into(holder.imageView);

            likeView(holder, feedItems.get(position));
            holder.mName.setText(feedItems.get(position).getUserDisplayName());
        }
    }

    @Override
    public int getItemCount() {
        return feedItems == null ? 0 : feedItems.size();
    }

    public void clear(){
        if (feedItems!=null && feedItems.size()>0){
            feedItems.clear();
            notifyDataSetChanged();
        }
    }

    public void updateLike(int position, ArrayList<String> id){
        FeedItem feedItem = feedItems.get(position);
        if (!feedItem.getLikes().contains(id)){
            feedItem.getLikes().addAll(id);
        }

        feedItems.remove(position);
        feedItems.add(position, feedItem);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView mLikeCount;
        protected TextView mCommentCount;
        protected TextView mName;
        protected ImageView mHighlight;


        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.feed);
            mLikeCount = (TextView) view.findViewById(R.id.like_count);
            mName = (TextView) view.findViewById(R.id.name);
            mHighlight = (ImageView) view.findViewById(R.id.highlight_like);
        }
    }

    private void likeView(ViewHolder viewHolder, FeedItem feedItem){
        if (viewHolder != null) {
            if (feedItem.getLikes() != null)
                viewHolder.mLikeCount
                        .setText(context.getString(R.string.like_count, feedItem.getLikes().size()));
            if (feedItem.getLikes().contains(Profile.getInstance().getToken()) && feedItem.getLikes().size()>0)
                viewHolder.mHighlight
                        .setImageDrawable(ContextCompat.getDrawable(context, R.drawable.circle_shape));
        }
    }

    /**
     * Get the Feed By Position
     * @param position
     * @return
     */
    public FeedItem getFeedByPosition(int position){
        return feedItems.get(position);
    }

    /**
     * Get the access token of feed item by position
     * @param position
     * @return
     */
    public String getFeedAccessTokenByPosition(int position){
        FeedItem feedItem = getFeedByPosition(position);
        return feedItem.getUserToken();
    }
}
