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

import com.castingmob.Feed.model.Comment;
import com.google.gson.annotations.SerializedName;


public class Feed implements Parcelable {
    @SerializedName("photoType")
    private String photoType;
    @SerializedName("numberOfComments")
    private String numberOfComments;
    @SerializedName("lastUpdatedTime")
    private String lastUpdatedTime;
    @SerializedName("userToken")
    private String userToken;
    @SerializedName("photoId")
    private String photoId;
    @SerializedName("likes")
    private String[] likes;
    @SerializedName("lastComment")
    private Comment lastComment;
    @SerializedName("userDisplayName")
    private String userDisplayName;
    @SerializedName("photoUrl")
    private String photoUrl;


    public String getPhotoType() {
        return photoType;
    }

    public void setPhotoType(String photoType) {
        this.photoType = photoType;
    }

    public String getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(String numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String[] getLikes() {
        return likes;
    }

    public void setLikes(String[] likes) {
        this.likes = likes;
    }

    public Comment getLastComment() {
        return lastComment;
    }

    public void setLastComment(Comment lastComment) {
        this.lastComment = lastComment;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.photoType);
        dest.writeString(this.numberOfComments);
        dest.writeString(this.lastUpdatedTime);
        dest.writeString(this.userToken);
        dest.writeString(this.photoId);
        dest.writeStringArray(this.likes);
        dest.writeParcelable(this.lastComment, flags);
        dest.writeString(this.userDisplayName);
        dest.writeString(this.photoUrl);
    }

    protected Feed(Parcel in) {
        this.photoType = in.readString();
        this.numberOfComments = in.readString();
        this.lastUpdatedTime = in.readString();
        this.userToken = in.readString();
        this.photoId = in.readString();
        this.likes = in.createStringArray();
        this.lastComment = in.readParcelable(Comment.class.getClassLoader());
        this.userDisplayName = in.readString();
        this.photoUrl = in.readString();
    }

    public static final Parcelable.Creator<Feed> CREATOR = new Parcelable.Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel source) {
            return new Feed(source);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };
}
