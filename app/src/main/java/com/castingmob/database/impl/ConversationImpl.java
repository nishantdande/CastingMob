package com.castingmob.database.impl;

import android.content.ContentValues;
import android.database.Cursor;

import com.castingmob.account.model.Profile;
import com.castingmob.casting.model.CastingItem;
import com.castingmob.database.CastingDatabase;
import com.castingmob.database.dao.ConversationDAO;
import com.castingmob.database.dao.MessageDAO;
import com.castingmob.database.dao.ParticipantsDAO;
import com.castingmob.database.model.Conversation;
import com.castingmob.database.model.Message;
import com.castingmob.database.model.Participant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nishant on 13/02/16.
 */
public class ConversationImpl extends ConversationInfo implements ConversationDAO{

    /**
     * Get the List of Users
     * @param cursor
     * @return
     */
    private List<Conversation> getConversationData(Cursor cursor) {
        List<Conversation> conversations = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Conversation conversation = new Conversation();
                conversation.setConversationId(cursor.getInt(cursor.getColumnIndex(COLUMN_CONVERSATION_ID)));
                conversation.setUpdatedTime(cursor.getLong(cursor.getColumnIndex(COLUMN_UPDATED_TIME)));
                conversation.setLastMessageTime(cursor.getLong(cursor.getColumnIndex(COLUMN_MESSAGE_TIME)));
                conversation.setLastMessageType(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_TYPE)));
                conversation.setLastMessage(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_MESSAGE)));
                conversation.setLastMessageSenderName(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_SENDER_NAME)));
                conversation.setLastMessageSenderToken(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_SENDER_TOKEN)));
                conversation.setParticipants(getParticipants(cursor.getInt(cursor.getColumnIndex(COLUMN_CONVERSATION_ID))));
                conversations.add(conversation);
            }while (cursor.moveToNext());
        }
        return conversations;
    }

    private List<Participant> getParticipants(Integer conversationId) {
        ParticipantsDAO participantsDAO = new ParticipantImpl();
        return participantsDAO.getParticipantsById(String.valueOf(conversationId));
    }


    /**
     * Insert the message in database
     * @param conversation
     */
    private boolean insert(Conversation conversation){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONVERSATION_ID, conversation.getConversationId());
        values.put(COLUMN_UPDATED_TIME, conversation.getUpdatedTime());
        values.put(COLUMN_MESSAGE_TIME, conversation.getLastMessageTime());
        values.put(COLUMN_MESSAGE_TYPE, conversation.getLastMessageType());
        values.put(COLUMN_LAST_MESSAGE, conversation.getLastMessage());
        values.put(COLUMN_MESSAGE_SENDER_NAME, conversation.getLastMessageSenderName());
        values.put(COLUMN_MESSAGE_SENDER_TOKEN, conversation.getLastMessageSenderToken());
        values.put(COLUMN_PARTICIPANT_ID, conversation.getConversationId());

        insertParticipantsInTable(conversation);

        long l = CastingDatabase.getInstance().insertRecords(TABLE_CONVERSATION, values);
        if (l != -1)
            return true;
        else
            return false;
    }

    private void insertParticipantsInTable(Conversation conversation) {
        ParticipantsDAO participantsDAO = new ParticipantImpl();
        for (Participant participant : conversation.getParticipants()){
            if (!participant.getToken().equalsIgnoreCase(Profile.getInstance().getToken())){
                participantsDAO.insertParticipants(String.valueOf(conversation.getConversationId()), participant);
            }
        }
    }


    @Override
    public List<Conversation> getConversation() {
        Cursor cursor = CastingDatabase.getInstance().getRecords(TABLE_CONVERSATION, ALL_COLUMNS, null, null, COLUMN_MESSAGE_TIME+" DESC");
        return getConversationData(cursor);
    }

    @Override
    public boolean insertConversation(Conversation conversation) {
        return insert(conversation);
    }

    @Override
    public boolean isConversationPresent(String conversationId){
        String query = String.format("SELECT * FROM %s WHERE %s=?",TABLE_CONVERSATION,COLUMN_CONVERSATION_ID);
        String[] args = new String[] {conversationId};
        return CastingDatabase.getInstance().isRecordExist(query, args);
    }

    @Override
    public boolean updateConversation(Conversation conversation) {
        String query = String.format("%s=?",COLUMN_CONVERSATION_ID);
        String[] args = new String[] {String.valueOf(conversation.getConversationId())};
        return updateRecord(conversation,query,args);
    }

    private boolean updateRecord(Conversation conversation, String where, String[] args){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONVERSATION_ID, conversation.getConversationId());
        values.put(COLUMN_UPDATED_TIME, conversation.getUpdatedTime());
        values.put(COLUMN_MESSAGE_TIME, conversation.getLastMessageTime());
        values.put(COLUMN_MESSAGE_TYPE, conversation.getLastMessageType());
        values.put(COLUMN_LAST_MESSAGE, conversation.getLastMessage());
        values.put(COLUMN_MESSAGE_SENDER_NAME, conversation.getLastMessageSenderName());
        values.put(COLUMN_MESSAGE_SENDER_TOKEN, conversation.getLastMessageSenderToken());
        values.put(COLUMN_PARTICIPANT_ID, conversation.getConversationId());

        insertParticipantsInTable(conversation);

        long l = CastingDatabase.getInstance().updateRecord(TABLE_CONVERSATION, values, where, args);
        if (l != -1)
            return true;
        else
            return false;
    }
}
