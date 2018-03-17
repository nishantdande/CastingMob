package com.castingmob.ui.fragment.profile;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 15/09/16
 ==============================================
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.castingmob.R;
import com.castingmob.account.AccountManager;
import com.castingmob.account.model.Profile;
import com.castingmob.event.EventManager;
import com.castingmob.ui.activity.EditProfileActivity;
import com.castingmob.ui.fragment.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.castingmob.ui.activity.EditProfileActivity.EDIT;
import static com.castingmob.utils.DetailUtility.getValue;

public class ProfileRatesFragment extends BaseFragment {

    private static ProfileRatesFragment profileGenresFragment;

    public static ProfileRatesFragment createFragment() {
        if (profileGenresFragment == null) {
            profileGenresFragment = new ProfileRatesFragment();
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

    TextView mHourlyText;
    TextView mHalfText;
    TextView mFullText;
    TextView mWillTravelText;
    TextView mMiles;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(ProfileRatesFragment.this);
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
        setCardValue();

        mHourlyText = (TextView) mHourlyCard.findViewById(R.id.card_value);
        mHourlyCard.findViewById(R.id.card_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("edit", 3); // Hour == 1
                Intent intent= new Intent(getActivity(),EditProfileActivity.class);
                if (bundle != null)
                    intent.putExtras(bundle);
                startActivityForResult(intent, 2004);
            }
        });

        mHalfText = (TextView) mHalfDayCard.findViewById(R.id.card_value);
        mHalfDayCard.findViewById(R.id.card_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("edit", 4); // Half == 1
                Intent intent= new Intent(getActivity(),EditProfileActivity.class);
                if (bundle != null)
                    intent.putExtras(bundle);
                startActivityForResult(intent, 2004);
            }
        });

        mFullText = (TextView) mFullDayCard.findViewById(R.id.card_value);
        mFullDayCard.findViewById(R.id.card_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("edit", 5); // Full == 1
                Intent intent= new Intent(getActivity(),EditProfileActivity.class);
                if (bundle != null)
                    intent.putExtras(bundle);
                startActivityForResult(intent, 2004);
            }
        });

        mWillTravelText = (TextView) mWillTravelCard.findViewById(R.id.card_value);
        mWillTravelCard.findViewById(R.id.card_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("edit", 6); // Full == 1
                Intent intent= new Intent(getActivity(),EditProfileActivity.class);
                if (bundle != null)
                    intent.putExtras(bundle);
                startActivityForResult(intent, 2004);
            }
        });

        mMiles = (TextView) mMilesCard.findViewById(R.id.card_value);
        mMilesCard.findViewById(R.id.card_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("edit", 7); // Full == 1
                Intent intent= new Intent(getActivity(),EditProfileActivity.class);
                if (bundle != null)
                    intent.putExtras(bundle);
                startActivityForResult(intent, 2004);
            }
        });
    }


    @Subscribe
    public void onEvent(EventManager event){

        if (event.getObject() instanceof Profile)
            setCardValue();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == EDIT){
            Bundle bundle = data.getExtras();
            if (bundle != null){
                switch (bundle.getInt("rates")) {
                    case 1 :
                        Profile.getInstance().setMin_hourrate(bundle.getString("minHourValue"));
                        Profile.getInstance().setMax_hourrate(bundle.getString("maxHourValue"));
                        mHourlyText.setText(getValue(Profile.getInstance().getMin_hourrate(),
                                Profile.getInstance().getMax_hourrate()));
                        break;

                    case 2:

                        Profile.getInstance().setMin_halfdayrate(bundle.getString("minHalfValue"));
                        Profile.getInstance().setMax_halfdayrate(bundle.getString("maxHalfValue"));
                        mHalfText.setText(getValue(Profile.getInstance().getMin_halfdayrate(),
                                Profile.getInstance().getMax_halfdayrate()));

                        break;

                    case 3:

                        Profile.getInstance().setMin_fulldayrate(bundle.getString("minFullValue"));
                        Profile.getInstance().setMax_fulldayrate(bundle.getString("maxFullValue"));
                        mFullText.setText(getValue(Profile.getInstance().getMin_fulldayrate(),
                                Profile.getInstance().getMax_fulldayrate()));

                        break;

                    case 4:

                        Profile.getInstance().setWill_travel(bundle.getBoolean("willTravel"));
                        mWillTravelText.setText(getValue(Profile.getInstance().getWill_travel()));

                        break;

                    case 5:

                        Profile.getInstance().setMax_travel(bundle.getString("milesValue"));
                        mMiles.setText(getValue(Profile.getInstance().getMax_travel()));

                        break;
                }

                updateProfile();
            }
        }
    }

    private void updateProfile(){
        AccountManager.getInstance().updateAccount(Profile.getInstance(), new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful())
                    logger.error(new Exception(response.raw()+""));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                logger.error(t);
            }
        });
    }


    private void setCardValue() {
        setValue(mHourlyCard, R.string.profile_rates_hourly_title, getValue(Profile.getInstance().getMin_hourrate(),
                Profile.getInstance().getMax_hourrate()));
        setValue(mHalfDayCard, R.string.profile_rates_half_day_title,  getValue(Profile.getInstance().getMin_halfdayrate(),
                Profile.getInstance().getMax_halfdayrate()));
        setValue(mFullDayCard, R.string.profile_rates_full_day_title,  getValue(Profile.getInstance().getMin_fulldayrate(),
                Profile.getInstance().getMax_fulldayrate()));
        setValue(mMilesCard, R.string.profile_rates_miles_title, getValue(Profile.getInstance().getMax_travel()));
        setValue(mWillTravelCard, R.string.profile_rates_wiil_travel_title, getValue(Profile.getInstance().getWill_travel()));
    }

    private void setValue(View view, @StringRes int stitle, String svalue){
        TextView title = (TextView) view.findViewById(R.id.card_title);
        TextView value = (TextView) view.findViewById(R.id.card_value);

        title.setText(stitle);
        value.setText(svalue);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(ProfileRatesFragment.this);
    }

}
