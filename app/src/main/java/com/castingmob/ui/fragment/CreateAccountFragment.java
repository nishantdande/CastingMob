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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.EditText;

import com.castingmob.R;
import com.castingmob.account.AccountManager;
import com.castingmob.account.model.Account;
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

import static com.castingmob.account.model.Account.Type;

public class CreateAccountFragment extends BaseFragment {

    @Bind(R.id.email_address)
    EditText mEmail;
    @Bind(R.id.website)
    EditText mWebsite;

    Type type = null;
    Account.subType subType = null;
    boolean showWebsite = false;

    private OpeningScreenActivity openingScreenActivity;
    private ProgressView progressView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle != null){
            int typeCount  = bundle.getInt("type");
            showWebsite = typeCount > 0 ? true : false;
            type = Type.values()[typeCount];


            int sub = bundle.getInt("subType");
            if (sub != -1){
                subType = Account.subType.values()[sub];
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up_screen, container, false);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        openingScreenActivity = ((OpeningScreenActivity) getActivity());

        progressView = DialogFactory.createProgressDialog(openingScreenActivity, "loading...");

        if (showWebsite){
            mWebsite.setVisibility(View.VISIBLE);
        }else {
            mWebsite.setVisibility(View.GONE);
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @OnClick(R.id.sign_up)
    public void onSignUpClick(){
        if (openingScreenActivity != null){
            if (Validation.isEmpty(getEmail())){
                mEmail.setError(openingScreenActivity.getString(R.string.field_blank_error_message));
                return;
            }

            if (!Validation.isValidEmailAddress(getEmail())) {
                mEmail.setError(openingScreenActivity.getString(R.string.email_validation_error_message));
                return;
            }

            if (showWebsite){
                if (Validation.isEmpty(getWebsite())){
                    mWebsite.setError(openingScreenActivity.getString(R.string.field_blank_error_message));
                    return;
                }
            }

            Account account = getAccount(type);
            if (account != null){
                progressView.show();
                AccountManager.getInstance().createAccount(account, new Callback<Void>() {
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
                        progressView.closeDialog();
                        Bundle bundle = new Bundle();
                        bundle.putString("email", getEmail());
                        openingScreenActivity
                                .setFragment(OpeningScreenActivity.ACCOUNT_FLOW.ACTIVATION, bundle);
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
                        // TODO - Handle the Error callback
                        progressView.closeDialog();
                        openingScreenActivity.logger.error(t);
                    }
                });
            }else{
                // TODO - Handle the Error callback
                openingScreenActivity.logger.error(new Exception("Unable to create account"));
            }
        }else{
            // TODO - Handle the Error call for activty null
        }
    }

    @OnClick(R.id.terms_condition)
    public void onTermsConditionClick(){

    }

    @OnClick(R.id.close)
    public void close(){
        if (openingScreenActivity != null) {
            Bundle bundle = new Bundle();
            bundle.putString("close","create_account");
            openingScreenActivity.setFragment(OpeningScreenActivity.ACCOUNT_FLOW.LAUNCH_SCREEN, bundle);
        }
    }


    /**
     * Get the Account object depending upon Type selected
     * @param type
     * @return
     */
    public Account getAccount(Type type){
        switch (type){
            case model:{
                return new Account(getEmail(), type);
            }

            case agent:{
                return new Account(getEmail(), type, getWebsite());
            }

            case client:{
                return new Account(getEmail(), type, subType, getWebsite());
            }
        }
        return null;
    }

    public String getEmail() {
        return mEmail.getText().toString().trim();
    }

    public String getWebsite() {
        return mWebsite.getText().toString().trim();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
