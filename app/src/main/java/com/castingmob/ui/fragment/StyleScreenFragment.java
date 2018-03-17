package com.castingmob.ui.fragment;
/*
 ==============================================
 Author      : Nishant Dande
 Version     : 
 Copyright   :
 Description :
 Date        : 17/01/16
 ==============================================
 */

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.castingmob.R;
import com.castingmob.account.AccountManager;
import com.castingmob.account.model.Profile;
import com.castingmob.ui.activity.CastingMobActivity;
import com.castingmob.ui.activity.DetailActivity;
import com.castingmob.ui.activity.OpeningScreenActivity;
import com.castingmob.ui.dialog.DialogFactory;
import com.castingmob.ui.view.ProgressView;
import com.castingmob.utils.MeasurementConversion;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StyleScreenFragment extends BaseFragment {


    @Bind(R.id.nationality)
    TextView mNationality;
    @Bind(R.id.hair_color)
    TextView mHairColor;
    @Bind(R.id.eye_color)
    TextView mEyeColor;
    @Bind(R.id.height)
    TextView mHeight;
    @Bind(R.id.weight)
    TextView mWeight;
    @Bind(R.id.chest)
    TextView mChest;
    @Bind(R.id.hip)
    TextView mHip;
    @Bind(R.id.waist)
    TextView mWaist;

    @Bind(R.id.style_next)
    TextView mNextBtn;

    private CastingMobActivity screenActivity;
    private ProgressView progressView;

    private ArrayList<Integer> height = new ArrayList<>();
    private ArrayList<Integer> weight = new ArrayList<>();
    private ArrayList<Integer> others = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeight();
        initWeight();
        initOthers();
    }

    private void initOthers() {
        for (int i = 1; i < 50; i++) {
            others.add(i);
        }
    }

    private void initWeight() {
        for (int i = 80; i < 600; i++) {
            weight.add(i);
        }
    }

    private void initHeight() {
        for (int i = 100; i < 300; i++) {
            height.add(i);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_style_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        if (getActivity() instanceof OpeningScreenActivity)
            screenActivity = ((OpeningScreenActivity) getActivity());
        else if (getActivity() instanceof DetailActivity){
            screenActivity = ((DetailActivity) getActivity());
            screenActivity.logger.debug(Profile.getInstance().toString());
            setValues(Profile.getInstance());
        }

        progressView = DialogFactory.createProgressDialog(screenActivity, "Loading...");
    }

    private void setValues(Profile profile){
        mNationality.setText(profile.getEthnicity());
        mHairColor.setText(profile.getHairColor());
        mEyeColor.setText(profile.getEyeColor());

        mHeight.setText(MeasurementConversion.convertInFeet(profile.getHeight()));
        mWeight.setText(String.format(screenActivity.getString(R.string.style_weight), profile.getWeight()));
        mChest.setText(String.format(screenActivity.getString(R.string.style_others), profile.getChest()));
        mHip.setText(String.format(screenActivity.getString(R.string.style_others), profile.getHip()));
        mWaist.setText(String.format(screenActivity.getString(R.string.style_others), profile.getWaist()));

        mNextBtn.setText(R.string.string_done);
    }

    @OnClick(R.id.nationality)
    public void onNationalityClick(){
        if (screenActivity !=null) {
            final String[] nationality = getResources().getStringArray(R.array.nationality);
            DialogFactory.createListDialog(screenActivity, getString(R.string.dialog_nationality_title), nationality, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mNationality.setText(nationality[which]);
                }
            });
        }
    }

    @OnClick(R.id.hair_color)
    public void onHairColorClick(){
        if (screenActivity !=null) {
            final String[] hair_colors = getResources().getStringArray(R.array.hair_color);
            DialogFactory.createListDialog(screenActivity, getString(R.string.dialog_hair_color_title), hair_colors , new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mHairColor.setText(hair_colors[which]);
                }
            });
        }
    }

    @OnClick(R.id.eye_color)
    public void onEyeColorClick(){
        if (screenActivity !=null) {
            final String[] eye_colors = getResources().getStringArray(R.array.eye_color);
            DialogFactory.createListDialog(screenActivity, getString(R.string.dialog_eye_color_title), eye_colors, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mEyeColor.setText(eye_colors[which]);
                }
            });
        }
    }

    @OnClick(R.id.height)
    public void onHeightClick(){
        if (screenActivity != null){
            DialogFactory.createListDialog(screenActivity, "Select Height in cm", height , new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Profile.getInstance().setHeight(height.get(which));
                    mHeight.setText(MeasurementConversion.convertInFeet(height.get(which)));
                }
            });
        }
    }

    @OnClick(R.id.weight)
    public void onWeightClick(){
        if (screenActivity != null){
            DialogFactory.createListDialog(screenActivity, "Select Weight in pounds", weight , new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Profile.getInstance().setWeight(weight.get(which));
                    mWeight.setText(String.format(screenActivity.getString(R.string.style_weight), weight.get(which)));
                }
            });
        }
    }

    @OnClick(R.id.chest)
    public void onChestClick(){
        if (screenActivity != null){
            DialogFactory.createListDialog(screenActivity, "Select Chest", others , new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Profile.getInstance().setChest(others.get(which));
                    mChest.setText(String.format(screenActivity.getString(R.string.style_others), others.get(which)));
                }
            });
        }
    }

    @OnClick(R.id.hip)
    public void onHipClick(){
        if (screenActivity != null){
            DialogFactory.createListDialog(screenActivity, "Select Hip", others , new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Profile.getInstance().setHip(others.get(which));
                    mHip.setText(String.format(screenActivity.getString(R.string.style_others), others.get(which)));
                }
            });
        }
    }

    @OnClick(R.id.waist)
    public void onWaistClick(){
        if (screenActivity != null){
            DialogFactory.createListDialog(screenActivity, "Select Waist", others , new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Profile.getInstance().setWaist(others.get(which));
                    mWaist.setText(String.format(screenActivity.getString(R.string.style_others), others.get(which)));
                }
            });
        }
    }

    private String extractInteger(String s) {
        return s.replaceAll("[^0-9]", "");
    }

    @OnClick(R.id.style_next)
    public void nextClick(){

        if (screenActivity !=null){
            Profile.getInstance().setEthnicity(getNationality());
            Profile.getInstance().setHairColor(getHairColor());
            Profile.getInstance().setEyeColor(getEyeColor());

            screenActivity.logger.debug(Profile.getInstance().toString());
            progressView.show();
            AccountManager.getInstance().updateAccount(Profile.getInstance(), new Callback<Void>() {
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
                    if (getActivity() instanceof OpeningScreenActivity){
                        ((OpeningScreenActivity) screenActivity)
                                .setFragment(OpeningScreenActivity.ACCOUNT_FLOW.SET_COVER_PHOTO, null);

                    }else if(getActivity() instanceof DetailActivity){
                        ((DetailActivity) screenActivity).finish();
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
                public void onFailure(Call<Void> call, Throwable t) {
                    progressView.closeDialog();
                    screenActivity.logger.error(t);
                }
            });

        }

    }

    public String getNationality() {
        return mNationality.getText().toString().trim();
    }

    public String getHairColor() {
        return mHairColor.getText().toString().trim();
    }

    public String getEyeColor() {
        return mEyeColor.getText().toString().trim();
    }

    public String getHeight() {
        return mHeight.getText().toString().trim();
    }

    public String getWeight() {
        return mWeight.getText().toString().trim();
    }

    public String getChest() {
        return mChest.getText().toString().trim();
    }

    public String getHip() {
        return mHip.getText().toString().trim();
    }

    public String getWaist() {
        return mWaist.getText().toString().trim();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

