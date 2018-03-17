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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.castingmob.R;
import com.castingmob.account.AccountManager;
import com.castingmob.account.model.Profile;
import com.castingmob.event.EventManager;
import com.castingmob.ui.activity.EditProfileActivity;
import com.castingmob.ui.activity.LocationFilterActivity;
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

public class ProfileInfoFragment extends BaseFragment {

    private static ProfileInfoFragment profileStatsFragment;

    public static ProfileInfoFragment createFragment() {
        if (profileStatsFragment == null) {
            profileStatsFragment = new ProfileInfoFragment();
        }
        return profileStatsFragment;
    }

    @Bind(R.id.name_card)
    CardView mNameCard;
    @Bind(R.id.city_card)
    CardView mCityCard;
    @Bind(R.id.country_card)
    CardView mCountryCard;

    TextView mName;
    TextView mCity;
    TextView mCountry;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(ProfileInfoFragment.this);
    }

    @Subscribe
    public void onEvent(EventManager event){

        if (event.getObject() instanceof Profile)
            setCardValue();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mName = (TextView) mNameCard.findViewById(R.id.card_value);
        mCity = (TextView) mCityCard.findViewById(R.id.card_value);
        mCountry = (TextView) mCountryCard.findViewById(R.id.card_value);
        mNameCard.findViewById(R.id.card_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("edit", 2); // Name == 1
                Intent intent= new Intent(getActivity(),EditProfileActivity.class);
                if (bundle != null)
                    intent.putExtras(bundle);
                startActivityForResult(intent, 2000);
            }
        });

        mCityCard.findViewById(R.id.card_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(),LocationFilterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tab", "info");
                bundle.putString("city", mCity.getText().toString());
                bundle.putString("country", mCountry.getText().toString());
                if (bundle != null)
                    intent.putExtras(bundle);
                startActivityForResult(intent, 2001);
            }
        });


        mCountryCard.findViewById(R.id.card_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(),LocationFilterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("tab", "info");
                bundle.putString("city", mCity.getText().toString());
                bundle.putString("country", mCountry.getText().toString());
                if (bundle != null)
                    intent.putExtras(bundle);
                startActivityForResult(intent, 2001);
            }
        });

        setCardValue();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == EDIT){
            Bundle bundle = data.getExtras();
            if (bundle != null){
                mName.setText(bundle.getString("nameValue", getString(R.string.profile_blank)));

                Profile.getInstance().setFirstName(bundle.getString("nameValue"));
                updateProfile();
            }
        }

        if(resultCode == 202){
            Bundle bundle = data.getExtras();
            if (bundle != null){
                mCity.setText(TextUtils.isEmpty(bundle.getString("city")) ? getString(R.string.profile_blank) :
                        bundle.getString("city"));
                mCountry.setText(TextUtils.isEmpty(bundle.getString("country")) ? getString(R.string.profile_blank) :
                        bundle.getString("country"));
                Profile.getInstance().setCity(mCity.getText().toString());
                Profile.getInstance().setCountry(mCountry.getText().toString());
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
        setValue(mNameCard, R.string.profile_stats_name_title, getValue(Profile.getInstance().getFirstName()));
        setValue(mCityCard, R.string.profile_stats_city_title, getValue(Profile.getInstance().getCity()));
        setValue(mCountryCard, R.string.profile_stats_country_title, getValue(Profile.getInstance().getCountry()));
    }

    private void setValue(View view, @StringRes int stitle,String svalue){
        TextView title = (TextView) view.findViewById(R.id.card_title);
        TextView value = (TextView) view.findViewById(R.id.card_value);

        title.setText(stitle);
        value.setText(svalue);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(ProfileInfoFragment.this);
    }
}
