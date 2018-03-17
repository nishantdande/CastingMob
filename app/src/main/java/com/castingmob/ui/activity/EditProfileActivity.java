package com.castingmob.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.castingmob.R;
import com.castingmob.account.model.Profile;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static com.castingmob.utils.DetailUtility.getValue;


/**
 * Created by nishant on 19/09/16.
 */
public class EditProfileActivity extends CastingMobActivity {

    public static final int EDIT = 3000;

    int edit;

    @Bind(R.id.textViewOnly)
    EditText mTextViewOnly;

    @Bind(R.id.textEditView)
    RelativeLayout mTextEditView;

    @Bind(R.id.milesTextEditView)
    RelativeLayout mMilesTextViewOnly;

    @Bind(R.id.genderView)
    RelativeLayout mGenderView;

    @Bind(R.id.twoTextEditView)
    LinearLayout mTwoTextEditView;

    @Bind(R.id.maxRateView)
    RelativeLayout mMax;

    @Bind(R.id.minRateView)
    RelativeLayout mMin;

    @Bind(R.id.switchView)
    RelativeLayout mSwitchView;
    @Bind(R.id.rbtnMale)
    RadioButton mMale;
    @Bind(R.id.rbtnFemales)
    RadioButton mFemale;
    @Bind(R.id.rbtnOthers)
    RadioButton mOther;

    boolean mWillTravel;
    Profile.Gender gender;

    Bundle bundle = new Bundle();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            edit = extras.getInt("edit");
            setUi(edit);
        }
    }

    private void setUi(int edit) {
        switch (edit){
            case 1:
                mTextViewOnly.setVisibility(View.VISIBLE);
                break;
            case 2:
                mTextEditView.setVisibility(View.VISIBLE);
                EditText min = (EditText) mTextEditView.findViewById(R.id.textField);
                min.setText(getValue(Profile.getInstance().getFirstName()));
                break;

            case 3:
                mTwoTextEditView.setVisibility(View.VISIBLE);
                setUi(getString(R.string.edit_profile_stats_rates_min_hour_title),
                        getString(R.string.edit_profile_stats_rates_max_hour_title));
                setUiValue(getValue(Profile.getInstance().getMin_hourrate()),
                        getValue(Profile.getInstance().getMax_hourrate()));
                break;
            case 4:
                mTwoTextEditView.setVisibility(View.VISIBLE);
                setUi(getString(R.string.edit_profile_stats_rates_min_half_title),
                        getString(R.string.edit_profile_stats_rates_max_half_title));
                setUiValue(getValue(Profile.getInstance().getMin_halfdayrate()),
                        getValue(Profile.getInstance().getMax_halfdayrate()));
                break;
            case 5:
                mTwoTextEditView.setVisibility(View.VISIBLE);
                setUi(getString(R.string.edit_profile_stats_rates_min_full_title),
                        getString(R.string.edit_profile_stats_rates_max_full_title));
                setUiValue(getValue(Profile.getInstance().getMin_fulldayrate()),
                        getValue(Profile.getInstance().getMax_fulldayrate()));
                break;

            case 6:
                mSwitchView.setVisibility(View.VISIBLE);
                setSwitchValue(Profile.getInstance().getWill_travel());
                break;

            case 7:
                mMilesTextViewOnly.setVisibility(View.VISIBLE);
                TextView miles = (TextView) mMilesTextViewOnly.findViewById(R.id.textName);
                miles.setText(getString(R.string.profile_rates_miles_title));
                setTextEditViewUiValue(getValue(Profile.getInstance().getMax_travel()));
                break;

            case 8:
                mMilesTextViewOnly.setVisibility(View.VISIBLE);
                setTextEditViewUi(getString(R.string.profile_stats_weight_title));
                setTextEditViewUiValue(getValue(Profile.getInstance().getWeight()));
                break;

            case 9:
                mMilesTextViewOnly.setVisibility(View.VISIBLE);
                setTextEditViewUi(getString(R.string.profile_stats_height_title));
                setTextEditViewUiValue(getValue(Profile.getInstance().getHeight()));
                break;

            case 10:
                mMilesTextViewOnly.setVisibility(View.VISIBLE);
                setTextEditViewUi(getString(R.string.profile_stats_waist_title));
                setTextEditViewUiValue(getValue(Profile.getInstance().getWaist()));
                break;

            case 11:
                mMilesTextViewOnly.setVisibility(View.VISIBLE);
                setTextEditViewUi(getString(R.string.profile_stats_chest_title));
                setTextEditViewUiValue(getValue(Profile.getInstance().getChest()));
                break;

            case 12:
                mMilesTextViewOnly.setVisibility(View.VISIBLE);
                setTextEditViewUi(getString(R.string.profile_stats_hip_title));
                setTextEditViewUiValue(getValue(Profile.getInstance().getHip()));
                break;

            case 13:
                mGenderView.setVisibility(View.VISIBLE);
                ButterKnife.bind(mGenderView);

                if (Profile.getInstance().getGender() == Profile.Gender.male){
                    mMale.setChecked(true);
                    mFemale.setChecked(false);
                    mOther.setChecked(false);
                }

                if (Profile.getInstance().getGender() == Profile.Gender.female){
                    mMale.setChecked(false);
                    mFemale.setChecked(true);
                    mOther.setChecked(false);
                }

                if (Profile.getInstance().getGender() == Profile.Gender.both){
                    mMale.setChecked(false);
                    mFemale.setChecked(false);
                    mOther.setChecked(true);
                }
                break;
        }
    }

    @OnClick(R.id.rbtnMale)
    public void onMaleClick(){
        gender = Profile.Gender.male;
    }

    @OnClick(R.id.rbtnFemales)
    public void onFemaleClick(){
        gender = Profile.Gender.female;
    }

    @OnClick(R.id.rbtnOthers)
    public void onBothClick(){
        gender = Profile.Gender.both;
    }

    private void setTextEditViewUi(String title) {
        TextView min = (TextView) mMilesTextViewOnly.findViewById(R.id.textName);
        min.setText(title);
    }

    private void setTextEditViewUiValue(String value) {
        EditText min = (EditText) mMilesTextViewOnly.findViewById(R.id.textField);
        if (value.contains("---"))
            min.setText("0");
        else
            min.setText(value);
    }

    private void setUi(String minRate, String maxRate) {
        TextView min = (TextView) mMin.findViewById(R.id.textName);
        min.setText(minRate);
        TextView max = (TextView) mMax.findViewById(R.id.textName);
        max.setText(maxRate);
    }

    private void setUiValue(String minRateValue, String maxRateValue) {
        EditText min = (EditText) mMin.findViewById(R.id.textField);
        if (minRateValue.contains("---"))
            min.setText("0");
        else
            min.setText(minRateValue);
        EditText max = (EditText) mMax.findViewById(R.id.textField);
        if (maxRateValue.contains("---"))
            max.setText("0");
        else
            max.setText(maxRateValue);
    }

    private void setSwitchValue(boolean b){
        SwitchCompat switchCompat = (SwitchCompat) mSwitchView.findViewById(R.id.willTravelSwitch);
        switchCompat.setChecked(b);
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }

    @OnClick(R.id.navigateBack)
    public void navigateBack(){
        finish();
    }

    @OnClick(R.id.done)
    public void done(){
        Intent intent = new Intent(this,NewHomeScreenActivity.class);
        intent.putExtras(getBundle());
        setResult(EDIT,intent);
        finish();
    }

    private Bundle getBundle(){
        Bundle bundle = new Bundle();
        switch (edit){

            // About Value
            case 1:
                bundle.putString("aboutValue",TextUtils.isEmpty(mTextViewOnly.getText().toString().trim()) ? getString(R.string.profile_blank) :
                        mTextViewOnly.getText().toString().trim());
                return bundle;

            // Name Value
            case 2:
                TextView name = (TextView) mTextEditView.findViewById(R.id.textField);
                bundle.putString("nameValue",TextUtils.isEmpty(name.getText().toString().trim()) ? getString(R.string.profile_blank) :
                        name.getText().toString().trim());
                return bundle;

            // Hour Value
            case 3:
                TextView min = (TextView) mMin.findViewById(R.id.textField);
                TextView max = (TextView) mMax.findViewById(R.id.textField);

                bundle.putString("minHourValue",TextUtils.isEmpty(min.getText().toString().trim()) ? "0" :
                        min.getText().toString().trim());


                bundle.putString("maxHourValue",TextUtils.isEmpty(max.getText().toString().trim()) ? "0" :
                        max.getText().toString().trim());
                bundle.putInt("rates", 1);

                return bundle;

            // Half Value
            case 4:
                TextView minHalf = (TextView) mMin.findViewById(R.id.textField);
                TextView maxHalf = (TextView) mMax.findViewById(R.id.textField);

                bundle.putString("minHalfValue",TextUtils.isEmpty(minHalf.getText().toString().trim()) ? "0" :
                        minHalf.getText().toString().trim());


                bundle.putString("maxHalfValue",TextUtils.isEmpty(maxHalf.getText().toString().trim()) ? "0" :
                        maxHalf.getText().toString().trim());
                bundle.putInt("rates", 2);

                return bundle;

            // Full Value
            case 5:
                TextView minFull = (TextView) mMin.findViewById(R.id.textField);
                TextView maxFull = (TextView) mMax.findViewById(R.id.textField);

                bundle.putString("minFullValue",TextUtils.isEmpty(minFull.getText().toString().trim()) ? "0" :
                        minFull.getText().toString().trim());


                bundle.putString("maxFullValue",TextUtils.isEmpty(maxFull.getText().toString().trim()) ? "0" :
                        maxFull.getText().toString().trim());
                bundle.putInt("rates", 3);

                return bundle;

            // Will Travel Value
            case 6:
                bundle.putBoolean("willTravel", mWillTravel);
                bundle.putInt("rates", 4);
                return bundle;

            // Miles Value
            case 7:
                TextView miles = (TextView) mMilesTextViewOnly.findViewById(R.id.textField);
                bundle.putString("milesValue",TextUtils.isEmpty(miles.getText().toString().trim())
                        ? getString(R.string.profile_blank) :
                        miles.getText().toString().trim());
                bundle.putInt("rates", 5);
                return bundle;

            // Weight Value
            case 8:
                TextView weight = (TextView) mMilesTextViewOnly.findViewById(R.id.textField);
                bundle.putString("weightValue",TextUtils.isEmpty(weight.getText().toString().trim())
                        ? getString(R.string.profile_blank) :
                        weight.getText().toString().trim());
                bundle.putInt("rates", 8);
                return bundle;

            // Height Value
            case 9:
                TextView height = (TextView) mMilesTextViewOnly.findViewById(R.id.textField);
                bundle.putString("heightValue",TextUtils.isEmpty(height.getText().toString().trim())
                        ? getString(R.string.profile_blank) :
                        height.getText().toString().trim());
                bundle.putInt("rates", 9);
                return bundle;

            // Waist Value
            case 10:
                TextView waist = (TextView) mMilesTextViewOnly.findViewById(R.id.textField);
                bundle.putString("waistValue",TextUtils.isEmpty(waist.getText().toString().trim())
                        ? getString(R.string.profile_blank) :
                        waist.getText().toString().trim());
                bundle.putInt("rates", 10);
                return bundle;

            // Chest Value
            case 11:
                TextView chest = (TextView) mMilesTextViewOnly.findViewById(R.id.textField);
                bundle.putString("chestValue",TextUtils.isEmpty(chest.getText().toString().trim())
                        ? getString(R.string.profile_blank) :
                        chest.getText().toString().trim());
                bundle.putInt("rates", 11);
                return bundle;

            // Hip Value
            case 12:
                TextView hip = (TextView) mMilesTextViewOnly.findViewById(R.id.textField);
                bundle.putString("hipValue",TextUtils.isEmpty(hip.getText().toString().trim())
                        ? getString(R.string.profile_blank) :
                        hip.getText().toString().trim());
                bundle.putInt("rates", 12);
                return bundle;

            // Gender Value
            case 13:

                bundle.putString("genderValue", String.valueOf(gender));
                bundle.putInt("rates", 13);
                return bundle;

            default:
                return null;

        }
    }

    @OnCheckedChanged(R.id.willTravelSwitch)
    public void willTravel(boolean ischecked){
        this.mWillTravel = ischecked;
    }
}
