package com.iot_server.iot_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iot_server.iot_server.entities.Data;

public interface DataRepository extends JpaRepository<Data, Long>{
    
}
