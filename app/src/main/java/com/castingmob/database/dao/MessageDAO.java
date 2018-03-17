package com.castingmob.database.dao;

import com.castingmob.database.model.Conversation;
import com.castingmob.database.model.Message;

import java.util.List;

/**
 * Created by nishant on 13/02/16.
 */
public interface MessageDAO {

    /**
     * Get the all Message
     * @return
     */
    List<Message> getMessages();

    /**
     * Insert the Message in database
     * @param message
     * @return
     */
    boolean insertMessage(Message message);

    /**
     * Check Message is present
     * @param messageId
     * @return
     */
    boolean isMessagePresent(String messageId);

    /**
     * Check messages present by conversation id
     * @param coversationId
     * @return
     */
    boolean isMessageExistByConversation(String coversationId);

    /**
     * get Message by Conversation Id
     * @param coversationId
     * @return
     */
    List<Message> getMessageByConversationId(String coversationId);
}
