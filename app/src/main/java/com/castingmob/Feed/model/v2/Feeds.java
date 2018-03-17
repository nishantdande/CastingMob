package com.castingmob.Feed.model.v2;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 14/09/16
 ==============================================
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.castingmob.Feed.model.FeedItem;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class Feeds implements Parcelable {

    @SerializedName("currentFeedCount")
    private String currentFeedCount;
    @SerializedName("categoryUserType")
    private String categoryUserType;
    @SerializedName("feed")
    private ArrayList<FeedItem> feed;

    public String getCurrentFeedCount() {
        return currentFeedCount;
    }

    public void setCurrentFeedCount(String currentFeedCount) {
        this.currentFeedCount = currentFeedCount;
    }

    public String getCategoryUserType() {
        return categoryUserType;
    }

    public void setCategoryUserType(String categoryUserType) {
        this.categoryUserType = categoryUserType;
    }

    public ArrayList<FeedItem> getFeed() {
        return feed;
    }

    public void setFeed(ArrayList<FeedItem> feed) {
        this.feed = feed;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.currentFeedCount);
        dest.writeString(this.categoryUserType);
        dest.writeTypedList(this.feed);
    }

    public Feeds() {
    }

    protected Feeds(Parcel in) {
        this.currentFeedCount = in.readString();
        this.categoryUserType = in.readString();
        this.feed = in.createTypedArrayList(FeedItem.CREATOR);
    }

    public static final Parcelable.Creator<Feeds> CREATOR = new Parcelable.Creator<Feeds>() {
        @Override
        public Feeds createFromParcel(Parcel source) {
            return new Feeds(source);
        }

        @Override
        public Feeds[] newArray(int size) {
            return new Feeds[size];
        }
    };
}
