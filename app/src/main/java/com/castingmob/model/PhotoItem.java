
package com.castingmob.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoItem {

    @SerializedName("photoId")
    @Expose
    private Integer photoId;
    @SerializedName("photoUrl")
    @Expose
    private String photoUrl;

    /**
     * 
     * @return
     *     The photoId
     */
    public Integer getPhotoId() {
        return photoId;
    }

    /**
     * 
     * @param photoId
     *     The photoId
     */
    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
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

    @Override
    public String toString() {
        return "PhotoItem{" +
                "photoId=" + photoId +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}
