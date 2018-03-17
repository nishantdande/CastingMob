package com.castingmob.database.manager;

import com.castingmob.database.dao.MessageDAO;
import com.castingmob.database.impl.MessageImpl;
import com.castingmob.database.model.Message;

import java.util.List;

/**
 * Created by nishant on 13/02/16.
 */
public class MessageManager {

    private final static MessageManager wordManger =new MessageManager();

    public static MessageManager getInstance() {
        return wordManger;
    }

    public interface onComplete{
        void status(boolean s);
        void onCompleteSync();
    }

    /**
     * Insert the Message from server in database
     * @param messages
     * @param onComplete
     */
    public void syncMessage(List<Message> messages, onComplete onComplete){
        MessageDAO messageDAO = new MessageImpl();
        for (Message message : messages){
            if (!messageDAO.isMessagePresent(message.getMessageId())){
                boolean status = messageDAO.insertMessage(message);
                onComplete.status(status);
            }
        }

        onComplete.onCompleteSync();
    }

    /**
     * Insert the Message from server in database
     * @param message
     * @param onComplete
     */
    public void syncMessage(Message message, onComplete onComplete){
        MessageDAO messageDAO = new MessageImpl();
        if (!messageDAO.isMessagePresent(message.getMessageId())){
            boolean status = messageDAO.insertMessage(message);
            onComplete.status(status);
        }

        onComplete.onCompleteSync();
    }

    /**
     * Get the all Messages in databases
     * @return
     */
    public List<Message> getAllMessages(){
        MessageDAO messageDAO = new MessageImpl();
        return messageDAO.getMessages();
    }

    /**
     * Get the Messages by Id in databases
     * @return
     */
    public List<Message> getMessagesByConversationId(String conversationId){
        MessageDAO messageDAO = new MessageImpl();
        return messageDAO.getMessageByConversationId(conversationId);
    }

    /**
     * Get the all Messages in databases
     * @return
     */
    public boolean isMessageExistsByConversationId(String conversationId){
        MessageDAO messageDAO = new MessageImpl();
        return messageDAO.isMessageExistByConversation(conversationId);
    }


}
