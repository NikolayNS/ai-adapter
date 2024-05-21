package com.dmitrenko.aiadapter.repository;

import com.dmitrenko.aiadapter.model.entity.Assistant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AssistantRepository extends JpaRepository<Assistant, UUID> {

    boolean existsByExternalId(String externalId);
}
