package com.castingmob.ui.activity;
/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 06/03/16
 ==============================================
 */

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.castingmob.R;
import com.castingmob.ui.fragment.news.FashionDailyFeedTab;
import com.castingmob.ui.fragment.FeedTabs;
import com.castingmob.ui.fragment.news.HarpersFeedTab;
import com.castingmob.ui.fragment.news.ModelsComFeedTab;
import com.castingmob.ui.fragment.news.VogueParisFeedTab;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsFeedActivity extends CastingMobActivity {

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupViewPager(mFeedViewPager);
        mSlidingTabLayout.setupWithViewPager(mFeedViewPager);
        mFeedViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mOnPageChange != null){
                    mOnPageChange.pageChange(position);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void setupViewPager(ViewPager viewPager) {
        if (adapter == null) {
            adapter = new ViewPagerAdapter(getSupportFragmentManager());
        }
        adapter.addFragment(HarpersFeedTab.createFragment(0), "Harpers");
        adapter.addFragment(FashionDailyFeedTab.createFragment(1), "FashionWeekDaily");
        adapter.addFragment(VogueParisFeedTab.createFragment(2), "Vogue Paris");
        adapter.addFragment(ModelsComFeedTab.createFragment(3), "Models.com");
        viewPager.setAdapter(adapter);
    }

    private Fragment getTabFragment(int page){
        Bundle bundle= new Bundle();
        bundle.putInt("page", page);
        Fragment fragment = new FeedTabs();
        fragment.setArguments(bundle);
        return fragment;
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
}
