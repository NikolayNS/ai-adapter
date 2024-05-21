package com.dmitrenko.aiadapter.repository;

import com.dmitrenko.aiadapter.model.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {

    boolean existsByExternalId(String externalId);
}
