package com.dmitrenko.aiadapter.repository;

import com.dmitrenko.aiadapter.model.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StationRepository extends JpaRepository<Station, UUID> {

    boolean existsByExternalId(String externalId);
}
