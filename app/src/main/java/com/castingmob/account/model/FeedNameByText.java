package com.castingmob.account.model;
/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 31/01/16
 ==============================================
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedNameByText {

    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("userToken")
    @Expose
    private String userToken;
    @SerializedName("displayName")
    @Expose
    private String displayName;
    @SerializedName("photoUrl")
    @Expose
    private String photoUrl;

    /**
     * @return The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName The firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName The lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return The userToken
     */
    public String getUserToken() {
        return userToken;
    }

    /**
     * @param userToken The userToken
     */
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    /**
     * @return The displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName The displayName
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return The photoUrl
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * @param photoUrl The photoUrl
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

}