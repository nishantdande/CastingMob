package com.castingmob.ui.fragment;
/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description : This is the screen which content
               Login and Sign Up Button
 Date        : 15/01/16
 ==============================================
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.castingmob.R;
import com.castingmob.ui.activity.OpeningScreenActivity;

public class LaunchScreenFragment extends BaseFragment implements View.OnClickListener {

    private Button login;
    private Button createAccount;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_launch_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        login = (Button) view.findViewById(R.id.login);
        createAccount = (Button) view.findViewById(R.id.createAccount);

        login.setOnClickListener(this);
        createAccount.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:{
                ((OpeningScreenActivity) getActivity()).setFragment(OpeningScreenActivity.ACCOUNT_FLOW.LOGIN,null);
            }
                break;

            case R.id.createAccount:{
                ((OpeningScreenActivity) getActivity()).setFragment(OpeningScreenActivity.ACCOUNT_FLOW.SELECT_ACCOUNT_TYPE,null);
            }
                break;
        }
    }
}
