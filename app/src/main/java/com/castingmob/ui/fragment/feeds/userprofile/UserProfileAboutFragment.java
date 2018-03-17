package com.castingmob.ui.fragment.feeds.userprofile;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 15/09/16
 ==============================================
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.castingmob.R;
import com.castingmob.ui.fragment.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserProfileAboutFragment extends BaseFragment {

    private static UserProfileAboutFragment userProfileAboutFragment;

    public static UserProfileAboutFragment createFragment() {
        if (userProfileAboutFragment == null) {
            userProfileAboutFragment = new UserProfileAboutFragment();
        }
        return userProfileAboutFragment;
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
}
