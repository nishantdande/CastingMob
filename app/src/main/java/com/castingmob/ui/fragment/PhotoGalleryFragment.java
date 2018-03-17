package com.castingmob.ui.fragment;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 26/01/16
 ==============================================
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.castingmob.R;
import com.castingmob.account.model.Profile;
import com.castingmob.account.model.UserDetail;
import com.castingmob.ui.activity.DetailActivity;
import com.castingmob.ui.adapter.PhotosAdapter;
import com.castingmob.ui.view.CastingPhotosRecycleView;
import com.castingmob.ui.view.ImageSliderView;
import com.castingmob.utils.PhotoItemParsing;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotoGalleryFragment extends BaseFragment implements BaseSliderView.OnSliderClickListener {

    public interface onPhotosCountEventListener {
        void getPhotosCount(int count);
        void showPhotoCount(boolean b);
    }

    @Bind(R.id.photos)
    CastingPhotosRecycleView mPhotoGallery;

    @Bind(R.id.slider_image)
    RelativeLayout mImageSlider;
    @Bind(R.id.slider)
    SliderLayout mSliderLayout;
    PhotosAdapter photosAdapter;

    List<String> photoUrls = new ArrayList<>();

    private onPhotosCountEventListener mPhotosCountEventListener;
    int position = 0;

    int mPhotogalleryType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null){
            mPhotogalleryType = bundle.getInt("photo_gallery_type");

            if (DetailActivity.DETAIL_FLOW.getScreen(mPhotogalleryType)
                    == DetailActivity.DETAIL_FLOW.PHOTO_GALLERY){
                photoUrls.addAll(PhotoItemParsing.getPhotoUrls(Profile.getInstance().getProfilePhotos()));
                photoUrls.addAll(PhotoItemParsing.getPhotoUrls(Profile.getInstance().getCoverPhotos()));
            }else if(DetailActivity.DETAIL_FLOW.getScreen(mPhotogalleryType)
                    == DetailActivity.DETAIL_FLOW.POLAROID_PHOTO_GALLERY){
                photoUrls.addAll(PhotoItemParsing.getPhotoUrls(Profile.getInstance().getPolaroidsPhotos()));
            }
            else if(DetailActivity.DETAIL_FLOW.getScreen(mPhotogalleryType)
                    == DetailActivity.DETAIL_FLOW.USER_DETAIL_COVER_PHOTOS){
                photoUrls.addAll(PhotoItemParsing.getPhotoUrls(UserDetail.getInstance().getProfilePhotos()));
                photoUrls.addAll(PhotoItemParsing.getPhotoUrls(UserDetail.getInstance().getCoverPhotos()));
            }
            else if(DetailActivity.DETAIL_FLOW.getScreen(mPhotogalleryType)
                    == DetailActivity.DETAIL_FLOW.USER_DETAIL_POLAROID_PHOTOS){
                photoUrls.addAll(PhotoItemParsing.getPhotoUrls(UserDetail.getInstance().getPolaroidsPhotos()));
            }
        }
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo_gallery, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        photosAdapter = PhotosAdapter.getInstance();
        photosAdapter.setPhotos(photoUrls);
        mPhotoGallery.setAdapter(photosAdapter);
        mPhotoGallery.setItemClickListener(onItemClickListener);

        if (mPhotosCountEventListener != null) {
            mPhotosCountEventListener.showPhotoCount(true);
            mPhotosCountEventListener.getPhotosCount(photoUrls.size());
        }

        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        mSliderLayout.stopAutoCycle();

        for(String name : photoUrls){
            ImageSliderView imageSliderView = new ImageSliderView(getActivity());
            imageSliderView.image(name).setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            imageSliderView.bundle(new Bundle());
            imageSliderView.getBundle()
                    .putString("extra",name);

            mSliderLayout.addSlider(imageSliderView);
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mPhotosCountEventListener = (onPhotosCountEventListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSomeEventListener");
        }
    }

    CastingPhotosRecycleView.OnItemClickListener onItemClickListener = new CastingPhotosRecycleView.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            mPhotoGallery.setVisibility(View.GONE);
            mImageSlider.setVisibility(View.VISIBLE);
            mSliderLayout.setCurrentPosition(position);
        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    };

    @OnClick(R.id.before)
    public void onBefore(){
        mSliderLayout.movePrevPosition();
    }

    @OnClick(R.id.next)
    public void onNext(){
        mSliderLayout.moveNextPosition();
    }

    @OnClick(R.id.image_grid)
    public void onGrid(){
        mPhotoGallery.setVisibility(View.VISIBLE);
        mImageSlider.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

        if (mPhotosCountEventListener != null){
            mPhotosCountEventListener.showPhotoCount(false);
        }
    }
}
