package com.castingmob.ui.activity;
/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description : This is Launch Screen of the App
 Date        : 14/01/16
 ==============================================
 */

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.castingmob.BuildConfig;
import com.castingmob.R;
import com.castingmob.account.model.Profile;
import com.castingmob.logger.Logger;
import com.castingmob.prefrences.ProfilePreferences;
import com.castingmob.ui.dialog.DialogFactory;
import com.castingmob.ui.fragment.AccountTypeFragment;
import com.castingmob.ui.fragment.ActivationScreenFragment;
import com.castingmob.ui.fragment.CreateAccountFragment;
import com.castingmob.ui.fragment.LaunchScreenFragment;
import com.castingmob.ui.fragment.LoginScreenFragment;
import com.castingmob.ui.fragment.PasswordScreenFragment;
import com.castingmob.ui.fragment.ProfilePicScreenFragment;
import com.castingmob.ui.fragment.StyleScreenFragment;
import com.castingmob.ui.fragment.UpdateCoverPhotoScreenFragment;
import com.castingmob.ui.fragment.UpdateProfileScreenFragment;
import com.castingmob.ui.fragment.signup.ProfileUpdateContainerFragment;

public class OpeningScreenActivity extends CastingMobActivity {

    public final Logger logger = new Logger(OpeningScreenActivity.class.getSimpleName());

    public enum ACCOUNT_FLOW {
        LAUNCH_SCREEN(0),
        LOGIN(1),
        SELECT_ACCOUNT_TYPE(2),
        CREATE_ACCOUNT(3),
        ACTIVATION(4),
        SET_PASSWORD(5),
        UPDATE_PROFILE(6),
        SET_STYLE(7),
        SET_COVER_PHOTO(8),
        SET_PROFILE_PIC(9);

        int id;
        ACCOUNT_FLOW(int id) {
            this.id = id;
        }
    }

    private FragmentTransaction mFragmentTransaction;
    private FragmentManager mFragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ProfilePreferences.getInstance().isLoggedIn()){
            Profile.getInstance().setProfile(ProfilePreferences.getInstance().getProfileInfo());
            Router.startHomeScreenActivity(this);
            this.finish();
        }
        setContentView(R.layout.activity_opening_screen);


        if (savedInstanceState == null) {
            mFragmentManager = getSupportFragmentManager();
            setFragment(ACCOUNT_FLOW.LAUNCH_SCREEN, null);
        }
    }

    /**
     * Set account fragment based on the status.
     *
     * @param account_flow the current account process value.
     * @param bundle          the information bundle.
     */
    public void setFragment(ACCOUNT_FLOW account_flow, Bundle bundle) {
        switch (account_flow) {
            case LAUNCH_SCREEN: {
                if (bundle!=null){
                    String bundleString = bundle.getString("close");
                    if (bundleString.contains("login")){
                        destroyFragment(LoginScreenFragment.class.getSimpleName());
                    }else if (bundleString.contains("account_type")){
                        destroyFragment(AccountTypeFragment.class.getSimpleName());
                    }else if (bundleString.contains("create_account")){
                        destroyFragment(CreateAccountFragment.class.getSimpleName());
                    }
                }
                addFragmentWithArgument(new LaunchScreenFragment(),
                        LaunchScreenFragment.class.getSimpleName(), bundle);
            }
                break;

            case LOGIN: {
                destroyFragment(LaunchScreenFragment.class.getSimpleName());
                addFragmentWithArgument(new LoginScreenFragment(),
                        LoginScreenFragment.class.getSimpleName(), bundle);
            }
                break;

            case SELECT_ACCOUNT_TYPE: {
                destroyFragment(LaunchScreenFragment.class.getSimpleName());
                addFragmentWithArgument(new AccountTypeFragment(),
                        AccountTypeFragment.class.getSimpleName(), bundle);
            }
                break;

            case CREATE_ACCOUNT: {
                destroyFragment(AccountTypeFragment.class.getSimpleName());
                addFragmentWithArgument(new CreateAccountFragment(),
                        CreateAccountFragment.class.getSimpleName(), bundle);
            }
                break;

            case ACTIVATION: {
                destroyFragment(CreateAccountFragment.class.getSimpleName());
                addFragmentWithArgument(new ActivationScreenFragment(),
                        ActivationScreenFragment.class.getSimpleName(), bundle);
            }
                break;

            case SET_PASSWORD: {
                destroyFragment(ActivationScreenFragment.class.getSimpleName());
                addFragmentWithArgument(new PasswordScreenFragment(),
                        PasswordScreenFragment.class.getSimpleName(), bundle);
            }
                break;

            case UPDATE_PROFILE: {
                destroyFragment(PasswordScreenFragment.class.getSimpleName());
                addFragmentWithArgument(new ProfileUpdateContainerFragment(),
                        ProfileUpdateContainerFragment.class.getSimpleName(), bundle);
            }
            break;

            case SET_STYLE:{
                destroyFragment(UpdateProfileScreenFragment.class.getSimpleName());
                addFragmentWithArgument(new StyleScreenFragment(),
                        StyleScreenFragment.class.getSimpleName(), bundle);
            }
                break;

            case SET_COVER_PHOTO:{
                destroyFragment(ProfileUpdateContainerFragment.class.getSimpleName());
                addFragmentWithArgument(new UpdateCoverPhotoScreenFragment(),
                        UpdateCoverPhotoScreenFragment.class.getSimpleName(), bundle);
            }
            break;

            case SET_PROFILE_PIC:{
                destroyFragment(UpdateCoverPhotoScreenFragment.class.getSimpleName());
                addFragmentWithArgument(new ProfilePicScreenFragment(),
                        ProfilePicScreenFragment.class.getSimpleName(), bundle);
            }
            break;


        }
    }

    private void destroyFragment(String tagName){

        Fragment fragment = mFragmentManager.findFragmentByTag(tagName);
        if (fragment!=null)
            removeFragment(fragment);
    }

    // add the fragment with bundle as argument.
    private void addFragmentWithArgument(Fragment fragment, String tag, Bundle bundle) {
        if (bundle != null)
            fragment.setArguments(bundle);

        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        mFragmentTransaction.add(R.id.container, fragment, tag);
        mFragmentTransaction.commitAllowingStateLoss();
    }

    //remove fragment from stack.
    private void removeFragment(Fragment fragment)
    {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mFragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });
        mFragmentTransaction.remove(fragment);
        mFragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {

        Fragment fragment = mFragmentManager.findFragmentById(R.id.container);
        if (fragment != null){
            canExist();
        }
    }

    private void canExist(){
        DialogFactory.createAlertDialogWithButtons(this,
                getString(R.string.open_screen_exist_title),
                getString(R.string.open_screen_exist_message),
                getString(R.string.open_screen_exist_yes_button),
                getString(R.string.open_screen_exist_no_button),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OpeningScreenActivity.this.finish();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        dialog.cancel();
                    }
                }).show();
    }
}
