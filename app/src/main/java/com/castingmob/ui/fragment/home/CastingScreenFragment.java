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
import com.castingmob.account.model.Profile;
import com.castingmob.casting.CastingManager;
import com.castingmob.casting.model.CastingItem;
import com.castingmob.event.EventManager;
import com.castingmob.prefrences.ProfilePreferences;
import com.castingmob.ui.activity.NewHomeScreenActivity;
import com.castingmob.ui.activity.NewsFeedActivity;
import com.castingmob.ui.activity.Router;
import com.castingmob.ui.fragment.BaseFragment;
import com.castingmob.ui.fragment.casting.MUAsCastingFragment;
import com.castingmob.ui.fragment.casting.ModelsCastingFragment;
import com.castingmob.ui.fragment.casting.PhotographerCastingFragment;
import com.castingmob.ui.fragment.casting.StylistsCastingFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CastingScreenFragment extends BaseFragment implements NewHomeScreenActivity.onFilterChangeListener {

    private static CastingScreenFragment castingScreenFragment;
    private NewHomeScreenActivity homeScreenActivity;

    public static CastingScreenFragment createFragment() {
        if (castingScreenFragment == null) {
            castingScreenFragment = new CastingScreenFragment();
        }
        return castingScreenFragment;
    }
    @Bind(R.id.viewpager)
    ViewPager mFeedViewPager;
    @Bind(R.id.sliding_tabs)
    TabLayout mSlidingTabLayout;

    @Bind(R.id.location)
    TextView mLocation;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    private ViewPagerAdapter adapter;

    public NewsFeedActivity.onPageChange mOnPageChange;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_casting_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mFeedViewPager.setOffscreenPageLimit(1);
    }

    @Override
    public void onResume() {
        super.onResume();
            setUp();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
            getCasting();
    }

    private void setUp(){
        setupViewPager(mFeedViewPager);
        mSlidingTabLayout.setupWithViewPager(mFeedViewPager);

        if (getTitleLocation()!=null && !TextUtils.isEmpty(getTitleLocation())) {
            mLocation.setVisibility(View.VISIBLE);
            mLocation.setText(getTitleLocation());
        }else{
            mLocation.setVisibility(View.GONE);
        }

    }

    public String getTitleLocation(){
        Filter filter = ProfilePreferences.getInstance().getFilterInfo();
        if (filter != null){
            Filter.getInstance().setCity(filter.getCityCasting());
            Filter.getInstance().setState(filter.getStateCasting());
            Filter.getInstance().setCountry(filter.getCountryCasting());

            StringBuilder s  = new StringBuilder();
            if (!TextUtils.isEmpty(Filter.getInstance().getCityCasting())){
                s.append(Filter.getInstance().getCityCasting()+",");
            }

            if (!TextUtils.isEmpty(Filter.getInstance().getStateCasting())){
                s.append(Filter.getInstance().getStateCasting()+",");
            }

            if (!TextUtils.isEmpty(Filter.getInstance().getCountryCasting())){
                s.append(Filter.getInstance().getCountryCasting());
            }

            return s.toString();
        }
        return null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeScreenActivity = ((NewHomeScreenActivity) context);
        ((NewHomeScreenActivity)context).onFilterChangeListener = this;
    }

    private void setupViewPager(ViewPager viewPager) {
        if (adapter == null && homeScreenActivity != null) {
            adapter = new ViewPagerAdapter(getChildFragmentManager());
            adapter.addFragment(ModelsCastingFragment.createFragment(), getString(R.string.feed_tab_models));
            adapter.addFragment(PhotographerCastingFragment.createFragment(), getString(R.string.feed_tab_photographer));
            adapter.addFragment(MUAsCastingFragment.createFragment(), getString(R.string.feed_tab_muas));
            adapter.addFragment(StylistsCastingFragment.createFragment(), getString(R.string.feed_tab_stylists));
        }
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    @OnClick(R.id.my_casting)
    public void MyCasting(){
        EventBus.getDefault().post(new EventManager(CastingContainerFragment.CASTING_ACTIVITY.MY_CASTING));
    }

    @OnClick(R.id.filter)
    public void onFilter(){
        Bundle bundle = new Bundle();
        bundle.putString("tab", "casting");
        Router.startLocationFilterActivity(homeScreenActivity, bundle);
    }

    @Override
    public void onFilterChange(Filter filter) {
        // Never Called
    }

    @Override
    public void onCastingFilterChange(Filter filter) {
        Filter.getInstance().toString();
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

    public void getCasting(){
        CastingManager.getInstance().getCasting(Profile.getInstance().getToken(), new Callback<List<CastingItem>>() {
            /**
             * Invoked for a received HTTP response.
             * <p/>
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call {@link Response#isSuccessful()} to determine if the response indicates success.
             *
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<List<CastingItem>> call, Response<List<CastingItem>> response) {
                if (response.isSuccessful()) {
                    EventBus.getDefault().post(new EventManager(response.body()));
                } else {
                    homeScreenActivity.logger.error(new Exception(response.raw() + ""));
                }
            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             *
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<List<CastingItem>> call, Throwable t) {
                homeScreenActivity.logger.error(t);
            }
        });
    }
}
