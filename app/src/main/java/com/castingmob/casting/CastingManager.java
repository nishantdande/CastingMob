package com.castingmob.casting;
/*
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
import com.castingmob.network.NetworkManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class CastingManager {

    private final static CastingManager castingManager = new CastingManager();

    private CastingManager(){}

    public static CastingManager getInstance() {
        return castingManager;
    }

    /**
     * Get the Own Casting List
     * @param access_token
     * @param castingItemCallback
     */
    public void getOwnCasting(String access_token, Callback<List<CastingItem>> castingItemCallback){
        CastingEndPointInterface castingEndPointInterface
                = NetworkManager.createService(CastingEndPointInterface.class);

        Call<List<CastingItem>> ownCastingList = castingEndPointInterface.getOwnCastingList(access_token);

        ownCastingList.enqueue(castingItemCallback);
    }

    /**
     * Get the Casting List
     * @param access_token
     * @param castingItemCallback
     */
    public void getCasting(String access_token, Callback<List<CastingItem>> castingItemCallback){
        CastingEndPointInterface castingEndPointInterface
                = NetworkManager.createService(CastingEndPointInterface.class);

        Call<List<CastingItem>> ownCastingList = castingEndPointInterface.getCastingList(access_token);

        ownCastingList.enqueue(castingItemCallback);
    }

    /**
     * Get the Casting By Parameter
     * @param access_token
     * @param gender
     * @param country
     * @param city
     * @param state
     * @param castingItemCallback
     */
    public void getCastingByParameter(String access_token,String gender, String country,
                                     String city, String state, Callback<List<CastingItem>> castingItemCallback){
        CastingEndPointInterface castingEndPointInterface
                = NetworkManager.createService(CastingEndPointInterface.class);

        Call<List<CastingItem>> ownCastingList = castingEndPointInterface
                .getCastingListByParameter(access_token, gender, country, city, state);

        ownCastingList.enqueue(castingItemCallback);
    }

    /**
     * Get Applied Casting list
     * @param access_token
     * @param castingItemCallback
     */
    public void getAppliedCasting(String access_token, Callback<List<CastingItem>> castingItemCallback){
        CastingEndPointInterface castingEndPointInterface
                = NetworkManager.createService(CastingEndPointInterface.class);

        Call<List<CastingItem>> ownCastingList = castingEndPointInterface.getAppliedCastingList(access_token);

        ownCastingList.enqueue(castingItemCallback);
    }

    /**
     * Add New Casting
     * @param token
     * @param title
     * @param desp
     * @param minAge
     * @param maxAge
     * @param gender
     * @param city
     * @param state
     * @param country
     * @param photo
     * @param castingItemCallback
     */
    public void addCasting(String token, String title, String desp, String minAge, String  maxAge,
                           Profile.Gender gender, String city, String state, String country,
                           String photo, Account.subType userType, String isPaid, Double pay,
                           String startDate, String endDate, Callback<CastingItem> castingItemCallback){

        CastingEndPointInterface castingEndPointInterface
                = NetworkManager.createService(CastingEndPointInterface.class);

        Call<CastingItem> castingList = castingEndPointInterface
                .addCasting(token, title, desp, minAge, maxAge, gender, city, state, country, photo,userType,
                        isPaid,pay, startDate, endDate);

        castingList.enqueue(castingItemCallback);
    }

    /**
     * Edit New Casting
     * @param token
     * @param title
     * @param desp
     * @param minAge
     * @param maxAge
     * @param gender
     * @param city
     * @param state
     * @param country
     * @param photo
     * @param castingItemCallback
     */
    public void editCasting(String token, String title, String desp, String minAge, String  maxAge,
                           Profile.Gender gender, String city, String state, String country,
                           String photo, Integer castingId,String userType, String isPaid,Double pay,
                            String startDate, String endDate,Callback<CastingItem> castingItemCallback){

        CastingEndPointInterface castingEndPointInterface
                = NetworkManager.createService(CastingEndPointInterface.class);

        Call<CastingItem> castingList = castingEndPointInterface
                .editCasting(token, title, desp, minAge, maxAge, gender, city, state, country, photo, castingId,userType,
                        isPaid,pay, startDate, endDate);

        castingList.enqueue(castingItemCallback);
    }

    /**
     * Apply Casting
     * @param token
     * @param castingId
     * @param conversationCallback
     */
    public void applyCasting(String token, String castingId, Callback<Conversation> conversationCallback){

        CastingEndPointInterface castingEndPointInterface
                = NetworkManager.createService(CastingEndPointInterface.class);

        Call<Conversation> castingList = castingEndPointInterface
                .applyCasting(token, castingId, "I am interested. Contact me for more details.");

        castingList.enqueue(conversationCallback);
    }


    public void getLocationByText(String text,Callback<LocationItem> locationItemCallback){

        CastingEndPointInterface castingEndPointInterface
                = NetworkManager.createService(CastingEndPointInterface.class);

        Call<LocationItem> castingList = castingEndPointInterface
                .getLocationByText(text,"geocode","en","AIzaSyCmEptRO9b7f2RpubVWBw3OFyJ75t3d5IE");

        castingList.enqueue(locationItemCallback);
//        castingList.enqueue(new Callback<LocationItem>() {
//            @Override
//            public void onResponse(Response<LocationItem> response) {
//                Log.d("locationCasting", response.raw()+" "+response.body().getPredictions());
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                Log.e("locationCasting",t.getMessage(),t);
//            }
//        });
    }
}
