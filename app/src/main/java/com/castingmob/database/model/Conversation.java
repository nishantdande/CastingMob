
package com.castingmob.database.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Conversation implements Parcelable {

    @SerializedName("conversationId")
    @Expose
    private Integer conversationId;
    @SerializedName("updatedTime")
    @Expose
    private Long updatedTime;
    @SerializedName("lastMessageTime")
    @Expose
    private Long lastMessageTime;
    @SerializedName("lastMessageType")
    @Expose
    private String lastMessageType;
    @SerializedName("lastMessage")
    @Expose
    private String lastMessage;
    @SerializedName("lastMessageSenderName")
    @Expose
    private String lastMessageSenderName;
    @SerializedName("lastMessageSenderToken")
    @Expose
    private String lastMessageSenderToken;
    @SerializedName("participants")
    @Expose
    private List<Participant> participants = new ArrayList<Participant>();

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
     *     The updatedTime
     */
    public Long getUpdatedTime() {
        return updatedTime;
    }

    /**
     * 
     * @param updatedTime
     *     The updatedTime
     */
    public void setUpdatedTime(Long updatedTime) {
        this.updatedTime = updatedTime;
    }

    /**
     * 
     * @return
     *     The lastMessageTime
     */
    public Long getLastMessageTime() {
        return lastMessageTime;
    }

    /**
     * 
     * @param lastMessageTime
     *     The lastMessageTime
     */
    public void setLastMessageTime(Long lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    /**
     * 
     * @return
     *     The lastMessageType
     */
    public String getLastMessageType() {
        return lastMessageType;
    }

    /**
     * 
     * @param lastMessageType
     *     The lastMessageType
     */
    public void setLastMessageType(String lastMessageType) {
        this.lastMessageType = lastMessageType;
    }

    /**
     * 
     * @return
     *     The lastMessage
     */
    public String getLastMessage() {
        return lastMessage;
    }

    /**
     * 
     * @param lastMessage
     *     The lastMessage
     */
    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    /**
     * 
     * @return
     *     The lastMessageSenderName
     */
    public String getLastMessageSenderName() {
        return lastMessageSenderName;
    }

    /**
     * 
     * @param lastMessageSenderName
     *     The lastMessageSenderName
     */
    public void setLastMessageSenderName(String lastMessageSenderName) {
        this.lastMessageSenderName = lastMessageSenderName;
    }

    /**
     * 
     * @return
     *     The lastMessageSenderToken
     */
    public String getLastMessageSenderToken() {
        return lastMessageSenderToken;
    }

    /**
     * 
     * @param lastMessageSenderToken
     *     The lastMessageSenderToken
     */
    public void setLastMessageSenderToken(String lastMessageSenderToken) {
        this.lastMessageSenderToken = lastMessageSenderToken;
    }

    /**
     * 
     * @return
     *     The participants
     */
    public List<Participant> getParticipants() {
        return participants;
    }

    /**
     * 
     * @param participants
     *     The participants
     */
    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.conversationId);
        dest.writeValue(this.updatedTime);
        dest.writeValue(this.lastMessageTime);
        dest.writeString(this.lastMessageType);
        dest.writeString(this.lastMessage);
        dest.writeString(this.lastMessageSenderName);
        dest.writeString(this.lastMessageSenderToken);
        dest.writeTypedList(this.participants);
    }

    public Conversation() {
    }

    protected Conversation(Parcel in) {
        this.conversationId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.updatedTime = (Long) in.readValue(Long.class.getClassLoader());
        this.lastMessageTime = (Long) in.readValue(Long.class.getClassLoader());
        this.lastMessageType = in.readString();
        this.lastMessage = in.readString();
        this.lastMessageSenderName = in.readString();
        this.lastMessageSenderToken = in.readString();
        this.participants = in.createTypedArrayList(Participant.CREATOR);
    }

    public static final Parcelable.Creator<Conversation> CREATOR = new Parcelable.Creator<Conversation>() {
        @Override
        public Conversation createFromParcel(Parcel source) {
            return new Conversation(source);
        }

        @Override
        public Conversation[] newArray(int size) {
            return new Conversation[size];
        }
    };
}
