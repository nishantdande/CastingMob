package com.castingmob.message;

import android.util.Log;

import com.castingmob.database.model.Conversation;
import com.castingmob.database.model.Message;
import com.castingmob.network.NetworkManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nishant on 12/03/16.
 */
public class MessageApiManager {

    private final static MessageApiManager messageApiManager = new MessageApiManager();

    private MessageApiManager(){}

    public static MessageApiManager getInstance() {
        return messageApiManager;
    }

    /**
     * Get the Conversation from API
     * @param access_token
     * @param conversationListCallback
     */
    public void getConversation(String access_token, Callback<List<Conversation>> conversationListCallback){
        MessageEndPointInterface messageEndPointInterface
                = NetworkManager.createService(MessageEndPointInterface.class);

        Call<List<Conversation>> conversationCall = messageEndPointInterface.getConversationList(access_token);

        conversationCall.enqueue(conversationListCallback);
    }

    /**
     * Get the message data
     * @param access_token
     * @param participantToken
     * @param lastMessageTime
     * @param messageListCallback
     */
    public void getChatMessages(String access_token, String participantToken,long lastMessageTime,
                                Callback<List<Message>> messageListCallback){
        MessageEndPointInterface messageEndPointInterface
                = NetworkManager.createService(MessageEndPointInterface.class);

        Call<List<Message>> messageCall = messageEndPointInterface.getMessageByConversation(access_token, participantToken, lastMessageTime);

        messageCall.enqueue(messageListCallback);
    }

    /**
     * Send the message
     * @param participantToken
     * @param sender_token
     * @param message
     * @param messageCallback
     */
    public void sendMessage(String participantToken,String sender_token,String message,
                                Callback<Message> messageCallback){
        MessageEndPointInterface messageEndPointInterface
                = NetworkManager.createService(MessageEndPointInterface.class);

        Call<Message> messageCall = messageEndPointInterface.sendMessage(participantToken,sender_token, message);

        messageCall.enqueue(messageCallback);
    }

}
