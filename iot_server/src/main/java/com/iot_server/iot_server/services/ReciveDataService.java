package com.iot_server.iot_server.services;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot_server.iot_server.repositories.DataRepository;
import com.iot_server.iot_server.repositories.DeviceRepository;
import com.iot_server.iot_server.entities.MqttProperties;
import com.iot_server.iot_server.entities.Data;
import com.iot_server.iot_server.entities.Device;

@Service
public class ReciveDataService {
    
    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    public Boolean reciveMessage(String device, Instant time, Integer charge, Integer useTime){
        
        for (Device d : deviceRepository.findAll()){
            if (d.getName().equals(device)){
                MqttProperties master = new MqttProperties();
                
                master.getClient().connectWith()
                .simpleAuth()
                .username(master.getUsername())
                .password(UTF_8.encode(master.getPassword()))
                .applySimpleAuth()
                .send();

                master.getClient().subscribeWith()
                    .topicFilter(device)
                    .send();

                Data dAppend = new Data(null, time, charge, useTime, d);
                System.out.println(dAppend);
                System.out.println(dAppend.getTime());
                System.out.println(dAppend.getCharge());
                dataRepository.save(dAppend);

                return true;
            } 
            
        }
        return false; 
    }


}
