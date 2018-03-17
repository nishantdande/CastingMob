
package com.castingmob.database.model;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("messageId")
    @Expose
    private String messageId;
    @SerializedName("conversationId")
    @Expose
    private Integer conversationId;
    @SerializedName("messageTime")
    @Expose
    private Long messageTime;
    @SerializedName("senderToken")
    @Expose
    private String senderToken;
    @SerializedName("senderName")
    @Expose
    private String senderName;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("messageType")
    @Expose
    private String messageType;

    /**
     * 
     * @return
     *     The messageId
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * 
     * @param messageId
     *     The messageId
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * 
     * @return
     *     The conversationId
     */
    public Integer getConversationId() {
        return conversationId;
    }

    /**
     * 
     * @param conversationId
     *     The conversationId
     */
    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }

    /**
     * 
     * @return
     *     The messageTime
     */
    public Long getMessageTime() {
        return messageTime;
    }

    /**
     * 
     * @param messageTime
     *     The messageTime
     */
    public void setMessageTime(Long messageTime) {
        this.messageTime = messageTime;
    }

    /**
     * 
     * @return
     *     The senderToken
     */
    public String getSenderToken() {
        return senderToken;
    }

    /**
     * 
     * @param senderToken
     *     The senderToken
     */
    public void setSenderToken(String senderToken) {
        this.senderToken = senderToken;
    }

    /**
     * 
     * @return
     *     The senderName
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * 
     * @param senderName
     *     The senderName
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * 
     * @return
     *     The data
     */
    public String getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * 
     * @return
     *     The messageType
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * 
     * @param messageType
     *     The messageType
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

}
