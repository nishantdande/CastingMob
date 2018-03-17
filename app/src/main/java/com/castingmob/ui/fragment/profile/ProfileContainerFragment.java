package com.castingmob.ui.fragment.profile;/*
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
import com.castingmob.ui.view.NonSwipeableViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileContainerFragment extends BaseFragment {

    private NewHomeScreenActivity homeScreenActivity;
    @Bind(R.id.view_pager)
    NonSwipeableViewPager mFeedViewPager;

    private ViewPagerAdapter adapter;

    public enum PROFILE_ACTIVITY{
        PROFILE(0),
        PROFILE_INFO(1),
        PROFILE_STATS(2);
        int id;
        PROFILE_ACTIVITY(int id) {
            this.id = id;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(ProfileContainerFragment.this);
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
        // your implementation
        if (event != null && event.getProfileActivity() != null){
            switch (event.getProfileActivity()){
                case PROFILE:
                    mFeedViewPager.setCurrentItem(0);
                    break;

                case PROFILE_INFO:
                    mFeedViewPager.setCurrentItem(1);
                    break;

                case PROFILE_STATS:
                    mFeedViewPager.setCurrentItem(2);
                    break;
            }
        }
    }

    private void setupViewPager(NonSwipeableViewPager viewPager) {
        if (adapter == null && homeScreenActivity != null) {
            adapter = new ViewPagerAdapter(getChildFragmentManager());
        }
        adapter.addFragment(ProfileFragment.createFragment(), "");
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
        EventBus.getDefault().unregister(ProfileContainerFragment.this);
    }
}
