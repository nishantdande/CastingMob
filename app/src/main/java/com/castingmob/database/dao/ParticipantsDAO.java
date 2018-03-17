package com.castingmob.database.dao;

import com.castingmob.database.model.Message;
import com.castingmob.database.model.Participant;

import java.util.List;

/**
 * Created by nishant on 13/02/16.
 */
public interface ParticipantsDAO {

    /**
     * Get the all participant
     * @return
     */
    List<Participant> getParticipants();

    /**
     * Insert the participant in database
     * @param participant
     * @return
     */
    boolean insertParticipants(String conversationId,Participant participant);

    /**
     * get participants by id
     * @param conversationId
     * @return
     */
    List<Participant> getParticipantsById(String conversationId);

    /**
     * Check participant present by conversation id
     * @param coversationId
     * @return
     */
    boolean isParticipantsExistByConversation(String coversationId);
}
