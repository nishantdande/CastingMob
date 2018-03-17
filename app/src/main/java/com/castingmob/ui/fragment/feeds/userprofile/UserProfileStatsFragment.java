package com.castingmob.ui.fragment.feeds.userprofile;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 15/09/16
 ==============================================
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.castingmob.R;
import com.castingmob.account.model.UserDetail;
import com.castingmob.event.EventManager;
import com.castingmob.ui.fragment.BaseFragment;
import com.castingmob.ui.fragment.home.HomeScreenContainerFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.castingmob.utils.DetailUtility.getGender;
import static com.castingmob.utils.DetailUtility.getValue;

public class UserProfileStatsFragment extends BaseFragment {

    private static UserProfileStatsFragment userProfileStatsFragment;

    public static UserProfileStatsFragment createFragment() {
        if (userProfileStatsFragment == null) {
            userProfileStatsFragment = new UserProfileStatsFragment();
        }
        return userProfileStatsFragment;
    }

    @Bind(R.id.gender_card)
    CardView mGenderCard;
    @Bind(R.id.age_card)
    CardView mAgeCard;
    @Bind(R.id.height_card)
    CardView mHeightCard;
    @Bind(R.id.weight_card)
    CardView mWeightCard;
    @Bind(R.id.chest_card)
    CardView mChestCard;
    @Bind(R.id.eye_color_card)
    CardView mEyeColorCard;
    @Bind(R.id.ethinicity_card)
    CardView mEthinicityCard;
    @Bind(R.id.waist_card)
    CardView mWaistCard;
    @Bind(R.id.hip_card)
    CardView mHipCard;
    @Bind(R.id.hair_color_card)
    CardView mHairColorCard;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(UserProfileStatsFragment.this);
    }

    @Subscribe
    public void onEvent(EventManager event){

        if (event.getObject() instanceof UserDetail)
            setCardValue();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_stats, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();
        setCardValue();
    }


    private void setCardValue() {
        setValue(mGenderCard, R.string.profile_stats_gender_title, getGender(UserDetail.getInstance().getGender()));
        setValue(mAgeCard, R.string.profile_stats_age_title, getString(R.string.profile_blank));
        setValue(mHeightCard, R.string.profile_stats_height_title, getValue(UserDetail.getInstance().getHeight()));
        setValue(mWeightCard, R.string.profile_stats_weight_title, getValue(UserDetail.getInstance().getWeight()));
        setValue(mChestCard, R.string.profile_stats_chest_title, getValue(UserDetail.getInstance().getChest()));
        setValue(mEyeColorCard, R.string.profile_stats_eye_color_title, getValue(UserDetail.getInstance().getEyeColor()));
        setValue(mEthinicityCard, R.string.profile_stats_ethinicity_title, getValue(UserDetail.getInstance().getEthnicity()));
        setValue(mWaistCard, R.string.profile_stats_waist_title, getValue(UserDetail.getInstance().getWaist()));
        setValue(mHipCard, R.string.profile_stats_hip_title, getValue(UserDetail.getInstance().getHip()));
        setValue(mHairColorCard, R.string.profile_stats_hair_color_title, getValue(UserDetail.getInstance().getHairColor()));
    }

    private void setValue(View view, @StringRes int stitle,String svalue){
        TextView title = (TextView) view.findViewById(R.id.card_title);
        TextView value = (TextView) view.findViewById(R.id.card_value);

        title.setText(stitle);
        value.setText(svalue);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(UserProfileStatsFragment.this);
    }
}
