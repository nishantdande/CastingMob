package com.castingmob.utils;
/*
 ==============================================
 Author      : nishantdande
 Version     : 
 Copyright   :
 Description :
 Date        : 26/01/16
 ==============================================
 */

import com.castingmob.model.PhotoItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PhotoItemParsing {

    public static List<String> getPhotoUrls(String photoJsonArray){
        List<String> photos = new ArrayList<>();
        Type listType = new TypeToken<List<PhotoItem>>() {}.getType();
        List<PhotoItem> photoItems = new Gson().fromJson(photoJsonArray, listType);

        if (photoItems != null){
            for (PhotoItem photoItem : photoItems){
                photos.add(photoItem.getPhotoUrl());
            }
        }

        return photos;
    }

    public static List<String> getPhotoUrls(List<PhotoItem> photoItems){
        List<String> photos = new ArrayList<>();

        if (photoItems != null){
            for (PhotoItem photoItem : photoItems){
                photos.add(photoItem.getPhotoUrl());
            }
        }

        return photos;
    }
}
