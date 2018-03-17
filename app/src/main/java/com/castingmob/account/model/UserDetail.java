package com.castingmob.account.model;

import com.castingmob.model.PhotoItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class UserDetail {

    private static UserDetail userDetail = new UserDetail();

    private UserDetail(){}

//    public enum Gender{
//        male,
//        female
//    }

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("displayName")
    @Expose
    private String displayName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("gender")
    @Expose
    private Profile.Gender gender;
    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("coverPhoto")
    @Expose
    private String coverPhoto;
    @SerializedName("profilePhoto")
    @Expose
    private String profilePhoto;
    @SerializedName("userType")
    @Expose
    private String userType;
    @SerializedName("eyeColor")
    @Expose
    private String eyeColor;
    @SerializedName("hairColor")
    @Expose
    private String hairColor;
    @SerializedName("userSubType")
    @Expose
    private String userSubType;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("ethnicity")
    @Expose
    private String ethnicity;
    @SerializedName("chest")
    @Expose
    private Integer chest;
    @SerializedName("waist")
    @Expose
    private Integer waist;
    @SerializedName("hip")
    @Expose
    private Integer hip;

    @SerializedName("min_hourrate")
    @Expose
    private String min_hourrate;

    @SerializedName("max_hourrate")
    @Expose
    private String max_hourrate;

    @SerializedName("min_halfdayrate")
    @Expose
    private String min_halfdayrate;

    @SerializedName("max_halfdayrate")
    @Expose
    private String max_halfdayrate;

    @SerializedName("min_fulldayrate")
    @Expose
    private String min_fulldayrate;

    @SerializedName("max_fulldayrate")
    @Expose
    private String max_fulldayrate;

    @SerializedName("max_travel")
    @Expose
    private String max_travel;

    @SerializedName("will_travel")
    @Expose
    private Boolean will_travel;

    @SerializedName("isPublished")
    @Expose
    private Boolean isPublished;

    @SerializedName("isPromoted")
    @Expose
    private Boolean isPromoted;

    @SerializedName("profilePhotos")
    @Expose
    private List<PhotoItem> profilePhotos = new ArrayList<PhotoItem>();
    @SerializedName("coverPhotos")
    @Expose
    private List<PhotoItem> coverPhotos = new ArrayList<PhotoItem>();
    @SerializedName("polaroidsPhotos")
    @Expose
    private List<PhotoItem> polaroidsPhotos = new ArrayList<PhotoItem>();

    public static UserDetail getInstance() {
        return userDetail;
    }

    public void setUserDetail(UserDetail profileObj){
        userDetail = profileObj;
    }

    public UserDetail(String token, String firstName, String lastName,
                      String displayName, Profile.Gender gender, String dateOfBirth,
                      String city, String country, String eyeColor, String hairColor,
                      Integer height, Integer weight, Integer chest, Integer waist, Integer hip, String ethnicity) {
        this.token = token;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.country = country;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.height = height;
        this.weight = weight;
        this.ethnicity = ethnicity;
        this.chest = chest;
        this.waist = waist;
        this.hip = hip;
    }

    public String getMin_hourrate() {
        return min_hourrate;
    }

    public void setMin_hourrate(String min_hourrate) {
        this.min_hourrate = min_hourrate;
    }

    public String getMax_hourrate() {
        return max_hourrate;
    }

    public void setMax_hourrate(String max_hourrate) {
        this.max_hourrate = max_hourrate;
    }

    public String getMin_halfdayrate() {
        return min_halfdayrate;
    }

    public void setMin_halfdayrate(String min_halfdayrate) {
        this.min_halfdayrate = min_halfdayrate;
    }

    public String getMax_halfdayrate() {
        return max_halfdayrate;
    }

    public void setMax_halfdayrate(String max_halfdayrate) {
        this.max_halfdayrate = max_halfdayrate;
    }

    public String getMin_fulldayrate() {
        return min_fulldayrate;
    }

    public String getMax_fulldayrate() {
        return max_fulldayrate;
    }

    public void setMax_fulldayrate(String max_fulldayrate) {
        this.max_fulldayrate = max_fulldayrate;
    }

    public void setMin_fulldayrate(String min_fulldayrate) {
        this.min_fulldayrate = min_fulldayrate;
    }

    public String getMax_travel() {
        return max_travel;
    }

    public void setMax_travel(String max_travel) {
        this.max_travel = max_travel;
    }

    public Boolean getWill_travel() {
        return will_travel;
    }

    public void setWill_travel(Boolean will_travel) {
        this.will_travel = will_travel;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    public Boolean getPromoted() {
        return isPromoted;
    }

    public void setPromoted(Boolean promoted) {
        isPromoted = promoted;
    }

    /**
     * 
     * @return
     *     The token
     */
    public String getToken() {
        return token;
    }

    /**
     * 
     * @param token
     *     The token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 
     * @return
     *     The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * 
     * @param firstName
     *     The firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * 
     * @return
     *     The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * 
     * @param lastName
     *     The lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * 
     * @return
     *     The displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * 
     * @param displayName
     *     The displayName
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * 
     * @return
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return
     *     The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @param password
     *     The password
     */
    public void setPassword(String password) {
        this.password = password;
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
     *     The dateOfBirth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * 
     * @param dateOfBirth
     *     The dateOfBirth
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
     *     The coverPhoto
     */
    public String getCoverPhoto() {
        return coverPhoto;
    }

    /**
     * 
     * @param coverPhoto
     *     The coverPhoto
     */
    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    /**
     * 
     * @return
     *     The profilePhoto
     */
    public String getProfilePhoto() {
        return profilePhoto;
    }

    /**
     * 
     * @param profilePhoto
     *     The profilePhoto
     */
    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    /**
     * 
     * @return
     *     The userType
     */
    public String getUserType() {
        return userType;
    }

    /**
     * 
     * @param userType
     *     The userType
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * 
     * @return
     *     The eyeColor
     */
    public String getEyeColor() {
        return eyeColor;
    }

    /**
     * 
     * @param eyeColor
     *     The eyeColor
     */
    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    /**
     * 
     * @return
     *     The hairColor
     */
    public String getHairColor() {
        return hairColor;
    }

    /**
     * 
     * @param hairColor
     *     The hairColor
     */
    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    /**
     * 
     * @return
     *     The userSubType
     */
    public String getUserSubType() {
        return userSubType;
    }

    /**
     * 
     * @param userSubType
     *     The userSubType
     */
    public void setUserSubType(String userSubType) {
        this.userSubType = userSubType;
    }

    /**
     * 
     * @return
     *     The website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * 
     * @param website
     *     The website
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * 
     * @return
     *     The height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * 
     * @param height
     *     The height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * 
     * @return
     *     The weight
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * 
     * @param weight
     *     The weight
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * 
     * @return
     *     The ethnicity
     */
    public String getEthnicity() {
        return ethnicity;
    }

    /**
     * 
     * @param ethnicity
     *     The ethnicity
     */
    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    /**
     * 
     * @return
     *     The chest
     */
    public Integer getChest() {
        return chest;
    }

    /**
     * 
     * @param chest
     *     The chest
     */
    public void setChest(Integer chest) {
        this.chest = chest;
    }

    /**
     * 
     * @return
     *     The waist
     */
    public Integer getWaist() {
        return waist;
    }

    /**
     * 
     * @param waist
     *     The waist
     */
    public void setWaist(Integer waist) {
        this.waist = waist;
    }

    /**
     * 
     * @return
     *     The hip
     */
    public Integer getHip() {
        return hip;
    }

    /**
     * 
     * @param hip
     *     The hip
     */
    public void setHip(Integer hip) {
        this.hip = hip;
    }

    /**
     * 
     * @return
     *     The profilePhotos
     */
    public List<PhotoItem> getProfilePhotos() {
        return profilePhotos;
    }

    /**
     * 
     * @param profilePhotos
     *     The profilePhotos
     */
    public void setProfilePhotos(List<PhotoItem> profilePhotos) {
        this.profilePhotos = profilePhotos;
    }

    /**
     * 
     * @return
     *     The coverPhotos
     */
    public List<PhotoItem> getCoverPhotos() {
        return coverPhotos;
    }

    /**
     * 
     * @param coverPhotos
     *     The coverPhotos
     */
    public void setCoverPhotos(List<PhotoItem> coverPhotos) {
        this.coverPhotos = coverPhotos;
    }

    /**
     * 
     * @return
     *     The polaroidsPhotos
     */
    public List<PhotoItem> getPolaroidsPhotos() {
        return polaroidsPhotos;
    }

    /**
     * 
     * @param polaroidsPhotos
     *     The polaroidsPhotos
     */
    public void setPolaroidsPhotos(List<PhotoItem> polaroidsPhotos) {
        this.polaroidsPhotos = polaroidsPhotos;
    }

    @Override
    public String toString() {
        return "UserDetail{" +
                "token='" + token + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", coverPhoto='" + coverPhoto + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                ", userType='" + userType + '\'' +
                ", eyeColor='" + eyeColor + '\'' +
                ", hairColor='" + hairColor + '\'' +
                ", userSubType='" + userSubType + '\'' +
                ", website='" + website + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", ethnicity='" + ethnicity + '\'' +
                ", chest=" + chest +
                ", waist=" + waist +
                ", hip=" + hip +
                ", profilePhotos=" + profilePhotos +
                ", coverPhotos=" + coverPhotos +
                ", polaroidsPhotos=" + polaroidsPhotos +
                '}';
    }

    public Profile getProfile(){
        Profile.getInstance().setToken(token);
        Profile.getInstance().setFirstName(firstName);
        Profile.getInstance().setLastName(lastName);
        Profile.getInstance().setDisplayName(displayName);
        Profile.getInstance().setEmail(email);
        Profile.getInstance().setPassword(password);
        Profile.getInstance().setGender(gender);
        Profile.getInstance().setDateOfBirth(dateOfBirth);
        Profile.getInstance().setCity(city);
        Profile.getInstance().setCountry(country);
        Profile.getInstance().setCoverPhoto(coverPhoto);
        Profile.getInstance().setProfilePhoto(profilePhoto);
        Profile.getInstance().setUserType(userType);
        Profile.getInstance().setEyeColor(eyeColor);
        Profile.getInstance().setHairColor(hairColor);
        Profile.getInstance().setUserSubType(userSubType);
        Profile.getInstance().setWebsite(website);
        Profile.getInstance().setHeight(height);
        Profile.getInstance().setWeight(weight);
        Profile.getInstance().setEthnicity(ethnicity);
        Profile.getInstance().setChest(chest);
        Profile.getInstance().setWaist(waist);
        Profile.getInstance().setHip(hip);
        Profile.getInstance().setMin_hourrate(min_hourrate);
        Profile.getInstance().setMax_hourrate(max_hourrate);
        Profile.getInstance().setMin_halfdayrate(min_halfdayrate);
        Profile.getInstance().setMax_halfdayrate(max_halfdayrate);
        Profile.getInstance().setMin_fulldayrate(min_fulldayrate);
        Profile.getInstance().setMax_fulldayrate(max_fulldayrate);
        Profile.getInstance().setMax_travel(max_travel);
        Profile.getInstance().setWill_travel(will_travel);
        Profile.getInstance().setPromoted(isPromoted);
        Profile.getInstance().setPublished(isPublished);
        Profile.getInstance().setProfilePhotos(profilePhotos);
        Profile.getInstance().setCoverPhotos(coverPhotos);
        Profile.getInstance().setPolaroidsPhotos(polaroidsPhotos);
        return Profile.getInstance();
    }
}
