package com.castingmob.ui.fragment.profile;/*
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
import com.castingmob.account.model.Profile;
import com.castingmob.account.model.UserDetail;
import com.castingmob.event.EventManager;
import com.castingmob.ui.fragment.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.castingmob.utils.DetailUtility.getValue;

public class ProfileGenresFragment extends BaseFragment {

    private static ProfileGenresFragment profileGenresFragment;

    public static ProfileGenresFragment createFragment() {
        if (profileGenresFragment == null) {
            profileGenresFragment = new ProfileGenresFragment();
        }
        return profileGenresFragment;
    }

    @Bind(R.id.name_card)
    CardView mNamerCard;
    @Bind(R.id.waist_card)
    CardView mWaistCard;
    @Bind(R.id.hip_card)
    CardView mHipCard;
    @Bind(R.id.hair_color_card)
    CardView mHairColorCard;
    @Bind(R.id.city_card)
    CardView mCityCard;
    @Bind(R.id.country_card)
    CardView mCountryCard;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(ProfileGenresFragment.this);
    }

    @Subscribe
    public void onEvent(EventManager event){

        if (event.getObject() instanceof Profile)
            setCardValue();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_genres, container, false);
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
        setValue(mNamerCard, R.string.profile_stats_name_title, getValue(Profile.getInstance().getFirstName()));
        setValue(mWaistCard, R.string.profile_stats_waist_title, getValue(Profile.getInstance().getWaist()));
        setValue(mHipCard, R.string.profile_stats_hip_title, getValue(Profile.getInstance().getHip()));
        setValue(mHairColorCard, R.string.profile_stats_hair_color_title, getValue(Profile.getInstance().getHairColor()));
        setValue(mCityCard, R.string.profile_stats_city_title, getValue(Profile.getInstance().getCity()));
        setValue(mCountryCard, R.string.profile_stats_country_title, getValue(Profile.getInstance().getCountry()));
    }

    private void setValue(View view, @StringRes int stitle,  String svalue){
        TextView title = (TextView) view.findViewById(R.id.card_title);
        TextView value = (TextView) view.findViewById(R.id.card_value);

        title.setText(stitle);
        value.setText(svalue);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(ProfileGenresFragment.this);
    }
}
