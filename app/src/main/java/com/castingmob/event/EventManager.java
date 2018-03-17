package com.castingmob.event;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 15/09/16
 ==============================================
 */

import android.os.Bundle;

import com.castingmob.casting.model.CastingItem;
import com.castingmob.ui.fragment.home.CastingContainerFragment;
import com.castingmob.ui.fragment.home.HomeScreenContainerFragment;
import com.castingmob.ui.fragment.home.MessageFragment;
import com.castingmob.ui.fragment.profile.ProfileContainerFragment;
import com.castingmob.ui.fragment.signup.ProfileUpdateContainerFragment;

import java.util.List;

public class   EventManager {

    private ProfileContainerFragment.PROFILE_ACTIVITY profileActivity;
    private ProfileUpdateContainerFragment.PROFILE_ACTIVITY profileUpdateActivity;
    private HomeScreenContainerFragment.FEED_ACTIVITY feedActivity;
    private CastingContainerFragment.CASTING_ACTIVITY castingActivity;
    private MessageFragment.CHAT chatActivity;
    private Object object;
    private Bundle bundle;

    private List<CastingItem> castingItems;

    public CastingContainerFragment.CASTING_ACTIVITY getCastingActivity() {
        return castingActivity;
    }

    public EventManager(CastingContainerFragment.CASTING_ACTIVITY castingActivity) {
        this.castingActivity = castingActivity;
    }

    public EventManager(List<CastingItem> castingItems) {
        this.castingItems = castingItems;
    }

    public EventManager(Object object) {
        this.object = object;
    }

    public EventManager(ProfileContainerFragment.PROFILE_ACTIVITY activity) {
        this.profileActivity = activity;
    }

    public EventManager(ProfileUpdateContainerFragment.PROFILE_ACTIVITY activity) {
        this.profileUpdateActivity = activity;
    }

    public EventManager(HomeScreenContainerFragment.FEED_ACTIVITY activity) {
        this.feedActivity = activity;
    }

    public EventManager(MessageFragment.CHAT chatActivity, Bundle bundle) {
        this.chatActivity = chatActivity;
        this.bundle = bundle;
    }

    public ProfileContainerFragment.PROFILE_ACTIVITY getProfileActivity() {
        return profileActivity;
    }

    public ProfileUpdateContainerFragment.PROFILE_ACTIVITY getProfileUpdateActivity() {
        return profileUpdateActivity;
    }

    public HomeScreenContainerFragment.FEED_ACTIVITY getFeedActivity() {
        return  feedActivity;
    }

    public MessageFragment.CHAT getChatActivity() {
        return chatActivity;
    }

    public Object getObject() {
        return object;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public List<CastingItem> getCastingItems() {
        return castingItems;
    }
}
