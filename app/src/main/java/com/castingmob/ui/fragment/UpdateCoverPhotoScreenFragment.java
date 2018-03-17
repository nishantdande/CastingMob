package com.castingmob.ui.fragment;
/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 17/01/16
 ==============================================
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.castingmob.R;
import com.castingmob.account.AccountManager;
import com.castingmob.account.model.PhotoItem;
import com.castingmob.account.model.Profile;
import com.castingmob.ui.activity.CastingMobActivity;
import com.castingmob.ui.activity.DetailActivity;
import com.castingmob.ui.activity.OpeningScreenActivity;
import com.castingmob.ui.dialog.DialogFactory;
import com.castingmob.ui.view.ProgressView;
import com.castingmob.utils.ImageUtility;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateCoverPhotoScreenFragment extends BaseFragment {

    @Bind(R.id.cover_photo)
    ImageView mCoverPhoto;
    private int REQUEST_CAMERA = 101;
    private int SELECT_FILE = 102;
    private CastingMobActivity screenActivity;
    private ProgressView progressView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set_cover_photo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if (getActivity() instanceof OpeningScreenActivity)
            screenActivity = ((OpeningScreenActivity) getActivity());
        else if(getActivity() instanceof DetailActivity)
            screenActivity = (DetailActivity) getActivity();

        if (screenActivity instanceof DetailActivity){
            ((TextView)view.findViewById(R.id.cover_next)).setVisibility(View.GONE);
            ((TextView)view.findViewById(R.id.title_template)).setVisibility(View.GONE);
        }

        progressView = DialogFactory.createProgressDialog(screenActivity, "Loading...");
    }

    @OnClick(R.id.cover_next)
    public void onCoverNextClick(){
        if (screenActivity != null){
            if (screenActivity instanceof OpeningScreenActivity){
            ((OpeningScreenActivity) screenActivity)
                    .setFragment(OpeningScreenActivity.ACCOUNT_FLOW.SET_PROFILE_PIC, null);

            }
        }

    }

    @OnClick(R.id.take_photo)
    public void onTakePhotoClick(){
//        - See more at: http://www.theappguruz.com/blog/android-take-photo-camera-gallery-code-sample#sthash.fD2nNIRu.dpuf
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @OnClick(R.id.choose_photo)
    public void onChoosePhotoClick(){
//        - See more at: http://www.theappguruz.com/blog/android-take-photo-camera-gallery-code-sample#sthash.fD2nNIRu.dpuf
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(intent, "Select File"),
                SELECT_FILE);
    }

    @OnClick(R.id.cover_next)
    public void onNextClick(){
        if (screenActivity != null) {
            if (screenActivity instanceof OpeningScreenActivity)
                ((OpeningScreenActivity)screenActivity).setFragment(OpeningScreenActivity.ACCOUNT_FLOW.SET_PROFILE_PIC,null);
        }
    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (screenActivity != null){
            if (resultCode != Activity.RESULT_OK) {
                screenActivity.logger.error(new Exception("something went wrong with upload photos"));
                return;
            }

            if (data == null){
                screenActivity.logger.error(new Exception("Intent data is null"));
                return;
            }

            if (requestCode == REQUEST_CAMERA){
                Bitmap bitmap = (Bitmap) (data != null ? data.getExtras().get("data") : null);
                mCoverPhoto.setImageBitmap(bitmap);
//                mCoverPhoto.setRotation(90f);
                String destination = ImageUtility.getImageDestinationofCapturedPhoto(bitmap);

                if (destination != null){
                    screenActivity.logger.debug(destination);
                    uploadCoverPhoto(destination);
                }else{
                    screenActivity.logger.error(new Exception("Image location not found"));
                }
            }else if(requestCode == SELECT_FILE){
                mCoverPhoto.setImageBitmap(ImageUtility.getBitmapFromGallery(screenActivity, data));
//                mCoverPhoto.setRotation(90f);
                if (data != null){
                    uploadCoverPhoto(ImageUtility.getGalleyImagePath(data, screenActivity));
                }else {
                    screenActivity.logger.error(new Exception("Image location not found"));
                }

            }
        }
    }

    private void uploadCoverPhoto(String destination){
        progressView.show();
        AccountManager.getInstance().uploadCoverPhoto(Profile.getInstance().getToken(), destination, new Callback<PhotoItem>() {
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
            public void onResponse(Call<PhotoItem> call, Response<PhotoItem> response) {
                screenActivity.logger.debug(response.raw() + " "+response.body());
                progressView.closeDialog();
                if (!response.isSuccessful())
                    screenActivity.logger.error(new Exception(response.raw() + ""));
            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             *
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<PhotoItem> call, Throwable t) {
                screenActivity.logger.error(t);
                progressView.closeDialog();
            }
        });
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
