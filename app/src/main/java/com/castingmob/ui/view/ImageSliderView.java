package com.castingmob.ui.view;
/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 27/01/16
 ==============================================
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.castingmob.R;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;

public class ImageSliderView extends BaseSliderView {
    public ImageSliderView(Context context) {
        super(context);
    }

    /**
     * the extended class have to implement getView(), which is called by the adapter,
     * every extended class response to render their own view.
     *
     * @return
     */
    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.sample,null);
        ImageView target = (ImageView)v.findViewById(com.daimajia.slider.library.R.id.daimajia_slider_image);
//        target.setRotation(90f);
        target.setScaleType(ImageView.ScaleType.FIT_XY);
        bindEventAndShow(v, target);
        return v;
    }
}
