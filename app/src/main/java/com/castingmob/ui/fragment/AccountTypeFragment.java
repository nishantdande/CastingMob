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

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.castingmob.R;
import com.castingmob.account.model.Account;
import com.castingmob.ui.activity.OpeningScreenActivity;
import com.castingmob.ui.dialog.DialogFactory;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountTypeFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_account,container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.model)
    public void onModelClick(){
        Bundle bundle = new Bundle();
        bundle.putInt("type", Account.Type.model.getValue());
        bundle.putInt("subType", Account.subType.nothing.getValue());
        ((OpeningScreenActivity) getActivity())
                .setFragment(OpeningScreenActivity.ACCOUNT_FLOW.CREATE_ACCOUNT, bundle);
    }

    @OnClick(R.id.photographer)
    public void onAgentClick(){
        Bundle bundle = new Bundle();
        bundle.putInt("type", Account.Type.client.getValue());
        bundle.putInt("subType", Account.subType.photographer.getValue());
        ((OpeningScreenActivity) getActivity())
                .setFragment(OpeningScreenActivity.ACCOUNT_FLOW.CREATE_ACCOUNT, bundle);
    }

    @OnClick(R.id.makeup_artist)
    public void onMakeupArtistClick(){
        Bundle bundle = new Bundle();
        bundle.putInt("type", Account.Type.client.getValue());
        bundle.putInt("subType", Account.subType.makeupartist.getValue());
        ((OpeningScreenActivity) getActivity())
                .setFragment(OpeningScreenActivity.ACCOUNT_FLOW.CREATE_ACCOUNT, bundle);
    }

    @OnClick(R.id.stylist)
    public void onStylistClick(){
        Bundle bundle = new Bundle();
        bundle.putInt("type", Account.Type.client.getValue());
        bundle.putInt("subType", Account.subType.stylist.getValue());
        ((OpeningScreenActivity) getActivity())
                .setFragment(OpeningScreenActivity.ACCOUNT_FLOW.CREATE_ACCOUNT, bundle);
    }

    @OnClick(R.id.close)
    public void close(){
        if (getActivity() != null) {
            Bundle bundle = new Bundle();
            bundle.putString("close","account_type");
            ((OpeningScreenActivity)getActivity()).setFragment(OpeningScreenActivity.ACCOUNT_FLOW.LAUNCH_SCREEN, bundle);
        }
    }
}
