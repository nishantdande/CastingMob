package com.castingmob.ui.fragment.signup;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 15/09/16
 ==============================================
 */

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.castingmob.R;
import com.castingmob.account.model.Profile;
import com.castingmob.event.EventManager;
import com.castingmob.ui.dialog.DialogFactory;
import com.castingmob.ui.fragment.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static com.castingmob.utils.DetailUtility.getValue;

public class SignUpEditStatsFragment extends BaseFragment {

    private static SignUpEditStatsFragment profileInfoFragment;

    public static SignUpEditStatsFragment createFragment() {
        if (profileInfoFragment == null) {
            profileInfoFragment = new SignUpEditStatsFragment();
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

    @Bind(R.id.height)
    LinearLayout mHeight;
    @Bind(R.id.weight)
    LinearLayout mWeight;
    @Bind(R.id.chest)
    LinearLayout mChest;
    @Bind(R.id.waist)
    LinearLayout mWaist;
    @Bind(R.id.hips)
    LinearLayout mHips;

    @Bind(R.id.hair)
    LinearLayout mHair;
    @Bind(R.id.eye)
    LinearLayout mEye;
    @Bind(R.id.ethinicity)
    LinearLayout mEthinicity;

    EditText mHairValue;
    EditText mEyeValue;
    EditText mEthinicityValue;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup_edit_stats, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);


        mHairValue = (EditText) mHair.findViewById(R.id.rate_values);
        final String[] hair_colors = getResources().getStringArray(R.array.hair_color);
        Profile.getInstance().setHairColor(hair_colors[0]);
        mHair.findViewById(R.id.rate_values).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFactory.createListDialog(getActivity(),
                        getString(R.string.dialog_hair_color_title), hair_colors ,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mHairValue.setText(hair_colors[which]);
                    }
                });
            }
        });

        final String[] eye_colors = getResources().getStringArray(R.array.eye_color);
        Profile.getInstance().setEyeColor(eye_colors[0]);
        mEyeValue = (EditText) mEye.findViewById(R.id.rate_values);
        mEye.findViewById(R.id.rate_values).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFactory.createListDialog(getActivity(),
                        getString(R.string.dialog_eye_color_title), eye_colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mEyeValue.setText(eye_colors[which]);
                    }
                });
            }
        });


        final String[] nationality = getResources().getStringArray(R.array.nationality);
        Profile.getInstance().setEthnicity(nationality[0]);
        mEthinicityValue = (EditText) mEthinicity.findViewById(R.id.rate_values);
        mEthinicity.findViewById(R.id.rate_values).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFactory.createListDialog(getActivity(), getString(R.string.dialog_nationality_title), nationality, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mEthinicityValue.setText(nationality[which]);
                    }
                });
            }
        });

        setCardValue();
    }

    @OnClick(R.id.info)
    public void stats(){
        saveValues();

        EventBus.getDefault().post(new EventManager(ProfileUpdateContainerFragment.PROFILE_ACTIVITY.PROFILE_INFO));
    }

    private void saveValues(){
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

        if (!TextUtils.isEmpty(getHeight()))
            Profile.getInstance().setHeight(Integer.valueOf(getHeight()));

        if (!TextUtils.isEmpty(getWeight()))
            Profile.getInstance().setWeight(Integer.valueOf(getWeight()));

        if (!TextUtils.isEmpty(getChest()))
            Profile.getInstance().setChest(Integer.valueOf(getChest()));

        if (!TextUtils.isEmpty(getWaist()))
            Profile.getInstance().setWaist(Integer.valueOf(getWaist()));

        if (!TextUtils.isEmpty(getHips()))
            Profile.getInstance().setHip(Integer.valueOf(getHips()));

        if (!TextUtils.isEmpty(getEye()))
            Profile.getInstance().setEyeColor(getEye());

        if (!TextUtils.isEmpty(getHair()))
            Profile.getInstance().setHairColor(getHair());

        if (!TextUtils.isEmpty(getEthinicity()))
            Profile.getInstance().setEthnicity(getEthinicity());
    }

    @OnClick(R.id.done)
    public void done(){
        saveValues();

        EventBus.getDefault().post(new EventManager(ProfileUpdateContainerFragment.PROFILE_ACTIVITY.PROFILE_INFO));
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
        setSizesValue(mHeight, R.string.profile_stats_height_title, getValue(Profile.getInstance().getHeight()),"cm");
        setSizesValue(mWeight, R.string.profile_stats_weight_title, getValue(Profile.getInstance().getWeight()),"lb");
        setSizesValue(mChest, R.string.profile_stats_chest_title, getValue(Profile.getInstance().getChest()),"cm");
        setSizesValue(mWaist, R.string.profile_stats_waist_title, getValue(Profile.getInstance().getWaist()),"cm");
        setSizesValue(mHips, R.string.profile_stats_hip_title, getValue(Profile.getInstance().getHip()),"cm");
        setFeatureValue(mHair, R.string.profile_stats_hair_color_title, getValue(Profile.getInstance().getHairColor()));
        setFeatureValue(mEye, R.string.profile_stats_eye_color_title, getValue(Profile.getInstance().getEyeColor()));
        setFeatureValue(mEthinicity, R.string.profile_stats_ethinicity_title, getValue(Profile.getInstance().getEthnicity()));
    }

    private void setValue(View view, @StringRes int stitle, String svalue){
        TextView title = (TextView) view.findViewById(R.id.rate_text);
        EditText value = (EditText) view.findViewById(R.id.rate_values);

        title.setText(stitle);
        if (!svalue.contains("---"))
            value.setText(svalue);
    }

    private void setSizesValue(View view, @StringRes int stitle, String svalue, String hintText){
        TextView title = (TextView) view.findViewById(R.id.rate_text);
        EditText value = (EditText) view.findViewById(R.id.rate_values);

        title.setText(stitle);
        value.setHint(hintText);
        if (!svalue.contains("---"))
            value.setText(svalue);
    }

    private void setFeatureValue(View view, @StringRes int stitle, String svalue){
        TextView title = (TextView) view.findViewById(R.id.rate_text);
        EditText value = (EditText) view.findViewById(R.id.rate_values);

        title.setText(stitle);
        value.setFocusable(false);
        value.setFocusableInTouchMode(false);
        value.setClickable(false);
        value.setCursorVisible(false);
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

    public String getHeight() {
        return getText(mHeight);
    }

    public String getWeight() {
        return getText(mWeight);
    }

    public String getChest() {
        return getText(mChest);
    }

    public String getWaist() {
        return getText(mWaist);
    }

    public String getHips() {
        return getText(mHips);
    }

    public String getHair() {
        return getText(mHair);
    }

    public String getEye() {
        return getText(mEye);
    }

    public String getEthinicity() {
        return getText(mEthinicity);
    }
}
