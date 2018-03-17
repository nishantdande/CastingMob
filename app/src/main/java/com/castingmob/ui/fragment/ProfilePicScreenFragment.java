package com.castingmob.ui.fragment;/*
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

import com.castingmob.R;
import com.castingmob.account.AccountManager;
import com.castingmob.account.model.PhotoItem;
import com.castingmob.account.model.Profile;
import com.castingmob.prefrences.ProfilePreferences;
import com.castingmob.ui.activity.CastingMobActivity;
import com.castingmob.ui.activity.DetailActivity;
import com.castingmob.ui.activity.OpeningScreenActivity;
import com.castingmob.ui.activity.Router;
import com.castingmob.ui.dialog.DialogFactory;
import com.castingmob.ui.view.ProgressView;
import com.castingmob.utils.ImageUtility;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePicScreenFragment extends BaseFragment {

    @Bind(R.id.profile_pic)
    ImageView mProfilePic;
    private CastingMobActivity openingScreenActivity;
    private ProgressView progressView;
    private int REQUEST_CAMERA = 100;
    private int SELECT_FILE = 101;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set_profile_pic_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if (getActivity() instanceof OpeningScreenActivity)
            openingScreenActivity = ((OpeningScreenActivity) getActivity());
        else if(getActivity() instanceof DetailActivity)
            openingScreenActivity = (DetailActivity) getActivity();

        progressView = DialogFactory.createProgressDialog(openingScreenActivity, "Loading...");
    }

    @OnClick(R.id.profile_pic_done)
    public void onDoneClick(){
        ((OpeningScreenActivity)getActivity()).logger.debug("You r done with registration, ready to go..");
        progressView.show();
        AccountManager.getInstance().login(Profile.getInstance().getEmail(),
                Profile.getInstance().getPassword(), new Callback<Profile>() {
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
                        ProfilePreferences.getInstance().setIsLoggedIn();
                        ProfilePreferences.getInstance().setProfileInfo(response.body());
                        // Set the response in Profile object
                        Profile.getInstance().setProfile(ProfilePreferences.getInstance().getProfileInfo());


                        Router.startHomeScreenActivity(openingScreenActivity);
                        openingScreenActivity.finish();
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
                        openingScreenActivity.logger.error(t);
                    }
        });
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

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (openingScreenActivity != null){
            if (resultCode != Activity.RESULT_OK) {
                openingScreenActivity.logger.error(new Exception("something went wrong with upload photos"));
                return;
            }

            if (data == null){
                openingScreenActivity.logger.error(new Exception("Intent data is null"));
                return;
            }

            if (requestCode == REQUEST_CAMERA){
                Bitmap bitmap = (Bitmap) (data != null ? data.getExtras().get("data") : null);
                mProfilePic.setImageBitmap(bitmap);
                String destination = ImageUtility.getImageDestinationofCapturedPhoto(bitmap);

                if (destination != null){
                    openingScreenActivity.logger.debug(destination);
                    uploadCoverPhoto(destination);
                }else{
                    openingScreenActivity.logger.error(new Exception("Image location not found"));
                }
            }else if(requestCode == SELECT_FILE){
                mProfilePic.setImageBitmap(ImageUtility.getBitmapFromGallery(openingScreenActivity, data));
                if (data != null){
                    uploadCoverPhoto(ImageUtility.getGalleyImagePath(data, openingScreenActivity));
                }else {
                    openingScreenActivity.logger.error(new Exception("Image location not found"));
                }

            }
        }
    }

    private void uploadCoverPhoto(String destination){
        progressView.show();
        AccountManager.getInstance().uploadProfilePhoto(Profile.getInstance().getToken(),
                destination, new Callback<PhotoItem>() {
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
                        openingScreenActivity.logger.debug(response.raw() + " "+response.body()+" "+response.isSuccessful());
                        progressView.closeDialog();
                        if (!response.isSuccessful())
                            openingScreenActivity.logger.error(new Exception(response.raw() + ""));
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
                        openingScreenActivity.logger.error(t);
                        progressView.closeDialog();
                    }
        });
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
