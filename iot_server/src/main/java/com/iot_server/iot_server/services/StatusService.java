package com.iot_server.iot_server.services;

import java.util.Dictionary;
import java.util.Hashtable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot_server.iot_server.repositories.DeviceRepository;
import com.iot_server.iot_server.services.exceptions.ResourceNotFoundException;
import com.iot_server.iot_server.entities.Device;

@Service
public class StatusService {

    @Autowired
    DeviceRepository dRepository;

    public Dictionary<String,Boolean> getStatus(String nameDevice){
        try{
            for (Device d : dRepository.findAll()){
                if (d.getName().equals(nameDevice)){
                    Dictionary<String,Boolean> obj = new Hashtable<String,Boolean>();
                    obj.put("Estado", d.getStatus()); 
                    obj.put("Conexão", d.getIsConnected());
                    return obj; 
                }
    
            }
        } catch (RuntimeException ex){
            ex.printStackTrace();
            throw new ResourceNotFoundException(nameDevice); 
        }
        

        throw new ResourceNotFoundException(nameDevice);
    }
    
}
