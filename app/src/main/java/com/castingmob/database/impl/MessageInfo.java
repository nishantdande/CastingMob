package com.castingmob.database.impl;

import android.provider.BaseColumns;

/**
 * Created by nishant on 13/02/16.
 */
public abstract class MessageInfo implements BaseColumns{

    //TABLE DETAILS
    public static final String TABLE_MESSAGE = "message";

    public static final String COLUMN_MESSAGE_ID 		= "messageId";
    public static final String COLUMN_CONVERSATION_ID	= "conversationId";
    public static final String COLUMN_MESSAGE_TIME		= "messageTime";
    public static final String COLUMN_SENDER_TOKEN 		= "senderToken";
    public static final String COLUMN_SENDER_NAME 		= "senderName";
    public static final String COLUMN_DATA 		        = "data";
    public static final String COLUMN_MESSAGE_TYPE 		= "messageType";

    //CREATE TABLE QUERY
    public static final String SQL_CREATE_MESSAGE =
            "CREATE TABLE " + TABLE_MESSAGE + " (" +
                    _ID				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_MESSAGE_ID 	+ " TEXT, " +
                    COLUMN_CONVERSATION_ID 	+ " INTEGER, " +
                    COLUMN_MESSAGE_TIME 	+ " REAL, " +
                    COLUMN_SENDER_TOKEN 	+ " TEXT, " +
                    COLUMN_SENDER_NAME 	+ " TEXT, " +
                    COLUMN_DATA    + " TEXT, "	+
                    COLUMN_MESSAGE_TYPE    + " TEXT"	+
                    ")";

    public static final String[] ALL_COLUMNS = { _ID,
            COLUMN_MESSAGE_ID, COLUMN_CONVERSATION_ID,
            COLUMN_MESSAGE_TIME, COLUMN_SENDER_TOKEN,
            COLUMN_SENDER_NAME, COLUMN_DATA, COLUMN_MESSAGE_TYPE};

}
