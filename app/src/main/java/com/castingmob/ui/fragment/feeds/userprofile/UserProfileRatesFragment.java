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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.castingmob.utils.DetailUtility.getValue;

public class UserProfileRatesFragment extends BaseFragment {

    private static UserProfileRatesFragment profileGenresFragment;

    public static UserProfileRatesFragment createFragment() {
        if (profileGenresFragment == null) {
            profileGenresFragment = new UserProfileRatesFragment();
        }
        return profileGenresFragment;
    }

    @Bind(R.id.hourly_card)
    CardView mHourlyCard;
    @Bind(R.id.half_day_card)
    CardView mHalfDayCard;
    @Bind(R.id.fullday__card)
    CardView mFullDayCard;
    @Bind(R.id.miles_card)
    CardView mMilesCard;
    @Bind(R.id.will_travel_card)
    CardView mWillTravelCard;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(UserProfileRatesFragment.this);
    }

    @Subscribe
    public void onEvent(EventManager event){

        if (event.getObject() instanceof UserDetail)
            setCardValue();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_rates, container, false);
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
        setValue(mHourlyCard, R.string.profile_rates_hourly_title, getValue(UserDetail.getInstance().getMin_hourrate(),
                UserDetail.getInstance().getMax_hourrate()));
        setValue(mHalfDayCard, R.string.profile_rates_half_day_title,  getValue(UserDetail.getInstance().getMin_halfdayrate(),
                UserDetail.getInstance().getMax_halfdayrate()));
        setValue(mFullDayCard, R.string.profile_rates_full_day_title,  getValue(UserDetail.getInstance().getMin_fulldayrate(),
                UserDetail.getInstance().getMax_fulldayrate()));
        setValue(mMilesCard, R.string.profile_rates_miles_title, getValue(UserDetail.getInstance().getMax_travel()));
        setValue(mWillTravelCard, R.string.profile_rates_wiil_travel_title, getValue(UserDetail.getInstance().getWill_travel()));
    }

    private void setValue(View view, @StringRes int stitle, String svalue){
        TextView title = (TextView) view.findViewById(R.id.card_title);
        TextView value = (TextView) view.findViewById(R.id.card_value);

        title.setText(stitle);
        value.setText(svalue);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(UserProfileRatesFragment.this);
    }

}
