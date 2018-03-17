package com.castingmob.ui.fragment.home;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 07/08/16
 ==============================================
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.castingmob.R;
import com.castingmob.ui.activity.NewHomeScreenActivity;
import com.castingmob.ui.fragment.BaseFragment;
import com.castingmob.ui.fragment.news.FashionDailyFeedTab;
import com.castingmob.ui.fragment.news.HarpersFeedTab;
import com.castingmob.ui.fragment.news.ModelsComFeedTab;
import com.castingmob.ui.fragment.news.VogueParisFeedTab;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsFeedFragment extends BaseFragment {

    private static NewsFeedFragment newsFeedFragment;

    public static NewsFeedFragment getInstance() {
        if (newsFeedFragment == null)
            newsFeedFragment = new NewsFeedFragment();

        return newsFeedFragment;
    }

    public interface onPageChange{
        void pageChange(int position);
    }

    @Bind(R.id.viewpager)
    ViewPager mFeedViewPager;
    @Bind(R.id.sliding_tabs)
    TabLayout mSlidingTabLayout;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private ViewPagerAdapter adapter;
    public onPageChange mOnPageChange;
    private NewHomeScreenActivity newHomeScreenActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.newHomeScreenActivity != null) {
            this.newHomeScreenActivity.setSupportActionBar(mToolbar);
        }
        setupViewPager(mFeedViewPager);
        mSlidingTabLayout.setupWithViewPager(mFeedViewPager);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.newHomeScreenActivity = ((NewHomeScreenActivity)context);
    }

    private void setupViewPager(ViewPager viewPager) {
        if (adapter == null && this.newHomeScreenActivity != null) {
            adapter = new ViewPagerAdapter(getChildFragmentManager());
            adapter.addFragment(HarpersFeedTab.createFragment(0), "Harpers");
            adapter.addFragment(FashionDailyFeedTab.createFragment(1), "FashionWeekDaily");
            adapter.addFragment(VogueParisFeedTab.createFragment(2), "Vogue Paris");
            adapter.addFragment(ModelsComFeedTab.createFragment(3), "Models.com");
        }
        viewPager.setAdapter(adapter);
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
