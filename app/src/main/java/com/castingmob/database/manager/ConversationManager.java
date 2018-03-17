package com.castingmob.database.manager;

import com.castingmob.database.dao.ConversationDAO;
import com.castingmob.database.dao.MessageDAO;
import com.castingmob.database.impl.ConversationImpl;
import com.castingmob.database.impl.MessageImpl;
import com.castingmob.database.model.Conversation;
import com.castingmob.database.model.Message;

import java.util.List;

/**
 * Created by nishant on 13/02/16.
 */
public class ConversationManager {

    private final static ConversationManager CONVERSATION_MANAGER =new ConversationManager();

    public static ConversationManager getInstance() {
        return CONVERSATION_MANAGER;
    }

    public interface onComplete{
        void status(boolean s);
        void onCompleteSync();
    }

    /**
     * Insert the conversation from server in database
     * @param conversations
     * @param onComplete
     */
    public void syncConversation(List<Conversation> conversations, onComplete onComplete){
        ConversationDAO conversationDAO = new ConversationImpl();
        for (Conversation conversation : conversations){
            if (!conversationDAO.isConversationPresent(String.valueOf(conversation.getConversationId()))){
                boolean status = conversationDAO.insertConversation(conversation);
                onComplete.status(status);
            }else{
                boolean status = conversationDAO.updateConversation(conversation);
                onComplete.status(status);
            }
        }
        onComplete.onCompleteSync();
    }

    /**
     * Get the all conversation in databases
     * @return
     */
    public List<Conversation> getAllConversation(){
        ConversationDAO conversationDAO = new ConversationImpl();
        return conversationDAO.getConversation();
    }

}
