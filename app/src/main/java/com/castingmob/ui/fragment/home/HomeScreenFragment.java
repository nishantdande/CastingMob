package com.castingmob.ui.fragment.home;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 26/01/16
 ==============================================
 */

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.castingmob.Feed.model.Filter;
import com.castingmob.R;
import com.castingmob.prefrences.ProfilePreferences;
import com.castingmob.ui.activity.NewHomeScreenActivity;
import com.castingmob.ui.activity.Router;
import com.castingmob.ui.fragment.BaseFragment;
import com.castingmob.ui.fragment.feeds.MUAsFragment;
import com.castingmob.ui.fragment.feeds.ModelsFragment;
import com.castingmob.ui.fragment.feeds.PhotographerFragment;
import com.castingmob.ui.fragment.feeds.StylistsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeScreenFragment extends BaseFragment {

    private NewHomeScreenActivity homeScreenActivity;
    @Bind(R.id.viewpager)
    ViewPager mFeedViewPager;
    @Bind(R.id.sliding_tabs)
    TabLayout mSlidingTabLayout;

    @Bind(R.id.location)
    TextView mFilteredLocation;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private ViewPagerAdapter adapter;

    private static HomeScreenFragment homeScreenFragment;

    public static HomeScreenFragment createFragment() {
        if (homeScreenFragment == null) {
            homeScreenFragment = new HomeScreenFragment();
        }
        return homeScreenFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mFeedViewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void onResume() {
        super.onResume();
        setupViewPager(mFeedViewPager);
        mSlidingTabLayout.setupWithViewPager(mFeedViewPager);

        if (getTitleLocation()!= null && !TextUtils.isEmpty(getTitleLocation())) {
            mFilteredLocation.setVisibility(View.VISIBLE);
            mFilteredLocation.setText(getTitleLocation());
        }else{
            mFilteredLocation.setVisibility(View.GONE);
        }
    }

    public String getTitleLocation(){
        Filter filter = ProfilePreferences.getInstance().getFilterInfo();
        if (filter != null){
            Filter.getInstance().setCity(filter.getCity());
            Filter.getInstance().setState(filter.getState());
            Filter.getInstance().setCountry(filter.getCountry());

            StringBuilder s  = new StringBuilder();
            if (!TextUtils.isEmpty(Filter.getInstance().getCity())){
                s.append(Filter.getInstance().getCity()+",");
            }

            if (!TextUtils.isEmpty(Filter.getInstance().getState())){
                s.append(Filter.getInstance().getState()+",");
            }

            if (!TextUtils.isEmpty(Filter.getInstance().getCountry())){
                s.append(Filter.getInstance().getCountry());
            }

            return s.toString();
        }
        return null;
    }

    @OnClick(R.id.search)
    public void onSearchClick(){
        Router.startFilterActivity(homeScreenActivity, null);
    }

    @OnClick(R.id.location_filter)
    public void onLocationFilterClick(){
        Bundle bundle = new Bundle();
        bundle.putString("tab", "feed");
        Router.startLocationFilterActivity(homeScreenActivity, bundle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeScreenActivity = ((NewHomeScreenActivity) context);
    }

    private void setupViewPager(ViewPager viewPager) {
        if (adapter == null && homeScreenActivity != null) {
            adapter = new ViewPagerAdapter(getChildFragmentManager());
            adapter.addFragment(ModelsFragment.createFragment(), getString(R.string.feed_tab_models));
            adapter.addFragment(PhotographerFragment.createFragment(), getString(R.string.feed_tab_photographer));
            adapter.addFragment(MUAsFragment.createFragment(), getString(R.string.feed_tab_muas));
            adapter.addFragment(StylistsFragment.createFragment(), getString(R.string.feed_tab_stylists));
            viewPager.setAdapter(adapter);
        }


    }


    class ViewPagerAdapter extends FragmentStatePagerAdapter {
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
}
