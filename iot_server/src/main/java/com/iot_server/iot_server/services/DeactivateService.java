package com.iot_server.iot_server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot_server.iot_server.entities.Device;
import com.iot_server.iot_server.repositories.DeviceRepository;
import com.iot_server.iot_server.services.auxiliaryServices.PublishOnBrokerService;

@Service
public class DeactivateService {
    
    @Autowired
    private DeviceRepository dRepository;

    @Autowired
    private PublishOnBrokerService auxService;

    public Boolean publish(String deviceName){
        Boolean result; 
        result = auxService.publish(deviceName, "10", true);
        return result; 
    } 

    public Boolean isConnected(String nameDevice){

        for (Device d : dRepository.findAll()){
            if (d.getName().equals(nameDevice)){
                if(d.getIsConnected()){
                    return true;
                }
            }
        }
        return false;
    }
}
