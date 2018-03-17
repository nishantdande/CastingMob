package com.castingmob.ui.adapter;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 26/01/16
 ==============================================
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.castingmob.CastingMob;
import com.castingmob.Feed.rss.RSSItem;
import com.castingmob.R;
import com.castingmob.utils.DateTimeUtility;
import com.castingmob.utils.XmlParserUtility;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public class VogueParisFeedAdapter extends RecyclerView.Adapter<VogueParisFeedAdapter.ViewHolder> {

    private static VogueParisFeedAdapter feedAdapter = new VogueParisFeedAdapter();

    VogueParisFeedAdapter(){}

    public static VogueParisFeedAdapter getInstance() {
        return feedAdapter;
    }

    private List<RSSItem> rssItems;
    private Context context;
    private Uri moreUrl = null;

    public void setFeeds(Context context,List<RSSItem> rssItems){
        this.context = context;
        this.rssItems = rssItems;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news_feed_with_image, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (rssItems != null && rssItems.size()>0) {
            RSSItem rssItem = rssItems.get(position);
            holder.title.setText(rssItem.getTitle());
            holder.pub_date.setText(DateTimeUtility.messageTimeStamp(rssItem.getPubDate()));

            try {
                String xmlData[] = XmlParserUtility.parserTag(rssItem.getDescription());
                Log.d("imageFeed", xmlData[0]+"");
                holder.desp.setText(xmlData[1]);
                Glide.with(CastingMob.getInstance().getContext())
                    .load(xmlData[0])
                    .centerCrop()
                    .placeholder(R.drawable.ic_logo_final_black)
                    .crossFade()
                    .into(holder.imageView);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            moreUrl = rssItem.getLink();

        }
    }

    @Override
    public int getItemCount() {
        return rssItems == null ? 0 : rssItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView imageView;
        protected TextView title;
        protected TextView pub_date;
        protected TextView desp;
        protected Button more;


        public ViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.feedImage);
            this.title = (TextView) view.findViewById(R.id.title);
            this.pub_date = (TextView) view.findViewById(R.id.pub_date);
            this.desp = (TextView) view.findViewById(R.id.desp);
            this.more = (Button) view.findViewById(R.id.more);
            this.more.setOnClickListener(this);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            if (moreUrl != null){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, moreUrl);
                context.startActivity(browserIntent);
            }
        }
    }

    /**
     * Get the Feed By Position
     * @param position
     * @return
     */
    public RSSItem getFeedByPosition(int position) {
        return rssItems.get(position);
    }
}
