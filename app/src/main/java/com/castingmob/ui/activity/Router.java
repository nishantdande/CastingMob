package com.castingmob.ui.activity;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 24/01/16
 ==============================================
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.castingmob.R;

public class Router {

    public static int REQUEST_CODE = 1000;

    /**
     * Launch Home Screen Activity
     * @param activity
     */
    public static void startHomeScreenActivity(Activity activity){
        Intent intent= new Intent(activity,NewHomeScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        activity.startActivity(intent);
    }

    /**
     * Start Detail Activity
     * @param activity
     */
    public static void startDetailScreenActivity(Activity activity, int screenId){
        Bundle bundle = new Bundle();
        bundle.putInt("screenId", screenId);
        Intent intent= new Intent(activity,DetailActivity.class);
        intent.putExtras(bundle);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        activity.startActivity(intent);
    }

    /**
     * Start Detail Activity
     * @param activity
     */
    public static void startDetailScreenActivity(Activity activity,Bundle bundle){
        Intent intent= new Intent(activity,DetailActivity.class);
        intent.putExtras(bundle);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        activity.startActivity(intent);
    }

    /**
     * Start News Feed Activity
     * @param activity
     */
    public static void startNewsFeedScreenActivity(Activity activity,Bundle bundle){
        Intent intent= new Intent(activity,NewsFeedActivity.class);
        if (bundle != null)
            intent.putExtras(bundle);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        activity.startActivity(intent);
    }

    /**
     * Start Filter Activity
     * @param activity
     */
    public static void startFilterActivity(Activity activity,Bundle bundle){
        Intent intent= new Intent(activity,FilterActivity.class);
        if (bundle != null)
            intent.putExtras(bundle);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    /**
     * Start Search Casting Activity
     * @param activity
     */
    public static void startSearchCastingActivity(Activity activity,Bundle bundle){
        Intent intent= new Intent(activity,SearchCastingActivity.class);
        if (bundle != null)
            intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    /**
     * Start Location Filter Activity
     * @param activity
     */
    public static void startLocationFilterActivity(Activity activity,Bundle bundle){
        Intent intent= new Intent(activity,LocationFilterActivity.class);
        if (bundle != null)
            intent.putExtras(bundle);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }


    /**
     * Start Edit Profile Activity
     * @param activity
     */
    public static void startEditProfileActivity(Activity activity, Fragment fragment, Bundle bundle){
        Intent intent= new Intent(activity,EditProfileActivity.class);
        if (bundle != null)
            intent.putExtras(bundle);
        fragment.startActivityForResult(intent, REQUEST_CODE);
    }

    /**
     * Start Casting Detail Activity
     * @param activity
     */
    public static void startCastingDetailsActivity(Activity activity,Bundle bundle){
        Intent intent= new Intent(activity,CastingDetailActivity.class);
        if (bundle != null)
            intent.putExtras(bundle);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        activity.startActivity(intent);
    }

    /**
     * Start Send Message Activity
     * @param activity
     */
    public static void startSendMessageActivity(Activity activity,Bundle bundle){
        Intent intent= new Intent(activity,SendMessageActivity.class);
        if (bundle != null)
            intent.putExtras(bundle);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        activity.startActivity(intent);
    }

    /**
     * Start Filter Activity
     * @param activity
     */
    public static void startCastingFilterActivity(Activity activity,Bundle bundle){
        Intent intent= new Intent(activity,FilterCastingActivity.class);
        if (bundle != null)
            intent.putExtras(bundle);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    /**
     * Start Filter Activity
     * @param activity
     */
    public static void startAddCastingActivity(Activity activity,Bundle bundle){
        Intent intent= new Intent(activity,AddCastingActivity.class);
        if (bundle != null)
            intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    /**
     * Start Search Casting Activity
     * @param activity
     */
    public static void startOwnCastingActivity(Activity activity,Bundle bundle){
        Intent intent= new Intent(activity,OwnCastingActivity.class);
        if (bundle != null)
            intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    /**
     * Start Applied Casting Activity
     * @param activity
     */
    public static void startAppliedCastingActivity(Activity activity,Bundle bundle){
        Intent intent= new Intent(activity,AppliedCastingActivity.class);
        if (bundle != null)
            intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    /**
     * Start Message Activity
     * @param activity
     */
    public static void startMessageActivity(Activity activity,Bundle bundle){
        Intent intent= new Intent(activity,MessageActivity.class);
        if (bundle != null)
            intent.putExtras(bundle);
        activity.startActivity(intent);
    }
}
