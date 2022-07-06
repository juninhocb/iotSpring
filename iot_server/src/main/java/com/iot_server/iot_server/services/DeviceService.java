package com.iot_server.iot_server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.iot_server.iot_server.repositories.DataRepository;
import com.iot_server.iot_server.repositories.DeviceRepository;
import com.iot_server.iot_server.repositories.UserRepository;
import com.iot_server.iot_server.services.exceptions.InvalidResourceException;
import com.iot_server.iot_server.services.exceptions.NullResourceException;
import com.iot_server.iot_server.services.exceptions.ResourceNotFoundException;
import com.iot_server.iot_server.entities.Data;
import com.iot_server.iot_server.entities.Device;
import com.iot_server.iot_server.entities.enums.TypeDevice;
import com.iot_server.iot_server.entities.User;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private UserRepository uRepository;

    @Autowired
    private DataRepository dataRepository;

    public List<Device> findAll(){
        List<Device> list = deviceRepository.findAll();
        return list; 
    }

    public Device findById(@PathVariable long id){
        Optional <Device> obj = deviceRepository.findById(id);
        return obj.orElseThrow(()-> new ResourceNotFoundException(id));
    }

    public Device newAttemptConnection(String name, TypeDevice type, String nameCli){
        for (Device d : deviceRepository.findAll()){
            if(d.getName().equals(name) && !d.getIsConnected()){
                System.out.println("[DEVICE - ATTEMPT CONNECTION] " + d.getName() + " Irá se conectar novamente");
                return d;
            }else if (d.getIsConnected() && d.getName().equals(name)){ 
                System.out.println("[DEVICE - ATTEMPT CONNECTION] " +  d.getName() +  "Já está conectado!");
                return d;
            }
        }
        return newDevice(name, type, nameCli);
    }

    public Device newDevice(String name, TypeDevice type, String nameCli){
        if (name != null && type != null){
            for (User u : uRepository.findAll()){
                if (u.getName().equals(nameCli)){
                    for (Device d : deviceRepository.findAll()){
                        if (d.getName().equals(name)){
                            throw new InvalidResourceException(nameCli); 
                        }    
                    }
                    Device dObj = new Device(null, name, type, u);
                    dObj.newMacAdress();
                    deviceRepository.save(dObj);
                    System.out.println("[NEW DEVICE] Novo device " + dObj.getName() + " Criado com sucesso!");
                    return dObj;               
                }
            }
            throw new ResourceNotFoundException(nameCli);
        }
        
        throw new NullResourceException();
    }
    
    public Boolean deleteDevice(String name){
        for (Device d : deviceRepository.findAll()){
            if (d.getName().equals(name)){
                for(Data data : dataRepository.findAll()){
                    if(data.getDevice().getName().equals(name)){
                        dataRepository.deleteById(data.getId());
                    }
                }
                deviceRepository.deleteById(d.getId());
                return true;    
            } 
        }
        return false;
    }

    public Boolean updateDevice(String oldName, String newName){
        for (Device d : deviceRepository.findAll()){
            if (d.getName().equals(newName)){
                return false;
            }
        }

        for (Device d : deviceRepository.findAll()){
            if(d.getName().equals(oldName)){
                d.setName(newName);
                deviceRepository.save(d);
                return true;
            }
        }
        throw new ResourceNotFoundException(oldName);
    }

    public Boolean isConnected(String nameCompare){
        for (Device d : deviceRepository.findAll()){
            if(d.getName().equals(nameCompare)){
                if (d.getIsConnected()){
                    return true;
                }
            }
        }
        return false;
    }   

}
