package com.castingmob.database.dao;

import com.castingmob.database.model.Conversation;
import com.castingmob.database.model.Message;

import java.util.List;

/**
 * Created by nishant on 13/02/16.
 */
public interface ConversationDAO {

    /**
     * Get the all Message
     * @return
     */
    List<Conversation> getConversation();

    /**
     * Insert the conversation in database
     * @param conversation
     * @return
     */
    boolean insertConversation(Conversation conversation);

    /**
     * Check conversation present
     * @param conversationId
     * @return
     */
    boolean isConversationPresent(String conversationId);

    /**
     * Update conversation
     * @param conversation
     * @return
     */
    boolean updateConversation(Conversation conversation);
}
