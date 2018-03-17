package com.castingmob.Feed.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedItem implements Parcelable {

    @SerializedName("photoId")
    @Expose
    private String photoId;
    @SerializedName("photoType")
    @Expose
    private String photoType;
    @SerializedName("photoUrl")
    @Expose
    private String photoUrl;
    @SerializedName("userDisplayName")
    @Expose
    private String userDisplayName;
    @SerializedName("userToken")
    @Expose
    private String userToken;
    @SerializedName("likes")
    @Expose
    private List<String> likes = new ArrayList<String>();
    @SerializedName("lastUpdatedTime")
    @Expose
    private long lastUpdatedTime;
    @SerializedName("numberOfComments")
    @Expose
    private Integer numberOfComments;
    @SerializedName("lastComment")
    @Expose
    private Comment lastComment;

    /**
     * 
     * @return
     *     The photoId
     */
    public String getPhotoId() {
        return photoId;
    }

    /**
     * 
     * @param photoId
     *     The photoId
     */
    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    /**
     * 
     * @return
     *     The photoType
     */
    public String getPhotoType() {
        return photoType;
    }

    /**
     * 
     * @param photoType
     *     The photoType
     */
    public void setPhotoType(String photoType) {
        this.photoType = photoType;
    }

    /**
     * 
     * @return
     *     The photoUrl
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * 
     * @param photoUrl
     *     The photoUrl
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    /**
     * 
     * @return
     *     The userDisplayName
     */
    public String getUserDisplayName() {
        return userDisplayName;
    }

    /**
     * 
     * @param userDisplayName
     *     The userDisplayName
     */
    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

    /**
     * 
     * @return
     *     The userToken
     */
    public String getUserToken() {
        return userToken;
    }

    /**
     * 
     * @param userToken
     *     The userToken
     */
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    /**
     * 
     * @return
     *     The likes
     */
    public List<String> getLikes() {
        return likes;
    }

    /**
     * 
     * @param likes
     *     The likes
     */
    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    /**
     * 
     * @return
     *     The lastUpdatedTime
     */
    public long getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    /**
     * 
     * @param lastUpdatedTime
     *     The lastUpdatedTime
     */
    public void setLastUpdatedTime(Integer lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    /**
     * 
     * @return
     *     The numberOfComments
     */
    public Integer getNumberOfComments() {
        return numberOfComments;
    }

    /**
     * 
     * @param numberOfComments
     *     The numberOfComments
     */
    public void setNumberOfComments(Integer numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    /**
     * 
     * @return
     *     The lastComment
     */
    public Comment getLastComment() {
        return lastComment;
    }

    /**
     * 
     * @param lastComment
     *     The lastComment
     */
    public void setLastComment(Comment lastComment) {
        this.lastComment = lastComment;
    }


    @Override
    public String toString() {
        return "FeedItem{" +
                "photoId='" + photoId + '\'' +
                ", photoType='" + photoType + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", userDisplayName='" + userDisplayName + '\'' +
                ", userToken='" + userToken + '\'' +
                ", likes=" + likes +
                ", lastUpdatedTime=" + lastUpdatedTime +
                ", numberOfComments=" + numberOfComments +
                ", lastComment=" + lastComment +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.photoId);
        dest.writeString(this.photoType);
        dest.writeString(this.photoUrl);
        dest.writeString(this.userDisplayName);
        dest.writeString(this.userToken);
        dest.writeStringList(this.likes);
        dest.writeLong(this.lastUpdatedTime);
        dest.writeValue(this.numberOfComments);
        dest.writeParcelable(this.lastComment, 0);
    }

    public FeedItem() {
    }

    protected FeedItem(Parcel in) {
        this.photoId = in.readString();
        this.photoType = in.readString();
        this.photoUrl = in.readString();
        this.userDisplayName = in.readString();
        this.userToken = in.readString();
        this.likes = in.createStringArrayList();
        this.lastUpdatedTime = in.readLong();
        this.numberOfComments = (Integer) in.readValue(Integer.class.getClassLoader());
        this.lastComment = in.readParcelable(Comment.class.getClassLoader());
    }

    public static final Parcelable.Creator<FeedItem> CREATOR = new Parcelable.Creator<FeedItem>() {
        public FeedItem createFromParcel(Parcel source) {
            return new FeedItem(source);
        }

        public FeedItem[] newArray(int size) {
            return new FeedItem[size];
        }
    };
}
