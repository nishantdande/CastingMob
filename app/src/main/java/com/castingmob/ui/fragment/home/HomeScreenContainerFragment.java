package com.castingmob.ui.fragment.home;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 15/09/16
 ==============================================
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.castingmob.R;
import com.castingmob.event.EventManager;
import com.castingmob.ui.activity.NewHomeScreenActivity;
import com.castingmob.ui.fragment.BaseFragment;
import com.castingmob.ui.fragment.feeds.FeedDetailFragment;
import com.castingmob.ui.fragment.home.HomeScreenFragment;
import com.castingmob.ui.view.NonSwipeableViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeScreenContainerFragment extends BaseFragment {

    private NewHomeScreenActivity homeScreenActivity;
    @Bind(R.id.view_pager)
    NonSwipeableViewPager mFeedViewPager;

    private ViewPagerAdapter adapter;

    public enum FEED_ACTIVITY{
        FEEDS(0),
        FEED_DETAIL(1);
        int id;
        FEED_ACTIVITY(int id) {
            this.id = id;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(HomeScreenContainerFragment.this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_container, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setupViewPager(mFeedViewPager);
        mFeedViewPager.setCurrentItem(0);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeScreenActivity = ((NewHomeScreenActivity) context);
    }

    @Subscribe
    // This method will be called when a HelloWorldEvent is posted
    public void onEvent(EventManager event){

        if (event!=null && event.getFeedActivity()!= null){
            switch (event.getFeedActivity()){
            case FEEDS:
                mFeedViewPager.setCurrentItem(0);
                break;

            case FEED_DETAIL:
                mFeedViewPager.setCurrentItem(1);
                break;

            }
        }
    }

    private void setupViewPager(NonSwipeableViewPager viewPager) {
        if (adapter == null && homeScreenActivity != null) {
            adapter = new ViewPagerAdapter(getChildFragmentManager());
        }
        adapter.addFragment(HomeScreenFragment.createFragment(), "");
        adapter.addFragment(FeedDetailFragment.createFragment(), "");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(HomeScreenContainerFragment.this);
    }
}
