package com.castingmob.database.impl;

import android.content.ContentValues;
import android.database.Cursor;

import com.castingmob.database.CastingDatabase;
import com.castingmob.database.dao.MessageDAO;
import com.castingmob.database.dao.ParticipantsDAO;
import com.castingmob.database.model.Message;
import com.castingmob.database.model.Participant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nishant on 13/02/16.
 */
public class ParticipantImpl extends ParticipantsInfo implements ParticipantsDAO{

    /**
     * Get the List of Users
     * @param cursor
     * @return
     */
    private List<Participant> getParticipantData(Cursor cursor) {
        List<Participant> participants = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Participant participant = new Participant();
                participant.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                participant.setToken(cursor.getString(cursor.getColumnIndex(COLUMN_TOKEN)));
                participants.add(participant);
            }while (cursor.moveToNext());
        }
        return participants;
    }



    /**
     * Insert the participant in database
     * @param participant
     */
    private boolean insert(String conversationId,Participant participant){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONVERSATION_ID, conversationId);
        values.put(COLUMN_NAME, participant.getName());
        values.put(COLUMN_TOKEN, participant.getToken());

        long l = CastingDatabase.getInstance().insertRecords(TABLE_PARTICIPANTS, values);
        if (l != -1)
            return true;
        else
            return false;
    }

    @Override
    public List<Participant> getParticipants() {
        Cursor cursor = CastingDatabase.getInstance().getRecords(TABLE_PARTICIPANTS, ALL_COLUMNS, null, null);
        return getParticipantData(cursor);
    }

    @Override
    public boolean insertParticipants(String conversationId,Participant participant) {
        return insert(conversationId,participant);
    }

    @Override
    public List<Participant> getParticipantsById(String conversationId) {
        String query = String.format("%s = %s",COLUMN_CONVERSATION_ID,conversationId);
        Cursor cursor = CastingDatabase.getInstance().getRecords(TABLE_PARTICIPANTS, ALL_COLUMNS, query, null);
        return getParticipantData(cursor);
    }


    @Override
    public boolean isParticipantsExistByConversation(String coversationId) {
        return false;
    }
}
