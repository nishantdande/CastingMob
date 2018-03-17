package com.castingmob.ui.activity;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 06/08/16
 ==============================================
 */

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.castingmob.Feed.model.Filter;
import com.castingmob.R;
import com.castingmob.event.EventManager;
import com.castingmob.ui.fragment.feeds.ModelsFragment;
import com.castingmob.ui.fragment.home.CastingContainerFragment;
import com.castingmob.ui.fragment.home.HomeScreenContainerFragment;
import com.castingmob.ui.fragment.home.MessageFragment;
import com.castingmob.ui.fragment.home.NewsFeedFragment;
import com.castingmob.ui.fragment.profile.ProfileContainerFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewHomeScreenActivity extends CastingMobActivity {

    public interface onFilterChangeListener{
        void onFilterChange(Filter filter);
        void onCastingFilterChange(Filter filter);
    }

    public enum TAB_ACTIVITY{
        HOME_SCREEN(0),
        CHAT_SCREEN(1),
        PROFILE_SCREEN(2),
        CASTING_SCREEN(3),
        NEWS_FEED(4);
        int id;
        TAB_ACTIVITY(int id) {
            this.id = id;
        }
    }

    private FragmentTransaction mFragmentTransaction;
    public FragmentManager mFragmentManager;
    public onFilterChangeListener onFilterChangeListener;

    private int[] mTabsIcons = {
            R.drawable.ic_feeds,
            R.drawable.ic_chat,
            R.drawable.ic_profile,
            R.drawable.ic_casting,
            R.drawable.ic_news};

    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;

    @Bind(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_home_screen);
        ButterKnife.bind(this);

        viewPager.setOffscreenPageLimit(3);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }

        mTabLayout.getTabAt(0).getCustomView().setSelected(true);
        setTabSelectedColor(mTabLayout.getTabAt(0));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab selectedTab) {
                setTabSelectedColor(selectedTab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setTabUnSelectedColor(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setTabSelectedColor(TabLayout.Tab selectedTab){
        View customView = selectedTab.getCustomView();
        ((ImageView)customView.findViewById(R.id.icon)).setColorFilter(Color.parseColor("#1E88E5"));
        ((TextView)customView.findViewById(R.id.title)).setTextColor(Color.parseColor("#1E88E5"));
    }

    private void setTabUnSelectedColor(TabLayout.Tab unSelectedTab){
        View customView = unSelectedTab.getCustomView();
        ((ImageView)customView.findViewById(R.id.icon)).setColorFilter(Color.WHITE);
        ((TextView)customView.findViewById(R.id.title)).setTextColor(Color.WHITE);
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {

        public final int PAGE_COUNT = 5;

        private final String[] mTabsTitle = {"Feed", "Chat", "Profile", "Casting", "News"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View view = LayoutInflater.from(NewHomeScreenActivity.this).inflate(R.layout.item_tab, null);
            ImageView icon = (ImageView) view.findViewById(R.id.icon);
            icon.setImageResource(mTabsIcons[position]);
            TextView title = (TextView) view.findViewById(R.id.title);
            title.setText(mTabsTitle[position]);
            return view;
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return new HomeScreenContainerFragment();

                case 1:
                    return new MessageFragment();

                case 2:
                    return new ProfileContainerFragment();

                case 3:
                    return new CastingContainerFragment();

                case 4:
                    return new NewsFeedFragment();

            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabsTitle[position];
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (onFilterChangeListener != null){
            if (resultCode == 200){
                EventBus.getDefault().post(new EventManager(Filter.getFilter()));
            }else if(resultCode == 201){
                EventBus.getDefault().post(new ModelsFragment.LocationBradcast(Filter.getFilter()));
            }else if(resultCode == RESULT_OK){
                EventBus.getDefault().post(new EventManager(Filter.getFilter()));
            }
        }

        logger.debug(Filter.getInstance().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }
}
