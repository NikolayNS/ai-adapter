package com.dmitrenko.aiadapter.repository;

import com.dmitrenko.aiadapter.model.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    boolean existsByFirstNameAndLastName(String firstName, String lastName);
}
