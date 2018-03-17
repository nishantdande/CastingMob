package com.castingmob.Feed;/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 26/01/16
 ==============================================
 */

import android.util.Log;

import com.castingmob.Feed.model.Comment;
import com.castingmob.Feed.model.FeedItem;
import com.castingmob.Feed.model.Filter;
import com.castingmob.Feed.model.v2.Feed;
import com.castingmob.Feed.model.v2.Feeds;
import com.castingmob.network.NetworkManager;
import com.castingmob.prefrences.ProfilePreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedManager {
    
    private static final FeedManager feedManager = new FeedManager();
    
    FeedManager(){}

    public static FeedManager getInstance() {
        return feedManager;
    }

    public void getFeeds(Filter filter,Callback<List<FeedItem>> listCallback){
        FeedEndPointInterface feedEndPointInterface
                = NetworkManager.createService(FeedEndPointInterface.class);

        Call<List<FeedItem>> feedListCall =
                feedEndPointInterface.getFeeds(ProfilePreferences.getInstance().getProfileInfo().getToken(),
                        filter.getLastPhotoTime(),filter.getMaxResult(),
                        filter.getName(), filter.getUserType(), filter.getHairColor(),filter.getEyeColor(),
                        filter.getEthnicity(),filter.getMinHeight(), filter.getMaxHeight(),filter.getMinWeight(),
                        filter.getMaxWeight(), filter.getMinAge(),filter.getMaxAge(),filter.getMinChest(),
                        filter.getMaxChest(),filter.getMinWaist(),filter.getMaxWaist(),filter.getMinHip(),
                        filter.getMaxHip(),filter.getGender(),filter.getCity(),filter.getCountry());

        feedListCall.enqueue(listCallback);
    }

    public void getFeedsV2(Filter filter,Callback<Feeds> feedCallback){
        FeedEndPointInterface feedEndPointInterface
                = NetworkManager.createService(FeedEndPointInterface.class);

        Call<Feeds> feedListCall =
                feedEndPointInterface.getFeedsV2(ProfilePreferences.getInstance().getProfileInfo().getToken(),
                        filter.getLastPhotoTime(),filter.getMaxResult(),
                        filter.getName(), filter.getUserType(), filter.getHairColor(),filter.getEyeColor(),
                        filter.getEthnicity(),filter.getMinHeight(), filter.getMaxHeight(),filter.getMinWeight(),
                        filter.getMaxWeight(), filter.getMinAge(),filter.getMaxAge(),filter.getMinChest(),
                        filter.getMaxChest(),filter.getMinWaist(),filter.getMaxWaist(),filter.getMinHip(),
                        filter.getMaxHip(),filter.getGender(),filter.getCity(),filter.getCountry(),
                        filter.getFilterToken(), filter.getPopularLike(), filter.getUserSubType());

        feedListCall.enqueue(feedCallback);
    }

    public void likeSelectedPhoto(String access_token, String photoId,Callback<ArrayList<String>> likeCallback){
        FeedEndPointInterface feedEndPointInterface
                = NetworkManager.createService(FeedEndPointInterface.class);

        Call<ArrayList<String>> likeCall = feedEndPointInterface.likePhoto(access_token, photoId);

        likeCall.enqueue(likeCallback);
    }

    public void commentOnPhoto(String access_token, String photoId, String comment, Callback<Comment> commentCallback){
        FeedEndPointInterface feedEndPointInterface
                = NetworkManager.createService(FeedEndPointInterface.class);

        Call<Comment> likeCall = feedEndPointInterface.commentOnPhoto(access_token, photoId, comment);

        likeCall.enqueue(commentCallback);
    }

    public void getAllComments(String access_token, String photoId, Callback<List<Comment>> commentsCallback){
        FeedEndPointInterface feedEndPointInterface
                = NetworkManager.createService(FeedEndPointInterface.class);

        Call<List<Comment>> likeCall = feedEndPointInterface.getAllComments(access_token,photoId,0,0);

        likeCall.enqueue(commentsCallback);
    }
}
