package com.castingmob.database.impl;

import android.content.ContentValues;
import android.database.Cursor;

import com.castingmob.database.CastingDatabase;
import com.castingmob.database.dao.MessageDAO;
import com.castingmob.database.model.Conversation;
import com.castingmob.database.model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nishant on 13/02/16.
 */
public class MessageImpl extends MessageInfo implements MessageDAO{

    /**
     * Get the List of Users
     * @param cursor
     * @return
     */
    private List<Message> getMessageData(Cursor cursor) {
        List<Message> messages = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Message message = new Message();
                message.setMessageId(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_ID)));
                message.setConversationId(cursor.getInt(cursor.getColumnIndex(COLUMN_CONVERSATION_ID)));
                message.setMessageTime(cursor.getLong(cursor.getColumnIndex(COLUMN_MESSAGE_TIME)));
                message.setSenderToken(cursor.getString(cursor.getColumnIndex(COLUMN_SENDER_TOKEN)));
                message.setSenderName(cursor.getString(cursor.getColumnIndex(COLUMN_SENDER_NAME)));
                message.setData(cursor.getString(cursor.getColumnIndex(COLUMN_DATA)));
                message.setMessageType(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_TYPE)));
                messages.add(message);
            }while (cursor.moveToNext());
        }
        return messages;
    }

    @Override
    public List<Message> getMessages() {
        Cursor cursor = CastingDatabase.getInstance().getRecords(TABLE_MESSAGE, ALL_COLUMNS, null, null);
        return getMessageData(cursor);
    }

    @Override
    public boolean insertMessage(Message message) {
        return insert(message);
    }

    /**
     * Insert the message in database
     * @param message
     */
    private boolean insert(Message message){
        ContentValues values = new ContentValues();
        values.put(COLUMN_MESSAGE_ID, message.getMessageId());
        values.put(COLUMN_CONVERSATION_ID, message.getConversationId());
        values.put(COLUMN_MESSAGE_TIME, message.getMessageTime());
        values.put(COLUMN_SENDER_TOKEN, message.getSenderToken());
        values.put(COLUMN_SENDER_NAME, message.getSenderName());
        values.put(COLUMN_DATA, message.getData());
        values.put(COLUMN_MESSAGE_TYPE, message.getMessageType());

        long l = CastingDatabase.getInstance().insertRecords(TABLE_MESSAGE, values);
        if (l != -1)
            return true;
        else
            return false;
    }

    @Override
    public boolean isMessagePresent(String messageId){
        String query = String.format("SELECT * FROM %s WHERE %s=?",TABLE_MESSAGE,COLUMN_MESSAGE_ID);
        String[] args = new String[] {messageId};
        return CastingDatabase.getInstance().isRecordExist(query, args);
    }

    @Override
    public boolean isMessageExistByConversation(String coversationId){
        String query = String.format("SELECT * FROM %s WHERE %s=?",TABLE_MESSAGE,COLUMN_CONVERSATION_ID);
        String[] args = new String[] {coversationId};
        return CastingDatabase.getInstance().isRecordExist(query, args);
    }

    @Override
    public List<Message> getMessageByConversationId(String coversationId) {
        String query = String.format("%s = %s",COLUMN_CONVERSATION_ID,coversationId);
        Cursor cursor = CastingDatabase.getInstance().getRecords(TABLE_MESSAGE, ALL_COLUMNS, query, null);
        return getMessageData(cursor);
    }

}
