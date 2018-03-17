package com.castingmob.ui.fragment;
/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 15/01/16
 ==============================================
 */

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.castingmob.R;
import com.castingmob.account.AccountManager;
import com.castingmob.account.model.Profile;
import com.castingmob.prefrences.ProfilePreferences;
import com.castingmob.ui.activity.OpeningScreenActivity;
import com.castingmob.ui.activity.Router;
import com.castingmob.ui.dialog.DialogFactory;
import com.castingmob.ui.view.ProgressView;
import com.castingmob.utils.Validation;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreenFragment extends BaseFragment {

    @Bind(R.id.email_address)
    EditText mEmail;
    @Bind((R.id.password))
    EditText mPassword;
    private ProgressView progressView;
    private OpeningScreenActivity openingScreenActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_screen,container,false);
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        openingScreenActivity = ((OpeningScreenActivity) getActivity());

        progressView = DialogFactory.createProgressDialog(openingScreenActivity, "loading...");
    }

    @OnClick(R.id.sign_in)
    public void onSignInClick() {
        if (Validation.isEmpty(getEmail())){
            mEmail.setError(openingScreenActivity.getString(R.string.field_blank_error_message));
            return;
        }

        if (!Validation.isValidEmailAddress(getEmail())) {
            mEmail.setError(openingScreenActivity.getString(R.string.email_validation_error_message));
            return;
        }

        if (Validation.isEmpty(getPassword())){
            mPassword.setError(openingScreenActivity.getString(R.string.field_blank_error_message));
            return;
        }
        progressView.showDialog();
        AccountManager.getInstance().login(getEmail(), getPassword(), new Callback<Profile>() {
            /**
             * Invoked for a received HTTP response.
             * <p/>
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call {@link Response#isSuccessful()} to determine if the response indicates success.
             *
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                progressView.closeDialog();
                if (response.isSuccessful()){
                    ProfilePreferences.getInstance().setIsLoggedIn();
                    ProfilePreferences.getInstance().setProfileInfo(response.body());
                    // Set the response in Profile object
                    Profile.getInstance().setProfile(ProfilePreferences.getInstance().getProfileInfo());


                    Router.startHomeScreenActivity(openingScreenActivity);
                    openingScreenActivity.finish();
                }else{
                    Log.e("error", response.errorBody().toString(), new Exception(response.message()));
                }
            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             *
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                progressView.closeDialog();
                Log.e("error", t.getMessage(), t);
            }
        });

    }

    @OnClick(R.id.close)
    public void close(){
        if (openingScreenActivity != null) {
            Bundle bundle = new Bundle();
            bundle.putString("close","login");
            openingScreenActivity.setFragment(OpeningScreenActivity.ACCOUNT_FLOW.LAUNCH_SCREEN, bundle);
        }
    }

    public String getEmail() {
        return mEmail.getText().toString().trim();
    }

    public String getPassword() {
        return mPassword.getText().toString().trim();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
