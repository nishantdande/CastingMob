package com.castingmob.message;

import com.castingmob.casting.model.CastingItem;
import com.castingmob.database.model.Conversation;
import com.castingmob.database.model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by nishant on 12/03/16.
 */
public interface MessageEndPointInterface {

    /**
     * Get the conversation
     * @param access_token
     * @return
     */
    @GET("api/members/conversation/list/1.0/{access_token}")
    Call<List<Conversation>> getConversationList(@Path("access_token") String access_token);

    /**
     * Get the message
     * @param access_token
     * @return
     */
    @GET("api/members/message/list/1.0/{access_token}?")
    Call<List<Message>> getMessageByConversation(@Path("access_token") String access_token,
                                                 @Query("participantToken") String participantToken,
                                                 @Query("lastMessageCreationTime") long lastMessageCreationTime);

    /**
     * Send the message
     * @param participant
     * @param sender
     * @param message
     * @return
     */
    @FormUrlEncoded
    @POST("api/members/message/send/1.0")
    Call<Message> sendMessage(@Field("participant") String participant,
                                                 @Field("sender") String sender,
                                                 @Field("message") String  message);

    /**
     * Block User
     * @param access_token
     * @param sender
     * @return
     */
    @FormUrlEncoded
    @POST("api/members/reportUser/1.0/{access_token}")
    Call<Message> blockUser(@Path("access_token") String access_token,
                              @Field("targetUserToken") String sender);


}
