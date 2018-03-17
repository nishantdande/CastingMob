package com.castingmob.Feed;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 26/01/16
 ==============================================
 */

import com.castingmob.Feed.model.Comment;
import com.castingmob.Feed.model.FeedItem;
import com.castingmob.Feed.model.v2.Feed;
import com.castingmob.Feed.model.v2.Feeds;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FeedEndPointInterface {

    @GET("/api/members/feed/get/1.0/{access_token}?")
    Call<List<FeedItem>> getFeeds(@Path("access_token") String access_token,
                                  @Query("lastPhotoTime") String lastPhotoTime,
                                  @Query("maxResults") int maxResults,
                                  @Query("name") String name,
                                  @Query("userType") String userType,
                                  @Query("hairColor") String hairColor,
                                  @Query("eyeColor") String eyeColor,
                                  @Query("ethnicity") String ethnicity,
                                  @Query("minHeight") String minHeight,
                                  @Query("maxHeight") String maxHeight,
                                  @Query("minWeight") String minWeight,
                                  @Query("maxWeight") String maxWeight,
                                  @Query("minAge") String minAge,
                                  @Query("maxAge") String maxAge,
                                  @Query("minChest") String minChest,
                                  @Query("maxChest") String maxChest,
                                  @Query("minWaist") String minWaist,
                                  @Query("maxWaist") String maxWaist,
                                  @Query("minHip") String minHip,
                                  @Query("maxHip") String maxHip,
                                  @Query("gender") String gender,
                                  @Query("city") String city,
                                  @Query("country") String country);

    @GET("/api/members/feed/get/1.2/{access_token}?")
    Call<Feeds> getFeedsV2(@Path("access_token") String access_token,
                           @Query("lastPhotoTime") String lastPhotoTime,
                           @Query("maxResults") int maxResults,
                           @Query("name") String name,
                           @Query("userType") String userType,
                           @Query("hairColor") String hairColor,
                           @Query("eyeColor") String eyeColor,
                           @Query("ethnicity") String ethnicity,
                           @Query("minHeight") String minHeight,
                           @Query("maxHeight") String maxHeight,
                           @Query("minWeight") String minWeight,
                           @Query("maxWeight") String maxWeight,
                           @Query("minAge") String minAge,
                           @Query("maxAge") String maxAge,
                           @Query("minChest") String minChest,
                           @Query("maxChest") String maxChest,
                           @Query("minWaist") String minWaist,
                           @Query("maxWaist") String maxWaist,
                           @Query("minHip") String minHip,
                           @Query("maxHip") String maxHip,
                           @Query("gender") String gender,
                           @Query("city") String city,
                           @Query("country") String country,
                           @Query("filterToken") String filterToken,
                           @Query("popularLike") String popularLike,
                           @Query("userSubType") String userSubType);

    @FormUrlEncoded
    @POST("/api/members/like/1.0/{access_token}")
    Call<ArrayList<String>> likePhoto(@Path("access_token") String access_token,
                                      @Field("photoId") String photoId);

    @FormUrlEncoded
    @POST("/api/members/comment/1.0/{access_token}")
    Call<Comment> commentOnPhoto(@Path("access_token") String access_token,
                                      @Field("photoId") String photoId,
                                      @Field("comment") String comment);

    @GET("/api/members/photo/comment/get/1.0/{access_token}?")
    Call<List<Comment>> getAllComments(@Path("access_token") String access_token,
                                 @Query("photoId") String photoId,
                                 @Query("lastCommentTime") Integer lastCommentTime,
                                 @Query("maxResults") Integer maxResults);




}
