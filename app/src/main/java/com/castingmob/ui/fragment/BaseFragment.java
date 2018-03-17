package com.castingmob.ui.fragment;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 15/01/16
 ==============================================
 */

import android.support.v4.app.Fragment;

import com.castingmob.logger.Logger;
import com.castingmob.ui.activity.CastingMobActivity;

public class BaseFragment extends Fragment {

    public final Logger logger = new Logger(CastingMobActivity.class.getSimpleName());

}
