package com.castingmob.ui.fragment;
/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 17/01/16
 ==============================================
 */

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.castingmob.ui.activity.OpeningScreenActivity;
import com.castingmob.ui.dialog.DialogFactory;
import com.castingmob.ui.view.ProgressView;
import com.castingmob.utils.Validation;

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

public class UpdateProfileScreenFragment extends BaseFragment {

    @Bind(R.id.first_name)
    EditText mFirstName;
    @Bind(R.id.last_name)
    EditText mLastName;
    @Bind(R.id.display_name)
    EditText mDisplayName;
    @Bind(R.id.dob)
    EditText mDateOfBirth;
    @Bind(R.id.city)
    EditText mCity;
    @Bind(R.id.country)
    EditText mCountry;
    @Bind(R.id.rbtnMale)
    RadioButton mMale;
    @Bind(R.id.rbtnFemales)
    RadioButton mFemale;

    private OpeningScreenActivity openingScreenActivity;
    private ProgressView progressView;
    private SimpleDateFormat dateFormatter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_profile_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        openingScreenActivity = ((OpeningScreenActivity) getActivity());
        progressView = DialogFactory.createProgressDialog(openingScreenActivity, "Loading...");
    }

    @OnClick(R.id.done)
    public void doneClick(){
        if (openingScreenActivity != null){

            if (Validation.isEmpty(getFirstName())){
                mFirstName.setError(openingScreenActivity.getString(R.string.field_blank_error_message));
                return;
            }

            if (Validation.isEmpty(getLastName())){
                mLastName.setError(openingScreenActivity.getString(R.string.field_blank_error_message));
                return;
            }

            if (Validation.isEmpty(getDisplayName())){
                mDisplayName.setError(openingScreenActivity.getString(R.string.field_blank_error_message));
                return;
            }

            if (Validation.isEmpty(getDateOfBirth())){
                mDateOfBirth.setError(openingScreenActivity.getString(R.string.field_blank_error_message));
                return;
            }

            if (Validation.isEmpty(getCity())){
                mCity.setError(openingScreenActivity.getString(R.string.field_blank_error_message));
                return;
            }

            if (Validation.isEmpty(getCountry())){
                mCountry.setError(openingScreenActivity.getString(R.string.field_blank_error_message));
                return;
            }

            Profile.getInstance().setFirstName(getFirstName());
            Profile.getInstance().setLastName(getLastName());
            Profile.getInstance().setDisplayName(getDisplayName());
            Profile.getInstance().setFirstName(getDisplayName());
            Profile.getInstance().setCity(getCity());
            Profile.getInstance().setCountry(getCountry());
            Profile.getInstance().setDateOfBirth(getDateOfBirth());
            Profile.getInstance().setGender(mFemale.isChecked() ? Profile.Gender.female : Profile.Gender.male);

            openingScreenActivity.logger.debug(Profile.getInstance().toString());

            AccountManager.getInstance().updateAccount(Profile.getInstance(), new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful())
                    {
                        ((OpeningScreenActivity) getActivity())
                                .setFragment(OpeningScreenActivity.ACCOUNT_FLOW.SET_STYLE, null);
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    ((OpeningScreenActivity) getActivity())
                            .setFragment(OpeningScreenActivity.ACCOUNT_FLOW.SET_STYLE, null);
                }
            });


        }else{
            // TODO - Handle the Error call for activity null
        }
    }


    @OnClick(R.id.dob)
    public void onDateOfBirthClick(){
        setDateTimeField();
    }

    private void setDateTimeField() {
        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US); //dd/MM/yyyy

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

    public String getDisplayName() {
        return mDisplayName.getText().toString().trim();
    }

    public String getDateOfBirth() {
        return mDateOfBirth.getText().toString().trim();
    }

    public String getCity() {
        return mCity.getText().toString().trim();
    }

    public String getCountry() {
        return mCountry.getText().toString().trim();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
