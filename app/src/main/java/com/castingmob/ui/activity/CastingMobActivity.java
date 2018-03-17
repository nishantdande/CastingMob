package com.castingmob.ui.activity;
/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description : Base Activity Class which has common feature
 Date        : 14/01/16
 ==============================================
 */

import android.support.v7.app.AppCompatActivity;

import com.castingmob.logger.Logger;

public class CastingMobActivity extends AppCompatActivity {

    public final Logger logger = new Logger(CastingMobActivity.class.getSimpleName());

}
