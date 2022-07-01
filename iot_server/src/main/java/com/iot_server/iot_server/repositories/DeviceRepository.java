package com.iot_server.iot_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iot_server.iot_server.entities.Device;

public interface DeviceRepository extends JpaRepository<Device, Long>  {
    
}
