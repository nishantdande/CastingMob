package com.castingmob.ui.fragment.news;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 08/03/16
 ==============================================
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.castingmob.Feed.rss.RSSFeed;
import com.castingmob.Feed.rss.RSSItem;
import com.castingmob.Feed.rss.RSSReader;
import com.castingmob.Feed.rss.RSSReaderException;
import com.castingmob.R;
import com.castingmob.ui.activity.CastingMobActivity;
import com.castingmob.ui.adapter.VogueParisFeedAdapter;
import com.castingmob.ui.fragment.BaseFragment;
import com.castingmob.ui.view.CastingTabRecycleView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VogueParisFeedTab extends BaseFragment {

    private static VogueParisFeedTab feedTabs = null;

    private static int tabPosition = -1;

    @Bind(R.id.feeds)
    CastingTabRecycleView mFeedList;
    private VogueParisFeedAdapter vogueParisFeedAdapter;

    // factory method for Led Fragment to create instance of LED fragment
    public static VogueParisFeedTab createFragment(int tabPosition) {
        feedTabs = new VogueParisFeedTab();
        return feedTabs;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (tabPosition != -1){
            fetchRssFeed(tabPosition);
//        }
    }

    private void fetchRssFeed(int tabPosition) {
        new FetchRssFeed().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,tabPosition);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_feeds, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        vogueParisFeedAdapter = VogueParisFeedAdapter.getInstance();
        mFeedList.setAdapter(vogueParisFeedAdapter);
        mFeedList.setItemClickListener(onItemClickListener);
    }

    CastingTabRecycleView.OnItemClickListener onItemClickListener
            = new CastingTabRecycleView.OnItemClickListener() {
        @Override
        public void onItemClick(View view, final int position) {

        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    };

    private void refreshFeeds(List<RSSItem> feedItems) {
        vogueParisFeedAdapter.setFeeds(getActivity(), feedItems);
    }

    class FetchRssFeed extends AsyncTask<Integer, Void, RSSFeed>{
        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected RSSFeed doInBackground(Integer... params) {

            RSSReader rssReader = new RSSReader();
            try {
                RSSFeed feed = rssReader.load("http://vogueparis.tumblr.com/rss");
                for (RSSItem rssItem : feed.getItems()){
                    Log.d("feed", rssItem.getDescription());
                }
                return feed;
            } catch (RSSReaderException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(RSSFeed rssFeed) {
            super.onPostExecute(rssFeed);
            if (rssFeed != null){
                refreshFeeds(rssFeed.getItems());
            }else{
                ((CastingMobActivity)getActivity()).logger.error(new Exception("Rss Feed is null"));
            }

        }
    }
}
