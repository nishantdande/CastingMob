package com.castingmob.Feed.model;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 26/01/16
 ==============================================
 */


public class Filter {

    private static final Filter filter = new Filter();

    public static Filter getInstance() {
        return filter;
    }

    private String name;
    private String userType;
    private String hairColor;
    private String eyeColor;
    private String ethnicity;
    private String minHeight;
    private String maxHeight;
    private String minWeight;
    private String maxWeight;
    private String minAge;
    private String maxAge;
    private String minChest;
    private String maxChest;
    private String minWaist;
    private String maxWaist;
    private String minHip;
    private String maxHip;
    private String gender;
    private String city;
    private String state;
    private String country;

    private String cityCasting;
    private String stateCasting;
    private String countryCasting;

    private int maxResult;
    private String lastPhotoTime;
    private String filterToken;
    private String popularLike;
    private String userSubType;

    public String getCityCasting() {
        return cityCasting;
    }

    public void setCityCasting(String cityCasting) {
        this.cityCasting = cityCasting;
    }

    public String getStateCasting() {
        return stateCasting;
    }

    public void setStateCasting(String stateCasting) {
        this.stateCasting = stateCasting;
    }

    public String getCountryCasting() {
        return countryCasting;
    }

    public void setCountryCasting(String countryCasting) {
        this.countryCasting = countryCasting;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(String minHeight) {
        this.minHeight = minHeight;
    }

    public String getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(String maxHeight) {
        this.maxHeight = maxHeight;
    }

    public String getMinWeight() {
        return minWeight;
    }

    public void setMinWeight(String minWeight) {
        this.minWeight = minWeight;
    }

    public String getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(String maxWeight) {
        this.maxWeight = maxWeight;
    }

    public String getMinAge() {
        return minAge;
    }

    public void setMinAge(String minAge) {
        this.minAge = minAge;
    }

    public String getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(String maxAge) {
        this.maxAge = maxAge;
    }

    public String getMinChest() {
        return minChest;
    }

    public void setMinChest(String minChest) {
        this.minChest = minChest;
    }

    public String getMaxChest() {
        return maxChest;
    }

    public void setMaxChest(String maxChest) {
        this.maxChest = maxChest;
    }

    public String getMinWaist() {
        return minWaist;
    }

    public void setMinWaist(String minWaist) {
        this.minWaist = minWaist;
    }

    public String getMaxWaist() {
        return maxWaist;
    }

    public void setMaxWaist(String maxWaist) {
        this.maxWaist = maxWaist;
    }

    public String getMinHip() {
        return minHip;
    }

    public void setMinHip(String minHip) {
        this.minHip = minHip;
    }

    public String getMaxHip() {
        return maxHip;
    }

    public void setMaxHip(String maxHip) {
        this.maxHip = maxHip;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public String getLastPhotoTime() {
        return lastPhotoTime;
    }

    public void setLastPhotoTime(String lastPhotoTime) {
        this.lastPhotoTime = lastPhotoTime;
    }

    public String getFilterToken() {
        return filterToken;
    }

    public void setFilterToken(String filterToken) {
        this.filterToken = filterToken;
    }

    public String getPopularLike() {
        return popularLike;
    }

    public void setPopularLike(String popularLike) {
        this.popularLike = popularLike;
    }

    public String getUserSubType() {
        return userSubType;
    }

    public void setUserSubType(String userSubType) {
        this.userSubType = userSubType;
    }

    public static Filter getFilter() {
        return filter;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "name='" + name + '\'' +
                ", userType='" + userType + '\'' +
                ", hairColor='" + hairColor + '\'' +
                ", eyeColor='" + eyeColor + '\'' +
                ", ethnicity='" + ethnicity + '\'' +
                ", minHeight='" + minHeight + '\'' +
                ", maxHeight='" + maxHeight + '\'' +
                ", minWeight='" + minWeight + '\'' +
                ", maxWeight='" + maxWeight + '\'' +
                ", minAge='" + minAge + '\'' +
                ", maxAge='" + maxAge + '\'' +
                ", minChest='" + minChest + '\'' +
                ", maxChest='" + maxChest + '\'' +
                ", minWaist='" + minWaist + '\'' +
                ", maxWaist='" + maxWaist + '\'' +
                ", minHip='" + minHip + '\'' +
                ", maxHip='" + maxHip + '\'' +
                ", gender='" + gender + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", maxResult='" + maxResult + '\'' +
                ", lastPhotoTime='" + lastPhotoTime + '\'' +
                '}';
    }

    public void clear(){
        name = "";
        hairColor = "";
        eyeColor = "";
        ethnicity = "";
        minAge = "";
        maxAge = "";
        minHeight ="";
        maxHeight = "";
        minWeight = "";
        maxWeight = "";
        minChest = "";
        maxChest = "";
        minWaist = "";
        maxWaist = "";
        minHip = "";
        maxHip = "";
        gender = "";
        lastPhotoTime = "0";
    }

    public void clearLastPhotoTime(){
        lastPhotoTime = "0";
    }
}
