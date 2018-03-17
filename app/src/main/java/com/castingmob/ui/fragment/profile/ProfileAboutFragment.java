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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.castingmob.R;
import com.castingmob.ui.activity.NewHomeScreenActivity;
import com.castingmob.ui.activity.Router;
import com.castingmob.ui.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.castingmob.ui.activity.EditProfileActivity.EDIT;

public class ProfileAboutFragment extends BaseFragment {

    private static ProfileAboutFragment profileAboutFragment;

    public static ProfileAboutFragment createFragment() {
        if (profileAboutFragment == null) {
            profileAboutFragment = new ProfileAboutFragment();
        }
        return profileAboutFragment;
    }

    @Bind(R.id.about_title)
    TextView mTitle;
    @Bind(R.id.about_value)
    TextView mValue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_about, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        mTitle.setText(R.string.profile_about_title);
        mValue.setText(R.string.profile_blank);
    }

    @OnClick(R.id.about_title)
    public void aboutTitle(){
        Bundle bundle = new Bundle();
        bundle.putInt("edit", 1); // About == 1
        Router.startEditProfileActivity(getActivity(), ProfileAboutFragment.this, bundle);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == EDIT){
            Bundle bundle = data.getExtras();
            if (bundle != null){
                mValue.setText(bundle.getString("aboutValue", getString(R.string.profile_blank)));
            }
        }
    }
}
