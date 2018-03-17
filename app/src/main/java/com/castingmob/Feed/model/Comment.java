
package com.castingmob.Feed.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment implements Parcelable {

    @SerializedName("commentId")
    @Expose
    private Integer commentId;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("commentorName")
    @Expose
    private String commentorName;
    @SerializedName("commentorToken")
    @Expose
    private String commentorToken;
    @SerializedName("commentTime")
    @Expose
    private Long commentTime;
    @SerializedName("commentorPhotoUrl")
    @Expose
    private String commentorPhotoUrl;

    /**
     * 
     * @return
     *     The commentId
     */
    public Integer getCommentId() {
        return commentId;
    }

    /**
     * 
     * @param commentId
     *     The commentId
     */
    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    /**
     * 
     * @return
     *     The text
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text
     *     The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 
     * @return
     *     The commentorName
     */
    public String getCommentorName() {
        return commentorName;
    }

    /**
     * 
     * @param commentorName
     *     The commentorName
     */
    public void setCommentorName(String commentorName) {
        this.commentorName = commentorName;
    }

    /**
     * 
     * @return
     *     The commentorToken
     */
    public String getCommentorToken() {
        return commentorToken;
    }

    /**
     * 
     * @param commentorToken
     *     The commentorToken
     */
    public void setCommentorToken(String commentorToken) {
        this.commentorToken = commentorToken;
    }

    /**
     * 
     * @return
     *     The commentTime
     */
    public Long getCommentTime() {
        return commentTime;
    }

    /**
     * 
     * @param commentTime
     *     The commentTime
     */
    public void setCommentTime(Long commentTime) {
        this.commentTime = commentTime;
    }

    /**
     * 
     * @return
     *     The commentorPhotoUrl
     */
    public String getCommentorPhotoUrl() {
        return commentorPhotoUrl;
    }

    /**
     * 
     * @param commentorPhotoUrl
     *     The commentorPhotoUrl
     */
    public void setCommentorPhotoUrl(String commentorPhotoUrl) {
        this.commentorPhotoUrl = commentorPhotoUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.commentId);
        dest.writeString(this.text);
        dest.writeString(this.commentorName);
        dest.writeString(this.commentorToken);
        dest.writeValue(this.commentTime);
        dest.writeString(this.commentorPhotoUrl);
    }

    public Comment() {
    }

    protected Comment(Parcel in) {
        this.commentId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.text = in.readString();
        this.commentorName = in.readString();
        this.commentorToken = in.readString();
        this.commentTime = (Long) in.readValue(Long.class.getClassLoader());
        this.commentorPhotoUrl = in.readString();
    }

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
