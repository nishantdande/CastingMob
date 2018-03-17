package com.castingmob.ui.fragment.signup;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 15/09/16
 ==============================================
 */

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.castingmob.R;
import com.castingmob.account.AccountManager;
import com.castingmob.account.model.Profile;
import com.castingmob.event.EventManager;
import com.castingmob.ui.activity.OpeningScreenActivity;
import com.castingmob.ui.fragment.BaseFragment;
import com.castingmob.utils.Validation;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpInfoFragment extends BaseFragment {

    private static SignUpInfoFragment profileEditInfoFragment;

    public static SignUpInfoFragment createFragment() {
        if (profileEditInfoFragment == null) {
            profileEditInfoFragment = new SignUpInfoFragment();
        }
        return profileEditInfoFragment;
    }


    @Bind(R.id.first_name)
    EditText mFirstName;
    @Bind(R.id.last_name)
    EditText mLastName;
    @Bind(R.id.display_name)
    EditText mDisplayName;
    @Bind(R.id.dob)
    EditText mDateOfBirth;
    @Bind(R.id.rbtnMale)
    RadioButton mMale;
    @Bind(R.id.rbtnFemales)
    RadioButton mFemale;
    @Bind(R.id.rbtnOthers)
    RadioButton mOther;

    private SimpleDateFormat dateFormatter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_edit_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateTimeField();
            }
        });
        setCardValue();
    }

    private void setCardValue() {
        mFirstName.setText(Profile.getInstance().getFirstName());
        mLastName.setText(Profile.getInstance().getLastName());
        mDisplayName.setText(Profile.getInstance().getDisplayName());
        mDateOfBirth.setText(Profile.getInstance().getDateOfBirth());

        Profile.getInstance().setGender(Profile.Gender.male);
    }


    @OnClick(R.id.stats)
    public void stats(){
        EventBus.getDefault().post(new EventManager(ProfileUpdateContainerFragment.PROFILE_ACTIVITY.PROFILE_STATS));
    }

    @OnClick(R.id.done)
    public void done(){
        if (Validation.isEmpty(getFirstName())){
            mFirstName.setError(getString(R.string.field_blank_error_message));
            return;
        }

        if (Validation.isEmpty(getLastName())){
            mLastName.setError(getString(R.string.field_blank_error_message));
            return;
        }

        if (Validation.isEmpty(getmDisplayName())){
            mDisplayName.setError(getString(R.string.field_blank_error_message));
            return;
        }

        if (!TextUtils.isEmpty(getFirstName()))
            Profile.getInstance().setFirstName(getFirstName());

        if (!TextUtils.isEmpty(getLastName()))
            Profile.getInstance().setLastName(getLastName());

        if (!TextUtils.isEmpty(getmDisplayName()))
            Profile.getInstance().setDisplayName(getmDisplayName());

        if (!TextUtils.isEmpty(getmDateOfBirth()))
            Profile.getInstance().setDisplayName(getmDateOfBirth());

        AccountManager.getInstance().updateAccount(Profile.getInstance(), new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){

                    ((OpeningScreenActivity) getActivity())
                            .setFragment(OpeningScreenActivity.ACCOUNT_FLOW.SET_COVER_PHOTO, null);
                }else {
                    ((OpeningScreenActivity)getActivity()).logger.error(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ((OpeningScreenActivity) getActivity())
                        .setFragment(OpeningScreenActivity.ACCOUNT_FLOW.SET_COVER_PHOTO, null);
            }
        });


    }

    @OnClick(R.id.rbtnMale)
    public void onMaleClick(){
        Profile.getInstance().setGender(Profile.Gender.male);
    }

    @OnClick(R.id.rbtnFemales)
    public void onFemaleClick(){
        Profile.getInstance().setGender(Profile.Gender.female);
    }

    @OnClick(R.id.rbtnOthers)
    public void onBothClick(){
        Profile.getInstance().setGender(Profile.Gender.both);
    }

    private void setDateTimeField() {
        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog dobDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                Date current = newDate.getTime();
                int diff1 = new Date().compareTo(current);

                if (diff1 < 0) {
                    Toast.makeText(getActivity(), "Please select a valid date", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    mDateOfBirth.setText(dateFormatter.format(newDate.getTime()));
                }

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        dobDatePickerDialog.show();
    }

    public String getFirstName() {
        return mFirstName.getText().toString().trim();
    }

    public String getLastName() {
        return mLastName.getText().toString().trim();
    }

    public String getmDisplayName() {
        return mDisplayName.getText().toString().trim();
    }

    public String getmDateOfBirth() {
        return mDateOfBirth.getText().toString().trim();
    }
}
