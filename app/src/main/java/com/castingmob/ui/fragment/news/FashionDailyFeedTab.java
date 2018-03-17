package com.castingmob.ui.fragment.news;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 08/03/16
 ==============================================
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.castingmob.Feed.rss.RSSConfig;
import com.castingmob.Feed.rss.RSSFeed;
import com.castingmob.Feed.rss.RSSItem;
import com.castingmob.Feed.rss.RSSParser;
import com.castingmob.Feed.rss.RssAdapter;
import com.castingmob.R;
import com.castingmob.network.NetworkManager;
import com.castingmob.ui.adapter.FashionDailyFeedAdapter;
import com.castingmob.ui.fragment.BaseFragment;
import com.castingmob.ui.view.CastingTabRecycleView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FashionDailyFeedTab extends BaseFragment {

    private static FashionDailyFeedTab feedTabs = null;

    private static int tabPosition = -1;

    @Bind(R.id.feeds)
    CastingTabRecycleView mFeedList;
    private FashionDailyFeedAdapter fashionDailyFeedAdapter;

    // factory method for Led Fragment to create instance of LED fragment
    public static FashionDailyFeedTab createFragment(int tabPosition) {
        feedTabs = new FashionDailyFeedTab();
        return feedTabs;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            fetchRssFeed();
    }

    private void fetchRssFeed() {
        Retrofit.Builder retrofitBuilder  = new Retrofit.Builder()
                .baseUrl("https://fashionweekdaily.com");

        Retrofit retrofit = retrofitBuilder.client(NetworkManager.getOkHttpClient().build()).build();
        RssAdapter rssAdapter = retrofit.create(RssAdapter.class);

        Call<ResponseBody> items = rssAdapter.getItems();
        items.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                RSSParser rssParser = new RSSParser(new RSSConfig());

                RSSFeed rssFeed = rssParser.parse(response.body().byteStream());


                refreshFeeds(rssFeed.getItems());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable error) {
                Log.e("Error", error.getMessage(), error);

            }
        });
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

        fashionDailyFeedAdapter = FashionDailyFeedAdapter.getInstance();
        mFeedList.setAdapter(fashionDailyFeedAdapter);
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
        fashionDailyFeedAdapter.setFeeds(getActivity(), feedItems);
    }
}
