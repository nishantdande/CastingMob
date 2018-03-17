package com.castingmob.ui.fragment;
/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 16/01/16
 ==============================================
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.castingmob.R;
import com.castingmob.account.AccountManager;
import com.castingmob.account.model.Profile;
import com.castingmob.ui.activity.OpeningScreenActivity;
import com.castingmob.ui.dialog.DialogFactory;
import com.castingmob.ui.view.ProgressView;
import com.castingmob.utils.Validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordScreenFragment extends BaseFragment {

    @Bind(R.id.set_password)
    EditText mNewPassword;
    @Bind(R.id.confirm_password)
    EditText mConfirmPassword;

    String email;
    private OpeningScreenActivity openingScreenActivity;
    private ProgressView progressView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            email = bundle.getString("email");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set_password_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        openingScreenActivity = ((OpeningScreenActivity) getActivity());
        progressView = DialogFactory.createProgressDialog(openingScreenActivity, "Loading...");
    }

    @OnClick(R.id.btnPassword)
    public void btnPasswordClick(){

        if (openingScreenActivity != null){

            if (Validation.isEmpty(getNewPassword())){
                mNewPassword.setError(openingScreenActivity.getString(R.string.field_blank_error_message));
                return;
            }

            if (!checkPassword(getNewPassword())) {
                mNewPassword.setError(openingScreenActivity.getString(R.string.password_limit_error_message));
                return;
            }

            if (!checkPassword(getNewPassword())) {
                mNewPassword.setError(openingScreenActivity.getString(R.string.password_alpha_numeric_error_message));
                return;
            }

            if (Validation.isEmpty(getConfirmPassword())){
                mConfirmPassword.setError(openingScreenActivity.getString(R.string.field_blank_error_message));
                return;
            }

            if (!validatePassword()) {
                mNewPassword.setError(openingScreenActivity.getString(R.string.password_match_error_message));
                // TODO - Handle the Error callback
                openingScreenActivity.logger.error(new Exception("password and confirm doesnot match"));
                return;
            }

            progressView.show();
            AccountManager.getInstance().setPassword(email, getNewPassword(), new Callback<Void>() {
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
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Profile.getInstance().setPassword(getNewPassword());
                    progressView.closeDialog();
                    ((OpeningScreenActivity) getActivity())
                            .setFragment(OpeningScreenActivity.ACCOUNT_FLOW.UPDATE_PROFILE, null);
                }

                /**
                 * Invoked when a network exception occurred talking to the server or when an unexpected
                 * exception occurred creating the request or processing the response.
                 *
                 * @param call
                 * @param t
                 */
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    progressView.closeDialog();
                    // TODO - Handle the Error callback
                    openingScreenActivity.logger.error(t);
                }
            });
        }else{
            // TODO - Handle the Error call for activty null
        }

    }

    /**
     * Check minimum limit of password
     * @param password
     * @return
     */
    private boolean checkPasswordLimit(String password){
        String pattern = "^.{6,20}$";
        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(password);
        return m.matches();
    }

    /**
     * Check password follows alhpa numeric pattern
     * @param password
     * @return
     */
    private boolean checkPassword(String password){
        String pattern = "[A-Za-z0-9]{6,20}";
        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(password);
        return m.matches();
    }

    /**
     * Check whether password and confirm password is equal
     * @return
     */
    private boolean validatePassword(){
        if (getNewPassword().equals(getConfirmPassword()))
            return true;

        return false;
    }

    public String getNewPassword() {
        return mNewPassword.getText().toString().trim();
    }

    public String getConfirmPassword() {
        return mConfirmPassword.getText().toString().trim();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
