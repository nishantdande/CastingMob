package com.castingmob.database.impl;

import android.provider.BaseColumns;

/**
 * Created by nishant on 13/02/16.
 */
public abstract class ParticipantsInfo implements BaseColumns{

    //TABLE DETAILS
    public static final String TABLE_PARTICIPANTS = "participants";

    public static final String COLUMN_CONVERSATION_ID 		= "conversationId";
    public static final String COLUMN_TOKEN	= "token";
    public static final String COLUMN_NAME		= "name";

    //CREATE TABLE QUERY
    public static final String SQL_CREATE_PARTICIPANTS =
            "CREATE TABLE " + TABLE_PARTICIPANTS + " (" +
                    _ID				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CONVERSATION_ID 	+ " INTEGER, " +
                    COLUMN_TOKEN 	+ " TEXT, " +
                    COLUMN_NAME 	+ " TEXT" +
                    ")";

    public static final String[] ALL_COLUMNS = { _ID,
            COLUMN_CONVERSATION_ID, COLUMN_TOKEN,
            COLUMN_NAME};

}
