
package com.castingmob.casting.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.castingmob.account.model.Profile;
import com.castingmob.database.model.Conversation;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CastingItem implements Parcelable {



    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("minAge")
    @Expose
    private Integer minAge;
    @SerializedName("maxAge")
    @Expose
    private Integer maxAge;
    @SerializedName("gender")
    @Expose
    private Profile.Gender gender;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("ownerToken")
    @Expose
    private String ownerToken;
    @SerializedName("postedOn")
    @Expose
    private Long postedOn;
    @SerializedName("updatedOn")
    @Expose
    private Long updatedOn;
    @SerializedName("appliedOn")
    @Expose
    private Long appliedOn;
    @SerializedName("photoUrl")
    @Expose
    private String photoUrl;
    @SerializedName("conversation")
    @Expose
    private Conversation conversation;

    @SerializedName("userType")
    @Expose
    private String userType;

    @SerializedName("endDate")
    @Expose
    private String endDate;

    @SerializedName("startDate")
    @Expose
    private String startDate;

    @SerializedName("paid")
    @Expose
    private Boolean paid;

    @SerializedName("pay")
    @Expose
    private String pay;

    @SerializedName("applications")
    @Expose
    private List<Object> applications = new ArrayList<Object>();

    public String getLocation(){
        return String.format("%s,%s,%s", city, state, country);
    }


    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String  description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The minAge
     */
    public Integer getMinAge() {
        return minAge;
    }

    /**
     * 
     * @param minAge
     *     The minAge
     */
    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    /**
     * 
     * @return
     *     The maxAge
     */
    public Integer getMaxAge() {
        return maxAge;
    }

    /**
     * 
     * @param maxAge
     *     The maxAge
     */
    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    /**
     * 
     * @return
     *     The gender
     */
    public Profile.Gender getGender() {
        return gender;
    }

    /**
     * 
     * @param gender
     *     The gender
     */
    public void setGender(Profile.Gender gender) {
        this.gender = gender;
    }

    /**
     * 
     * @return
     *     The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * 
     * @param country
     *     The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 
     * @return
     *     The state
     */
    public String getState() {
        return state;
    }

    /**
     * 
     * @param state
     *     The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 
     * @return
     *     The city
     */
    public String getCity() {
        return city;
    }

    /**
     * 
     * @param city
     *     The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 
     * @return
     *     The ownerToken
     */
    public String getOwnerToken() {
        return ownerToken;
    }

    /**
     * 
     * @param ownerToken
     *     The ownerToken
     */
    public void setOwnerToken(String ownerToken) {
        this.ownerToken = ownerToken;
    }

    /**
     * 
     * @return
     *     The postedOn
     */
    public Long getPostedOn() {
        return postedOn;
    }

    /**
     * 
     * @param postedOn
     *     The postedOn
     */
    public void setPostedOn(Long postedOn) {
        this.postedOn = postedOn;
    }

    /**
     * 
     * @return
     *     The updatedOn
     */
    public Long getUpdatedOn() {
        return updatedOn;
    }

    /**
     * 
     * @param updatedOn
     *     The updatedOn
     */
    public void setUpdatedOn(Long updatedOn) {
        this.updatedOn = updatedOn;
    }

    /**
     * 
     * @return
     *     The appliedOn
     */
    public Long getAppliedOn() {
        return appliedOn;
    }

    /**
     * 
     * @param appliedOn
     *     The appliedOn
     */
    public void setAppliedOn(Long appliedOn) {
        this.appliedOn = appliedOn;
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
     *     The conversation
     */
    public Conversation getConversation() {
        return conversation;
    }

    /**
     * 
     * @param conversation
     *     The conversation
     */
    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    /**
     * 
     * @return
     *     The applications
     */
    public List<Object> getApplications() {
        return applications;
    }

    /**
     * 
     * @param applications
     *     The applications
     */
    public void setApplications(List<Object> applications) {
        this.applications = applications;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeValue(this.minAge);
        dest.writeValue(this.maxAge);
        dest.writeInt(this.gender == null ? -1 : this.gender.ordinal());
        dest.writeString(this.country);
        dest.writeString(this.state);
        dest.writeString(this.city);
        dest.writeString(this.ownerToken);
        dest.writeValue(this.postedOn);
        dest.writeValue(this.updatedOn);
        dest.writeValue(this.appliedOn);
        dest.writeString(this.photoUrl);
        dest.writeString(this.userType);
        dest.writeString(this.endDate);
        dest.writeString(this.startDate);
        dest.writeValue(this.paid);
        dest.writeString(this.pay);
        dest.writeList(this.applications);
    }

    public CastingItem() {
    }

    protected CastingItem(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.description = in.readString();
        this.minAge = (Integer) in.readValue(Integer.class.getClassLoader());
        this.maxAge = (Integer) in.readValue(Integer.class.getClassLoader());
        int tmpGender = in.readInt();
        this.gender = tmpGender == -1 ? null : Profile.Gender.values()[tmpGender];
        this.country = in.readString();
        this.state = in.readString();
        this.city = in.readString();
        this.ownerToken = in.readString();
        this.postedOn = (Long) in.readValue(Long.class.getClassLoader());
        this.updatedOn = (Long) in.readValue(Long.class.getClassLoader());
        this.appliedOn = (Long) in.readValue(Long.class.getClassLoader());
        this.photoUrl = in.readString();
        this.userType = in.readString();
        this.endDate = in.readString();
        this.startDate = in.readString();
        this.paid = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.pay = in.readString();
        this.applications = new ArrayList<Object>();
        in.readList(this.applications, Object.class.getClassLoader());
    }

    public static final Parcelable.Creator<CastingItem> CREATOR = new Parcelable.Creator<CastingItem>() {
        @Override
        public CastingItem createFromParcel(Parcel source) {
            return new CastingItem(source);
        }

        @Override
        public CastingItem[] newArray(int size) {
            return new CastingItem[size];
        }
    };
}
