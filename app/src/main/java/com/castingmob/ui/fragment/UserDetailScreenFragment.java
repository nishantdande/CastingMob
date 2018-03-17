package com.castingmob.ui.fragment;
/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 31/01/16
 ==============================================
 */

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.castingmob.CastingMob;
import com.castingmob.Feed.FeedManager;
import com.castingmob.Feed.model.FeedItem;
import com.castingmob.R;
import com.castingmob.account.model.Profile;
import com.castingmob.account.model.UserDetail;
import com.castingmob.ui.activity.CastingMobActivity;
import com.castingmob.ui.activity.DetailActivity;
import com.castingmob.ui.activity.Router;
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

public class UserDetailScreenFragment extends BaseFragment {

    private DetailActivity detailActivity;
    private ProgressView progressView;

    public interface onUserDetailScreenListener {
        void showUserDetailComponent(boolean b);
    }

    private onUserDetailScreenListener mUserDetailScreenListener;

    @Bind(R.id.user_selected_feed_item_photo)
    ImageView mUserFeedPhoto;

    @Bind(R.id.user_detail_frame)
    LinearLayout mUserDetailFrame;

    @Bind(R.id.name)
    TextView mName;
    @Bind(R.id.display_name)
    TextView mDisplayName;
    @Bind(R.id.gender)
    TextView mGender;
    @Bind(R.id.dob)
    TextView mDOB;
    @Bind(R.id.city)
    TextView mCity;
    @Bind(R.id.country)
    TextView mCountry;
    @Bind(R.id.ethnicity)
    TextView mEthnicity;
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

    @Bind(R.id.like)
    ImageView mLike;
    FeedItem feedItem;

    @Bind(R.id.highlight_like)
    ImageView highlightLike;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            feedItem = getArguments().getParcelable("feed");
            Log.d("profile", Profile.getInstance().getToken()+" "+feedItem.getLikes());
            ((CastingMobActivity) getActivity()).logger.debug(UserDetail.getInstance().toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if (mUserDetailScreenListener != null) {
            mUserDetailScreenListener.showUserDetailComponent(true);
        }

        mHeight = (TextView) mUserDetailFrame.findViewById(R.id.height);
        mWeight = (TextView) mUserDetailFrame.findViewById(R.id.weight);
        mChest = (TextView) mUserDetailFrame.findViewById(R.id.chest);
        mHip = (TextView) mUserDetailFrame.findViewById(R.id.hip);
        mWaist = (TextView) mUserDetailFrame.findViewById(R.id.waist);

        detailActivity = ((DetailActivity) getActivity());
        progressView = DialogFactory.createProgressDialog(getActivity(), "Loading...");

        Glide.with(CastingMob.getInstance().getContext())
                .load(getPhotoUrl())
                .centerCrop()
                .placeholder(R.drawable.ic_logo_final_black)
                .crossFade()
                .into(mUserFeedPhoto);

        checkPhotoIsAlreadyLiked();
    }

    private String getPhotoUrl() {
        if (feedItem == null) {
            return "";
        }
        return feedItem.getPhotoUrl();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mUserDetailScreenListener = (onUserDetailScreenListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }

    @OnClick(R.id.detail)
    public void onDetailClick(){
        if (mUserDetailFrame.getVisibility() == View.VISIBLE){
            mUserDetailFrame.setVisibility(View.GONE);
        }else{
            setUserValues();
            mUserDetailFrame.setVisibility(View.VISIBLE);
        }
    }

    private void setUserValues() {
        mName.setText(UserDetail.getInstance().getFirstName()+" "+UserDetail.getInstance().getLastName());
        mDisplayName.setText(UserDetail.getInstance().getDisplayName());
        mGender.setText(UserDetail.getInstance().getGender().toString());
        mDOB.setText(UserDetail.getInstance().getDateOfBirth());
        mCity.setText(UserDetail.getInstance().getCity());
        mCountry.setText(UserDetail.getInstance().getCountry());
        mEthnicity.setText(UserDetail.getInstance().getEthnicity());
        mHairColor.setText(UserDetail.getInstance().getHairColor());
        mEyeColor.setText(UserDetail.getInstance().getEyeColor());
        mHeight.setText(MeasurementConversion.convertInFeet(UserDetail.getInstance().getHeight()));
        mWeight.setText(String.format(detailActivity.getString(R.string.style_weight), UserDetail.getInstance().getWeight()));
        mChest.setText(String.format(detailActivity.getString(R.string.style_others), UserDetail.getInstance().getChest()));
        mHip.setText(String.format(detailActivity.getString(R.string.style_others), UserDetail.getInstance().getHip()));
        mWaist.setText(String.format(detailActivity.getString(R.string.style_others), UserDetail.getInstance().getWaist()));
    }

    private void checkPhotoIsAlreadyLiked(){
        if (feedItem != null) {
            if (feedItem.getLikes().contains(Profile.getInstance().getToken())){
                likeView();
            }
        }
    }

    @OnClick(R.id.block_content)
    public void onBlockContentClick(){

    }

    @OnClick(R.id.message)
    public void onMessageClick(){
        Bundle bundle = new Bundle();
        bundle.putString("participantToken", feedItem.getUserToken());
        Router.startMessageActivity(getActivity(), bundle);
    }

    @OnClick(R.id.like)
    public void onLikeClick(){
        progressView.show();
        FeedManager.getInstance().likeSelectedPhoto(UserDetail.getInstance().getToken(),
                feedItem.getPhotoId() ,new Callback<ArrayList<String>>() {
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
                    public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                        progressView.closeDialog();
                        if (response.isSuccessful()){
                            if (response.body().size() == 0){
                                DialogFactory.createAlertDialogWithNeutralButton(detailActivity, "You already liked this photo!", "Thanks for the feedback", "Done", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        dialog.cancel();
                                    }
                                }).show();
                            }
                            likeView();
                        }else{
                            detailActivity.logger.error(new Exception(response.message()));
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
                    public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                        progressView.closeDialog();
                        detailActivity.logger.error(t);
                    }
        });
    }

    @OnClick(R.id.comment)
    public void onCommentClick(){
        Bundle bundle = new Bundle();
        bundle.putInt("comment", 2);
        bundle.putParcelable("feedItem", feedItem);
        Router.startMessageActivity(getActivity(), bundle);
    }

    private void likeView(){
        highlightLike.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.circle_shape));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (mUserDetailScreenListener != null) {
            mUserDetailScreenListener.showUserDetailComponent(false);
        }
    }
}
