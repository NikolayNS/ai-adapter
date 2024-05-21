package com.dmitrenko.aiadapter.repository;

import com.dmitrenko.aiadapter.model.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, UUID> {

    boolean existsByConversationThreadId(String conversationThreadId);
}
