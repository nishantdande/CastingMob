package com.castingmob.casting;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 10/03/16
 ==============================================
 */

import com.castingmob.account.model.Account;
import com.castingmob.account.model.Profile;
import com.castingmob.casting.model.CastingItem;
import com.castingmob.casting.model.location.LocationItem;
import com.castingmob.database.model.Conversation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CastingEndPointInterface {

    /**
     * Get the own casting item list
     * @param access_token
     * @return
     */
    @GET("api/members/casting/own/list/1.0/{access_token}")
    Call<List<CastingItem>> getOwnCastingList(@Path("access_token") String access_token);

    /**
     * Get the General casting item list
     * @param access_token
     * @return
     */
    @GET("api/members/casting/list/1.0/{access_token}")
    Call<List<CastingItem>> getCastingList(@Path("access_token") String access_token);

    /**
     * Get the General casting item list
     * @param access_token
     * @return
     */
    @GET("api/members/casting/list/1.0/{access_token}?")
    Call<List<CastingItem>> getCastingListByParameter(@Path("access_token") String access_token,
                                               @Query("gender") String gender,
                                               @Query("country") String country,
                                               @Query("city") String city,
                                               @Query("state") String state);

    /**
     * Get the Applied Casting item list
     * @param access_token
     * @return
     */
    @GET("api/members/casting/applied/list/1.0/{access_token}")
    Call<List<CastingItem>> getAppliedCastingList(@Path("access_token") String access_token);

    @FormUrlEncoded
    @POST("/api/members/casting/add/1.0")
    Call<CastingItem> addCasting(@Field("userToken") String token,
                      @Field("title")String title,
                      @Field("description") String  description,
                      @Field("minAge") String minAge,
                      @Field("maxAge")String maxAge,
                      @Field("gender") Profile.Gender gender,
                      @Field("city") String city,
                      @Field("state") String state,
                      @Field("country") String country,
                      @Field("photo") String photo,
                      @Field("userType") Account.subType userType,
                      @Field("isPaid") String isPaid,
                      @Field("pay") Double pay,
                      @Field("startDate") String startDate,
                      @Field("endDate") String endDate);


    @FormUrlEncoded
    @POST("/api/members/casting/apply/1.0")
    Call<Conversation> applyCasting(@Field("applicantToken") String token,
                                    @Field("castingId")String castingId,
                                    @Field("message") String  message);

    @FormUrlEncoded
    @POST("/api/members/casting/edit/1.0")
    Call<CastingItem> editCasting(@Field("userToken") String token,
                                 @Field("title")String title,
                                 @Field("description") String  description,
                                 @Field("minAge") String minAge,
                                 @Field("maxAge")String maxAge,
                                 @Field("gender") Profile.Gender gender,
                                 @Field("city") String city,
                                 @Field("state") String state,
                                 @Field("country") String country,
                                 @Field("photo") String photo,
                                 @Field("castingId") Integer castingId,
                                 @Field("userType") String userType,
                                 @Field("isPaid") String isPaid,
                                 @Field("pay") Double pay,
                                 @Field("startDate") String startDate,
                                 @Field("endDate") String endDate);

    @GET("https://maps.googleapis.com/maps/api/place/autocomplete/json?")
    Call<LocationItem> getLocationByText(@Query("input") String input,
                                      @Query("types") String types,
                                      @Query("language") String language,
                                      @Query("key") String key);
}
