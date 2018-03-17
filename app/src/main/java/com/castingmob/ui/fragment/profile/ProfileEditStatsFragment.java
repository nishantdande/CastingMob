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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.castingmob.R;
import com.castingmob.account.AccountManager;
import com.castingmob.account.model.Profile;
import com.castingmob.event.EventManager;
import com.castingmob.ui.activity.NewHomeScreenActivity;
import com.castingmob.ui.fragment.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.castingmob.utils.DetailUtility.getValue;

public class ProfileEditStatsFragment extends BaseFragment {

    private static ProfileEditStatsFragment profileInfoFragment;

    public static ProfileEditStatsFragment createFragment() {
        if (profileInfoFragment == null) {
            profileInfoFragment = new ProfileEditStatsFragment();
        }
        return profileInfoFragment;
    }


    @Bind(R.id.min_hour_rates)
    LinearLayout mMinHourRate;
    @Bind(R.id.min_half_day_rates)
    LinearLayout mMinHalfDayRate;
    @Bind(R.id.min_full_day_rates)
    LinearLayout mMinFullDayRate;

    @Bind(R.id.max_half_rates)
    LinearLayout mMaxHourRate;
    @Bind(R.id.max_half_day_rates)
    LinearLayout mMaxHalfDayRate;
    @Bind(R.id.max_full_day_rates)
    LinearLayout mMaxFullDayRate;
    @Bind(R.id.distance_travel)
    LinearLayout mDistanceTravels;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_edit_stats, container, false);
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
            setCardValue();
    }

    @OnClick(R.id.info)
    public void stats(){
        if (!TextUtils.isEmpty(getMinHourRate()))
            Profile.getInstance().setMin_hourrate(getMinHourRate());

        if (!TextUtils.isEmpty(getMaxHourRate()))
            Profile.getInstance().setMax_hourrate(getMaxHourRate());

        if (!TextUtils.isEmpty(getMinHalfDayRate()))
            Profile.getInstance().setMin_halfdayrate(getMinHalfDayRate());

        if (!TextUtils.isEmpty(getMaxHalfDayRate()))
            Profile.getInstance().setMax_halfdayrate(getMaxHalfDayRate());

        if (!TextUtils.isEmpty(getMinFullDayRate()))
            Profile.getInstance().setMin_fulldayrate(getMinFullDayRate());

        if (!TextUtils.isEmpty(getMaxFullDayRate()))
            Profile.getInstance().setMax_fulldayrate(getMaxFullDayRate());

        if (!TextUtils.isEmpty(getDistanceTravel()))
            Profile.getInstance().setMax_travel(getDistanceTravel());

        EventBus.getDefault().post(new EventManager(ProfileContainerFragment.PROFILE_ACTIVITY.PROFILE_INFO));
    }

    @OnClick(R.id.done)
    public void done(){
        if (!TextUtils.isEmpty(getMinHourRate()))
            Profile.getInstance().setMin_hourrate(getMinHourRate());

        if (!TextUtils.isEmpty(getMaxHourRate()))
            Profile.getInstance().setMax_hourrate(getMaxHourRate());

        if (!TextUtils.isEmpty(getMinHalfDayRate()))
            Profile.getInstance().setMin_halfdayrate(getMinHalfDayRate());

        if (!TextUtils.isEmpty(getMaxHalfDayRate()))
            Profile.getInstance().setMax_halfdayrate(getMaxHalfDayRate());

        if (!TextUtils.isEmpty(getMinFullDayRate()))
            Profile.getInstance().setMin_fulldayrate(getMinFullDayRate());

        if (!TextUtils.isEmpty(getMaxFullDayRate()))
            Profile.getInstance().setMax_fulldayrate(getMaxFullDayRate());

        if (!TextUtils.isEmpty(getDistanceTravel()))
            Profile.getInstance().setMax_travel(getDistanceTravel());

        AccountManager.getInstance().updateAccount(Profile.getInstance(), new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    EventBus.getDefault().post(new EventManager(ProfileContainerFragment.PROFILE_ACTIVITY.PROFILE));
                }else {
                    ((NewHomeScreenActivity)getActivity()).logger.error(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ((NewHomeScreenActivity)getActivity()).logger.error(t);
            }
        });
    }

    @OnCheckedChanged(R.id.travelValue)
    public void willTravel(boolean isChecked){
        Profile.getInstance().setWill_travel(isChecked);
    }

    @OnCheckedChanged(R.id.signedValue)
    public void signed(boolean isChecked){
        Profile.getInstance().setPublished(isChecked);
    }

    private void setCardValue() {
        setValue(mMinHourRate, R.string.profile_stats_rates_min_hour_title, getValue(Profile.getInstance().getMin_hourrate()));
        setValue(mMaxHourRate, R.string.profile_stats_rates_max_hour_title, getValue(Profile.getInstance().getMax_hourrate()));
        setValue(mMinHalfDayRate, R.string.profile_stats_rates_min_half_title, getValue(Profile.getInstance().getMin_halfdayrate()));
        setValue(mMaxHalfDayRate, R.string.profile_stats_rates_max_half_title, getValue(Profile.getInstance().getMax_halfdayrate()));
        setValue(mMinFullDayRate, R.string.profile_stats_rates_min_full_title, getValue(Profile.getInstance().getMin_fulldayrate()));
        setValue(mMaxFullDayRate, R.string.profile_stats_rates_max_full_title, getValue(Profile.getInstance().getMax_fulldayrate()));
        setValue(mDistanceTravels, R.string.profile_stats_travel_distance, getValue(Profile.getInstance().getMax_travel()));
    }

    private void setValue(View view, @StringRes int stitle, String svalue){
        TextView title = (TextView) view.findViewById(R.id.rate_text);
        EditText value = (EditText) view.findViewById(R.id.rate_values);

        title.setText(stitle);
        if (!svalue.contains("---"))
            value.setText(svalue);
    }

    private String getText(LinearLayout cardView){
        return ((EditText) cardView.findViewById(R.id.rate_values)).getText().toString().trim();
    }

    public String getMinHourRate() {
        return getText(mMinHourRate);
    }

    public String getMinHalfDayRate() {
        return getText(mMinHalfDayRate);
    }

    public String getMinFullDayRate() {
        return getText(mMinFullDayRate);
    }

    public String getMaxHourRate() {
        return getText(mMaxHourRate);
    }

    public String getMaxHalfDayRate() {
        return getText(mMaxHalfDayRate);
    }

    public String getMaxFullDayRate() {
        return getText(mMaxFullDayRate);
    }

    public String getDistanceTravel() {
        return getText(mDistanceTravels);
    }
}
