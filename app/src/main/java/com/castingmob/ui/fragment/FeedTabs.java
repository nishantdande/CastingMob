package com.castingmob.ui.fragment;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 06/03/16
 ==============================================
 */

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.castingmob.Feed.model.FeedItem;
import com.castingmob.Feed.rss.RSSFeed;
import com.castingmob.Feed.rss.RSSItem;
import com.castingmob.Feed.rss.RSSReader;
import com.castingmob.Feed.rss.RSSReaderException;
import com.castingmob.R;
import com.castingmob.account.AccountManager;
import com.castingmob.account.model.UserDetail;
import com.castingmob.ui.activity.CastingMobActivity;
import com.castingmob.ui.activity.DetailActivity;
import com.castingmob.ui.activity.NewsFeedActivity;
import com.castingmob.ui.activity.Router;
import com.castingmob.ui.adapter.FeedAdapter;
import com.castingmob.ui.adapter.NewsFeedAdapter;
import com.castingmob.ui.fragment.BaseFragment;
import com.castingmob.ui.view.CastingRecycleView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedTabs extends BaseFragment implements NewsFeedActivity.onPageChange {

    @Bind(R.id.feeds)
    CastingRecycleView mFeedList;

    private NewsFeedAdapter newsFeedAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            int position = bundle.getInt("page");
            fetchRssFeed(position);
        }
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
    public void onAttach(Context context) {
        super.onAttach(context);
        ((NewsFeedActivity)context).mOnPageChange = this;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        newsFeedAdapter = NewsFeedAdapter.getInstance();
        mFeedList.setAdapter(newsFeedAdapter);
        mFeedList.setItemClickListener(onItemClickListener);
    }

    CastingRecycleView.OnItemClickListener onItemClickListener
            = new CastingRecycleView.OnItemClickListener() {
        @Override
        public void onItemClick(View view, final int position) {

        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    };

    private void refreshFeeds(List<RSSItem> feedItems) {
        newsFeedAdapter.setFeeds(getActivity(), feedItems);
    }

    @Override
    public void pageChange(int position) {
        fetchRssFeed(position);
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
                RSSFeed feed = rssReader.load(getUrl(params[0]));
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

        private String getUrl(int tabPosition){
            switch (tabPosition){
                case 0:{
                    return "http://harpers.org/feed/";
                }

                case 1:{
                    return "http://vogueparis.tumblr.com/rss";
                }

                case 2:{
                    return "http://models.com/mdcdb/rss/output.xml";
                }

                case 3:{
                    return "http://fashionweekdaily.com/feed/";
                }

                default:{
                    return "http://harpers.org/feed/";
                }

            }
        }
    }
}
