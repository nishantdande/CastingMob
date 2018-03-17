package com.castingmob.ui.activity;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 27/01/16
 ==============================================
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.castingmob.R;
import com.castingmob.logger.Logger;
import com.castingmob.ui.fragment.PhotoGalleryFragment;
import com.castingmob.ui.fragment.PhotoGalleryFragment.onPhotosCountEventListener;
import com.castingmob.ui.fragment.ProfilePicScreenFragment;
import com.castingmob.ui.fragment.StyleScreenFragment;
import com.castingmob.ui.fragment.UpdateCoverPhotoScreenFragment;
import com.castingmob.ui.fragment.UpdatePolaroidPhotoScreenFragment;
import com.castingmob.ui.fragment.UpdateProfilePhotoFragment;
import com.castingmob.ui.fragment.UserDetailScreenFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.castingmob.ui.fragment.UserDetailScreenFragment.*;

public class DetailActivity extends CastingMobActivity implements onPhotosCountEventListener, onUserDetailScreenListener
{

    public final Logger logger = new Logger(DetailActivity.class.getSimpleName());

    @Bind(R.id.title)
    TextView mTitle;

    @Bind(R.id.detail_cover_photo)
    ImageView mUserCoverPhotos;
    @Bind(R.id.detail_polaroid_photo)
    ImageView mUserPolaroidPhotos;

    public enum DETAIL_FLOW {
        PHOTO_GALLERY(0),
        COVER_PHOTO(2),
        POLAROID_PHOTO(3),
        POLAROID_PHOTO_GALLERY(4),
        UPDATE_STYLE(5),
        USER_DETAIL_SCREEN(6),
        USER_DETAIL_COVER_PHOTOS(7),
        USER_DETAIL_POLAROID_PHOTOS(8),
        PROFILE_PIC(9);

        int id;
        DETAIL_FLOW(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static DETAIL_FLOW getScreen(int screenId){
            switch (screenId){
                case 0:
                    return PHOTO_GALLERY;

                case 2:
                    return COVER_PHOTO;

                case 3:
                    return POLAROID_PHOTO;

                case 4:
                    return POLAROID_PHOTO_GALLERY;

                case 5:
                    return UPDATE_STYLE;

                case 6:
                    return USER_DETAIL_SCREEN;

                case 7:
                    return USER_DETAIL_COVER_PHOTOS;

                case 8:
                    return USER_DETAIL_POLAROID_PHOTOS;

                case 9:
                    return PROFILE_PIC;

                default:
                    return null;
            }
        }
    }

    private FragmentTransaction mFragmentTransaction;
    private FragmentManager mFragmentManager;

    private int screenId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenId = getIntent().getExtras().getInt("screenId");
        setContentView(R.layout.activity_detail_screen);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            mFragmentManager = getSupportFragmentManager();
            setFragment(DETAIL_FLOW.getScreen(screenId), null);
        }
    }

    @Override
    public void getPhotosCount(int count) {
        mTitle.setText(getString(R.string.detail_title_string, count));
    }

    @Override
    public void showPhotoCount(boolean b) {
        if (b){
            mTitle.setVisibility(View.VISIBLE);
            mUserCoverPhotos.setVisibility(View.GONE);
            mUserPolaroidPhotos.setVisibility(View.GONE);
        }else {
            mTitle.setVisibility(View.GONE);
            mUserCoverPhotos.setVisibility(View.VISIBLE);
            mUserPolaroidPhotos.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showUserDetailComponent(boolean b) {
        if (b){
            mUserCoverPhotos.setVisibility(View.VISIBLE);
            mUserPolaroidPhotos.setVisibility(View.VISIBLE);

            mUserCoverPhotos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle photoBundle = new Bundle();
                    photoBundle.putInt("photo_gallery_type", DETAIL_FLOW.USER_DETAIL_COVER_PHOTOS.getId());
                    addFragmentWithArgument(new PhotoGalleryFragment(),
                            PhotoGalleryFragment.class.getSimpleName(), photoBundle);
                }
            });

            mUserPolaroidPhotos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle photoBundle = new Bundle();
                    photoBundle.putInt("photo_gallery_type", DETAIL_FLOW.USER_DETAIL_POLAROID_PHOTOS.getId());
                    addFragmentWithArgument(new PhotoGalleryFragment(),
                            PhotoGalleryFragment.class.getSimpleName(), photoBundle);
                }
            });
        }else{
            mUserCoverPhotos.setVisibility(View.GONE);
            mUserPolaroidPhotos.setVisibility(View.GONE);
        }
    }

    private void setFragment(DETAIL_FLOW detail_flow, Bundle bundle) {
        switch (detail_flow) {

            case POLAROID_PHOTO_GALLERY: {
                Bundle photoBundle = new Bundle();
                photoBundle.putInt("photo_gallery_type", DETAIL_FLOW.POLAROID_PHOTO_GALLERY.getId());
                addFragmentWithArgument(new PhotoGalleryFragment(),
                        PhotoGalleryFragment.class.getSimpleName(), photoBundle);
            }
            break;

            case POLAROID_PHOTO: {
                addFragmentWithArgument(new UpdatePolaroidPhotoScreenFragment(),
                        UpdatePolaroidPhotoScreenFragment.class.getSimpleName(), bundle);
            }
            break;

            case PHOTO_GALLERY: {
                Bundle photoBundle = new Bundle();
                photoBundle.putInt("photo_gallery_type", DETAIL_FLOW.PHOTO_GALLERY.getId());
                addFragmentWithArgument(new PhotoGalleryFragment(),
                        PhotoGalleryFragment.class.getSimpleName(), photoBundle);
            }
            break;

            case COVER_PHOTO: {

                addFragmentWithArgument(new UpdateCoverPhotoScreenFragment(),
                        UpdateCoverPhotoScreenFragment.class.getSimpleName(), bundle);
            }
            break;

            case UPDATE_STYLE: {
                addFragmentWithArgument(new StyleScreenFragment(),
                        StyleScreenFragment.class.getSimpleName(), bundle);
            }
            break;

            case USER_DETAIL_SCREEN: {
                addFragmentWithArgument(new UserDetailScreenFragment(),
                        UserDetailScreenFragment.class.getSimpleName(), getIntent().getExtras());
            }
            break;

            case USER_DETAIL_COVER_PHOTOS: {
                Bundle photoBundle = new Bundle();
                photoBundle.putInt("photo_gallery_type", DETAIL_FLOW.USER_DETAIL_COVER_PHOTOS.getId());
                addFragmentWithArgument(new PhotoGalleryFragment(),
                        PhotoGalleryFragment.class.getSimpleName(), photoBundle);
            }
            break;

            case USER_DETAIL_POLAROID_PHOTOS: {
                Bundle photoBundle = new Bundle();
                photoBundle.putInt("photo_gallery_type", DETAIL_FLOW.USER_DETAIL_POLAROID_PHOTOS.getId());
                addFragmentWithArgument(new PhotoGalleryFragment(),
                        PhotoGalleryFragment.class.getSimpleName(), photoBundle);
            }
            break;

            case PROFILE_PIC:{
                addFragmentWithArgument(new UpdateProfilePhotoFragment(),
                        UpdateProfilePhotoFragment.class.getSimpleName(), bundle);
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
        mFragmentTransaction.addToBackStack(tag);
        mFragmentTransaction.commitAllowingStateLoss();
    }

    //remove fragment from stack.
    private void removeFragment(Fragment fragment)
    {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_left);
        mFragmentTransaction.remove(fragment);
        mFragmentTransaction.commitAllowingStateLoss();
    }

    @OnClick(R.id.back)
    public void onBackClick(){
        // If the fragment exists and has some back-stack entry
        Fragment myFragment = mFragmentManager.findFragmentById(R.id.container);
        if (myFragment != null && mFragmentManager.getBackStackEntryCount() > 1) {
            // Get the fragment fragment manager - and pop the backstack
            mFragmentManager.popBackStack();

        }
        // Else, nothing in the direct fragment back stack
        else {
            // Let super handle the back press
            finish();
        }
    }
}
