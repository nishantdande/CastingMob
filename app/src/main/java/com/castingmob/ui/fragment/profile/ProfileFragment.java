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
import com.castingmob.account.model.Account;
import com.castingmob.account.model.Profile;
import com.castingmob.account.model.UserDetail;
import com.castingmob.event.EventManager;
import com.castingmob.ui.activity.DetailActivity;
import com.castingmob.ui.activity.NewHomeScreenActivity;
import com.castingmob.ui.activity.Router;
import com.castingmob.ui.fragment.BaseFragment;
import com.cocosw.bottomsheet.BottomSheet;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends BaseFragment {

    private static ProfileFragment profileFragment;
    private NewHomeScreenActivity homeScreenActivity;

    public static ProfileFragment createFragment() {
        if (profileFragment == null) {
            profileFragment = new ProfileFragment();
        }
        return profileFragment;
    }


    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;

    @Bind(R.id.view_pager)
    ViewPager viewPager;

    @Bind(R.id.cover_photo)
    ImageView mCoverPhoto;

    @Bind(R.id.profile_photo)
    CircleImageView mProfilePhoto;

    @Bind(R.id.profile_name)
    TextView mProfileName;

    @Bind(R.id.profile_type)
    TextView mProfileType;

    private ViewPagerAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
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
                .load(Profile.getInstance().getCoverPhoto())
                .centerCrop()
                .placeholder(R.drawable.ic_logo_final_black)
                .crossFade()
                .into(mCoverPhoto);
    }

    private void setProfilePhoto() {
        Glide.with(CastingMob.getInstance().getContext())
                .load(Profile.getInstance().getProfilePhoto())
                .centerCrop()
                .placeholder(R.drawable.profile_icon)
                .dontAnimate()
                .into(mProfilePhoto);
    }

//    @OnClick(R.id.edit)
//    public void edit(){
//        EventBus.getDefault().post(new EventManager(ProfileContainerFragment.PROFILE_ACTIVITY.PROFILE_INFO));
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeScreenActivity = ((NewHomeScreenActivity) context);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
            updateUserProfile();
    }

    @OnClick(R.id.my_photos)
    public void myPhotos(){
        Bundle bundle = new Bundle();
        bundle.putInt("screenId", DetailActivity.DETAIL_FLOW.PHOTO_GALLERY.getId());
        Router.startDetailScreenActivity(getActivity(), bundle);
    }

    @OnClick(R.id.polaroid)
    public void myPolaroid(){
        Bundle bundle = new Bundle();
        bundle.putInt("screenId", DetailActivity.DETAIL_FLOW.POLAROID_PHOTO_GALLERY.getId());
        Router.startDetailScreenActivity(getActivity(), bundle);
    }

    @OnClick(R.id.upload_photos)
    public void uploadPhoto(){
        final Bundle bundle = new Bundle();
        new BottomSheet.Builder(getActivity()).title("Upload").sheet(R.menu.menu_upload)
                .listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case R.id.profile_photo:
                                bundle.putInt("screenId", DetailActivity.DETAIL_FLOW.PROFILE_PIC.getId());
                                Router.startDetailScreenActivity(getActivity(), bundle);
                                break;

                            case R.id.cover_photo:
                                bundle.putInt("screenId", DetailActivity.DETAIL_FLOW.COVER_PHOTO.getId());
                                Router.startDetailScreenActivity(getActivity(), bundle);
                                break;

                            case R.id.polaroid_photo:
                                bundle.putInt("screenId", DetailActivity.DETAIL_FLOW.POLAROID_PHOTO.getId());
                                Router.startDetailScreenActivity(getActivity(), bundle);
                                break;

                            case R.id.cancel:
                                break;
                        }
                    }
                }).show();

    }

    private void setupViewPager(ViewPager viewPager) {
        if (adapter == null) {
            adapter = new ViewPagerAdapter(getChildFragmentManager());
            adapter.addFragment(ProfileAboutFragment.createFragment(), getString(R.string.profile_tab_title_about));
            if (Profile.getInstance().getUserType().equalsIgnoreCase(String.valueOf(Account.subType.model))){
                adapter.addFragment(ProfileStatsFragment.createFragment(), getString(R.string.profile_tab_title_stats));
            }
            adapter.addFragment(ProfileInfoFragment.createFragment(), getString(R.string.profile_tab_title_info));
            adapter.addFragment(ProfileRatesFragment.createFragment(), getString(R.string.profile_tab_title_rates));
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

    protected void updateUserProfile(){
        AccountManager.getInstance()
                .getSelectedUserDetail(Profile.getInstance().getToken(),
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
                                    UserDetail userDetail = response.body();
                                    Profile.getInstance().setProfile(userDetail.getProfile());
                                    setCoverPhoto();
                                    setProfilePhoto();
                                    mProfileName.setText(Profile.getInstance().getFirstName()+Profile.getInstance().getLastName());
                                    mProfileType.setText(Profile.getInstance().getUserType());

                                    EventBus.getDefault().post(new EventManager(Profile.getInstance()));
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