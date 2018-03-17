package com.castingmob.ui.fragment;/*
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivationScreenFragment extends BaseFragment{

    @Bind(R.id.activate_code)
    EditText mActivationCode;
    private OpeningScreenActivity openingScreenActivity;
    private String email = null;
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
        return inflater.inflate(R.layout.fragment_activation_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        openingScreenActivity = ((OpeningScreenActivity) getActivity());
        progressView = DialogFactory.createProgressDialog(openingScreenActivity, "Loading...");
    }

    @OnClick(R.id.activate)
    public void activateClick(){
        if (openingScreenActivity != null){
            if (Validation.isEmpty(getActivationCode())){
                mActivationCode.setError(openingScreenActivity.getString(R.string.field_blank_error_message));
                return;
            }

            if (email == null) {
                // TODO - Handle the Error callback
                openingScreenActivity.logger.error(new Exception("unable to have email address"));
                return;
            }

            progressView.show();
            AccountManager.getInstance().activateAccount(email, getActivationCode(), new Callback<Profile>() {
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

                    // Set the response in Profile object
                    Profile.getInstance().setProfile(response.body());


                    Bundle bundle = new Bundle();
                    bundle.putString("email", email);
                    openingScreenActivity
                            .setFragment(OpeningScreenActivity.ACCOUNT_FLOW.SET_PASSWORD, bundle);
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
                    // TODO - Handle the Error callback
                    openingScreenActivity.logger.error(t);
                }
        });

        } else{
            // TODO - Handle the Error call for activty null
        }

    }

    public String getActivationCode() {
        return mActivationCode.getText().toString().trim();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
