package com.castingmob.database.impl;

import android.provider.BaseColumns;

/**
 * Created by nishant on 13/02/16.
 */
public abstract class ConversationInfo implements BaseColumns{

    //TABLE DETAILS
    public static final String TABLE_CONVERSATION = "conversation";

    public static final String COLUMN_CONVERSATION_ID 		= "conversationId";
    public static final String COLUMN_UPDATED_TIME	= "updatedTime";
    public static final String COLUMN_MESSAGE_TIME		= "lastMessageTime";
    public static final String COLUMN_MESSAGE_TYPE 		= "lastMessageType";
    public static final String COLUMN_LAST_MESSAGE		= "lastMessage";
    public static final String COLUMN_MESSAGE_SENDER_NAME 		        = "lastMessageSenderName";
    public static final String COLUMN_MESSAGE_SENDER_TOKEN 		= "lastMessageSenderToken";
    public static final String COLUMN_PARTICIPANT_ID 		= "participantId";


    //CREATE TABLE QUERY
    public static final String SQL_CREATE_CONVERSATION =
            "CREATE TABLE " + TABLE_CONVERSATION + " (" +
                    _ID				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CONVERSATION_ID 	+ " INTEGER, " +
                    COLUMN_UPDATED_TIME 	+ " REAL, " +
                    COLUMN_MESSAGE_TIME 	+ " REAL, " +
                    COLUMN_MESSAGE_TYPE 	+ " TEXT, " +
                    COLUMN_LAST_MESSAGE 	+ " TEXT, " +
                    COLUMN_MESSAGE_SENDER_NAME    + " TEXT, "	+
                    COLUMN_MESSAGE_SENDER_TOKEN    + " TEXT, "	+
                    COLUMN_PARTICIPANT_ID    + " TEXT"	+
                    ")";

    public static final String[] ALL_COLUMNS = { _ID,
            COLUMN_CONVERSATION_ID, COLUMN_UPDATED_TIME,
            COLUMN_MESSAGE_TIME, COLUMN_MESSAGE_TYPE,
            COLUMN_LAST_MESSAGE, COLUMN_MESSAGE_SENDER_NAME, COLUMN_MESSAGE_SENDER_TOKEN, COLUMN_PARTICIPANT_ID};

}
