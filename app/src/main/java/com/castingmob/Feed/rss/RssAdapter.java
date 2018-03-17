package com.castingmob.Feed.rss;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by nishant on 28/09/16.
 */

public interface RssAdapter {
    @GET("/feed/")
    Call<ResponseBody> getItems();

    @GET("/mdcdb/rss/output.xml")
    Call<ResponseBody> getModelItems();
}
