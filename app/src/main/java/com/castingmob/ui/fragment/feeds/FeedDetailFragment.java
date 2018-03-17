package com.castingmob.ui.fragment.feeds;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.castingmob.CastingMob;
import com.castingmob.R;
import com.castingmob.account.AccountManager;
import com.castingmob.account.model.Profile;
import com.castingmob.account.model.UserDetail;
import com.castingmob.event.EventManager;
import com.castingmob.ui.activity.DetailActivity;
import com.castingmob.ui.activity.NewHomeScreenActivity;
import com.castingmob.ui.activity.Router;
import com.castingmob.ui.dialog.DialogFactory;
import com.castingmob.ui.fragment.BaseFragment;
import com.castingmob.ui.fragment.feeds.userprofile.UserProfileAboutFragment;
import com.castingmob.ui.fragment.feeds.userprofile.UserProfileGenresFragment;
import com.castingmob.ui.fragment.feeds.userprofile.UserProfileRatesFragment;
import com.castingmob.ui.fragment.feeds.userprofile.UserProfileStatsFragment;
import com.castingmob.ui.fragment.home.HomeScreenContainerFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nishant on 19/09/16.
 */
public class FeedDetailFragment extends BaseFragment {
    private static FeedDetailFragment feedDetailFragment;
    private NewHomeScreenActivity homeScreenActivity;

    public static FeedDetailFragment createFragment() {
        if (feedDetailFragment == null) {
            feedDetailFragment = new FeedDetailFragment();
        }
        return feedDetailFragment;
    }


    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;

    @Bind(R.id.view_pager)
    ViewPager viewPager;

    private ViewPagerAdapter adapter;

    @Bind(R.id.cover_photo)
    ImageView mCoverPhoto;

    @Bind(R.id.profile_photo)
    CircleImageView mProfilePhoto;

    @Bind(R.id.profile_name)
    TextView mProfileName;

    @Bind(R.id.profile_type)
    TextView mProfileType;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        viewPager.setOffscreenPageLimit(3);
        setupViewPager(viewPager);
        mTabLayout.setupWithViewPager(viewPager);

    }

    private void setCoverPhoto() {
        Glide.with(CastingMob.getInstance().getContext())
                .load(UserDetail.getInstance().getCoverPhoto())
                .centerCrop()
                .placeholder(R.drawable.ic_logo_final_black)
                .crossFade()
                .into(mCoverPhoto);
    }

    private void setProfilePhoto() {
        Glide.with(CastingMob.getInstance().getContext())
                .load(UserDetail.getInstance().getProfilePhoto())
                .centerCrop()
                .placeholder(R.drawable.profile_icon)
                .dontAnimate()
                .into(mProfilePhoto);
    }

    @OnClick(R.id.back)
    public void back(){
        EventBus.getDefault().post(new EventManager(HomeScreenContainerFragment.FEED_ACTIVITY.FEEDS));
    }

    @OnClick(R.id.report)
    public void alert(){
        DialogFactory.createAlertDialogWithButtons(getActivity(),
            getString(R.string.block_content),
            getString(R.string.block_content_message),
            getString(R.string.block_content_positive_button),
            getString(R.string.block_content_negative_button), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    AccountManager.getInstance().blockUser(Profile.getInstance().getToken(),
                        UserDetail.getInstance().getToken(), new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (!response.isSuccessful()){
                                logger.error(new Exception(response.message()));
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            logger.error(t);
                        }
                    });
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    dialogInterface.cancel();
                }
            }).show();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
            updateProfile();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeScreenActivity = ((NewHomeScreenActivity) context);
    }

    @OnClick(R.id.message)
    public void message(){

        Bundle bundle = new Bundle();
        bundle.putString("participantToken", UserDetail.getInstance().getToken());
        bundle.putString("participant",UserDetail.getInstance().getDisplayName());
        Router.startSendMessageActivity(getActivity(), bundle);
    }

    @OnClick(R.id.my_photos)
    public void myPhotos(){
        Router.startDetailScreenActivity(getActivity(),
                DetailActivity.DETAIL_FLOW.USER_DETAIL_COVER_PHOTOS.getId());
    }

    @OnClick(R.id.polaroid)
    public void myPolaroid(){
        Router.startDetailScreenActivity(getActivity(),
                DetailActivity.DETAIL_FLOW.USER_DETAIL_POLAROID_PHOTOS.getId());
    }

    private void setupViewPager(ViewPager viewPager) {
        if (adapter == null) {
            adapter = new ViewPagerAdapter(getChildFragmentManager());
            adapter.addFragment(UserProfileAboutFragment.createFragment(), getString(R.string.profile_tab_title_about));
            adapter.addFragment(UserProfileStatsFragment.createFragment(), getString(R.string.profile_tab_title_stats));
            adapter.addFragment(UserProfileGenresFragment.createFragment(), getString(R.string.profile_tab_title_genres));
            adapter.addFragment(UserProfileRatesFragment.createFragment(), getString(R.string.profile_tab_title_rates));
        }
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

    public void updateProfile(){
        AccountManager.getInstance()
                .getSelectedUserDetail(UserDetail.getInstance().getToken(),
                        new Callback<UserDetail>() {
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
                            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
                                if (response.isSuccessful()) {
                                    UserDetail.getInstance().setUserDetail(response.body());
                                    setCoverPhoto();
                                    setProfilePhoto();

                                    mProfileName.setText(Profile.getInstance().getFirstName()+Profile.getInstance().getLastName());
                                    mProfileType.setText(Profile.getInstance().getUserType());
                                    EventBus.getDefault().post(new EventManager(UserDetail.getInstance()));
                                } else {
                                    homeScreenActivity.logger.error(new Exception(response.message()));
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
                            public void onFailure(Call<UserDetail> call, Throwable t) {
                                homeScreenActivity.logger.error(t);
                            }
                        });
    }
}
