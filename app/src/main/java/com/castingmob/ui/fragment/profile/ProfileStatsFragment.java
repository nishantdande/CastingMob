package com.castingmob.ui.fragment.profile;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 15/09/16
 ==============================================
 */

import android.content.DialogInterface;
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
import com.castingmob.ui.dialog.DialogFactory;
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

public class ProfileStatsFragment extends BaseFragment {

    private static ProfileStatsFragment profileStatsFragment;
    private TextView mGenderText;
    private TextView mWeightText;
    private TextView mHeightText;
    private TextView mHipText;
    private TextView mWaistText;
    private TextView mChestText;
    private TextView mEyeText;
    private TextView mHairText;
    private TextView mEthinicityText;

    public static ProfileStatsFragment createFragment() {
        if (profileStatsFragment == null) {
            profileStatsFragment = new ProfileStatsFragment();
        }
        return profileStatsFragment;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(ProfileStatsFragment.this);
    }

    @Subscribe
    public void onEvent(EventManager event){

        if (event.getObject() instanceof Profile)
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
        setCardValue();

        mWeightText = (TextView) mWeightCard.findViewById(R.id.card_value);
        mWeightCard.findViewById(R.id.card_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("edit", 8); // Gender == 8
                Intent intent= new Intent(getActivity(),EditProfileActivity.class);
                if (bundle != null)
                    intent.putExtras(bundle);
                startActivityForResult(intent, 2005);
            }
        });

        mHeightText = (TextView) mHeightCard.findViewById(R.id.card_value);
        mHeightCard.findViewById(R.id.card_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("edit", 9); // height == 9
                Intent intent= new Intent(getActivity(),EditProfileActivity.class);
                if (bundle != null)
                    intent.putExtras(bundle);
                startActivityForResult(intent, 2005);
            }
        });

        mWaistText = (TextView) mWaistCard.findViewById(R.id.card_value);
        mWaistCard.findViewById(R.id.card_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("edit", 10); // Waist == 10
                Intent intent= new Intent(getActivity(),EditProfileActivity.class);
                if (bundle != null)
                    intent.putExtras(bundle);
                startActivityForResult(intent, 2005);
            }
        });

        mChestText = (TextView) mChestCard.findViewById(R.id.card_value);
        mChestCard.findViewById(R.id.card_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("edit", 11); // Chest == 11
                Intent intent= new Intent(getActivity(),EditProfileActivity.class);
                if (bundle != null)
                    intent.putExtras(bundle);
                startActivityForResult(intent, 2005);
            }
        });

        mHipText = (TextView) mHipCard.findViewById(R.id.card_value);
        mHipCard.findViewById(R.id.card_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("edit", 12); // Hip == 12
                Intent intent= new Intent(getActivity(),EditProfileActivity.class);
                if (bundle != null)
                    intent.putExtras(bundle);
                startActivityForResult(intent, 2005);
            }
        });

        mEyeText = (TextView) mEyeColorCard.findViewById(R.id.card_value);
        mEyeColorCard.findViewById(R.id.card_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] eye_colors = getResources().getStringArray(R.array.eye_color);
                DialogFactory.createListDialog(getActivity(), getString(R.string.dialog_eye_color_title), eye_colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mEyeText.setText(eye_colors[which]);
                        Profile.getInstance().setEyeColor(eye_colors[which]);
                        updateProfile();
                    }
                });
            }
        });

        mHairText = (TextView) mHairColorCard.findViewById(R.id.card_value);
        mHairColorCard.findViewById(R.id.card_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] hair_colors = getResources().getStringArray(R.array.hair_color);
                DialogFactory.createListDialog(getActivity(), getString(R.string.dialog_hair_color_title), hair_colors , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mHairText.setText(hair_colors[which]);
                        Profile.getInstance().setHairColor(hair_colors[which]);
                        updateProfile();
                    }
                });
            }
        });

        mEthinicityText = (TextView) mEthinicityCard.findViewById(R.id.card_value);
        mEthinicityCard.findViewById(R.id.card_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] nationality = getResources().getStringArray(R.array.nationality);
                DialogFactory.createListDialog(getActivity(), getString(R.string.dialog_nationality_title), nationality, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mEthinicityText.setText(nationality[which]);
                        Profile.getInstance().setEthnicity(nationality[which]);
                        updateProfile();
                    }
                });
            }
        });

        mGenderText = (TextView) mGenderCard.findViewById(R.id.card_value);
        mGenderCard.findViewById(R.id.card_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("edit", 13); // gender == 12
                Intent intent= new Intent(getActivity(),EditProfileActivity.class);
                if (bundle != null)
                    intent.putExtras(bundle);
                startActivityForResult(intent, 2005);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == EDIT){
            Bundle bundle = data.getExtras();
            if (bundle != null){
                switch (bundle.getInt("rates")) {
                    case 8 :
                        Profile.getInstance().setWeight(Integer.valueOf(bundle.getString("weightValue")));
                        mWeightText.setText(getValue(Profile.getInstance().getWeight()));
                        break;

                    case 9 :
                        Profile.getInstance().setHeight(Integer.valueOf(bundle.getString("heightValue")));
                        mHeightText.setText(getValue(Profile.getInstance().getHeight()));
                        break;

                    case 10 :
                        Profile.getInstance().setWaist(Integer.valueOf(bundle.getString("waistValue")));
                        mWaistText.setText(getValue(Profile.getInstance().getWaist()));
                        break;

                    case 11 :
                        Profile.getInstance().setChest(Integer.valueOf(bundle.getString("chestValue")));
                        mChestText.setText(getValue(Profile.getInstance().getChest()));
                        break;

                    case 12 :
                        Profile.getInstance().setHip(Integer.valueOf(bundle.getString("hipValue")));
                        mHipText.setText(getValue(Profile.getInstance().getHip()));
                        break;

                    case 13 :
                        Profile.getInstance().setGender(Profile.getInstance().getGender(bundle.getString("genderValue")));
                        mGenderText.setText(Profile.getInstance().getGender(Profile.getInstance().getGender()));
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
        setValue(mGenderCard, R.string.profile_stats_gender_title, Profile.getInstance().getGender(Profile.getInstance().getGender()));
        setValue(mAgeCard, R.string.profile_stats_age_title, getString(R.string.profile_blank));
        setValue(mHeightCard, R.string.profile_stats_height_title, getValue(Profile.getInstance().getHeight()));
        setValue(mWeightCard, R.string.profile_stats_weight_title, getValue(Profile.getInstance().getWeight()));
        setValue(mChestCard, R.string.profile_stats_chest_title, getValue(Profile.getInstance().getChest()));
        setValue(mEyeColorCard, R.string.profile_stats_eye_color_title, getValue(Profile.getInstance().getEyeColor()));
        setValue(mEthinicityCard, R.string.profile_stats_ethinicity_title, getValue(Profile.getInstance().getEthnicity()));
        setValue(mWaistCard, R.string.profile_stats_waist_title, getValue(Profile.getInstance().getWaist()));
        setValue(mHipCard, R.string.profile_stats_hip_title, getValue(Profile.getInstance().getHip()));
        setValue(mHairColorCard, R.string.profile_stats_hair_color_title, getValue(Profile.getInstance().getHairColor()));
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
        EventBus.getDefault().unregister(ProfileStatsFragment.this);
    }
}
