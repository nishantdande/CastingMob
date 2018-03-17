package com.castingmob.account;
/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 14/01/16
 ==============================================
 */


import com.castingmob.account.model.Account;
import com.castingmob.account.model.FeedNameByText;
import com.castingmob.account.model.PhotoItem;
import com.castingmob.account.model.Profile;
import com.castingmob.account.model.UserDetail;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AccountEndpointInterface {

    /**
     *  Create Account API
     * @param email
     * @param type
     * @param subType
     * @param website
     * @return
     */
    @FormUrlEncoded
    @POST("/api/members/register/1.0")
    Call<Void> register(@Field("email") String email,
                        @Field("type")Account.Type type,
                        @Field("subType") Account.subType subType,
                        @Field("website") String website);

    /**
     * Activation API
     * @param email
     * @param activation
     * @return
     */
    @FormUrlEncoded
    @POST("/api/members/activate/1.0")
    Call<Profile> activate(@Field("email") String email,
                           @Field("activationCode") String activation);

    /**
     * Set the Password API
     * @param email
     * @param website
     * @return
     */
    @FormUrlEncoded
    @POST("/api/members/password/set/1.0")
    Call<Void> setPassword(@Field("email") String email,
                           @Field("password") String website);


    @FormUrlEncoded
    @POST("/api/members/profile/info/update/1.0")
    Call<Void> update(@Field("token") String token,
                      @Field("firstName")String firstName,
                      @Field("lastName") String  lastName,
                      @Field("displayName") String displayName,
                        @Field("gender") String gender,
                        @Field("dateOfBirth")String dateOfBirth,
                        @Field("city") String city,
                        @Field("country") String country,
                        @Field("eyeColor") String eyeColor,
                        @Field("hairColor") String hairColor,
                        @Field("height") Integer height,
                        @Field("weight") Integer weight,
                        @Field("chest") Integer chest,
                        @Field("hip")Integer hip,
                        @Field("waist") Integer waist,
                        @Field("ethnicity") String ethnicity,
                        @Field("min_hourrate") String min_hourrate,
                        @Field("max_hourrate") String max_hourrate,
                        @Field("min_halfdayrate") String min_halfdayrate,
                        @Field("max_halfdayrate") String max_halfdayrate,
                        @Field("min_fulldayrate") String min_fulldayrate,
                        @Field("max_fulldayrate") String max_fulldayrate,
                        @Field("max_travel") String max_travel,
                        @Field("will_travel") String will_travel,
                        @Field("isPublished") String isPublished,
                        @Field("isPromoted") String isPromoted);


    /**
     * Login API
     * @param email
     * @param website
     * @return
     */
    @FormUrlEncoded
    @POST("/api/members/login/1.0")
    Call<Profile> login(@Field("email") String email,
                           @Field("password") String website);

    /**
     * Upload the Photo
     * @param access_token
     * @param photoType
     * @param file
     * @return
     */
    @Multipart
    @POST("/api/members/media/upload/1.0/{access_token}?")
    Call<PhotoItem> uploadPhoto(@Path("access_token") String access_token,
                                 @Query("photoType") String photoType,
                                 @Part("media") RequestBody file);

    /**
     * Get User Detail by token
     * @param access_token
     * @return
     */
    @GET("/api/members/userDetails/get/1.0/{access_token}")
    Call<UserDetail> getUserDetails(@Path("access_token") String access_token);

    /**
     * Select the Feed Name By Text
     * @param text
     * @return
     */
    @GET("/api/members/names/list/1.0/{text}")
    Call<FeedNameByText> selectFeedByName(@Path("text") String text);

    /**
     * Get the Chat/Conversation Profile Path
     * @param access_token
     * @return
     */
    @GET("/api/members/profilePhoto/get/1.0/{access_token}")
    Call<ResponseBody> getChatProfilePic(@Path("access_token") String access_token);

    @FormUrlEncoded
    @POST("/api/members/reportUser/1.0/{access_token}")
    Call<ResponseBody> blockUser(@Path("access_token") String access_token,
                                 @Field("targetUserToken") String targetUserToken);
}
