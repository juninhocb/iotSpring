package com.iot_server.iot_server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot_server.iot_server.repositories.DataRepository;
import com.iot_server.iot_server.repositories.DeviceRepository;
import com.iot_server.iot_server.repositories.UserRepository;
import com.iot_server.iot_server.services.exceptions.ResourceNotFoundException;
import com.iot_server.iot_server.entities.Data;
import com.iot_server.iot_server.entities.Device;
import com.iot_server.iot_server.entities.User;

@Service
public class UserService {
    
    @Autowired
    private UserRepository uRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DataRepository dataRepository;

    public List<User> findAll(){
        List<User> list = uRepository.findAll();
        return list;
    }

    public User findById(long id){
        Optional<User> obj = uRepository.findById(id);
        return obj.orElseThrow(()-> new ResourceNotFoundException(id));
    }

    public User newUser(String name, String username, String password){
        User u1 = new User(null, name, username, password);
        uRepository.save(u1);
        return u1;
    }

    public Boolean deleteUser(String name){ 
        try {
            for (User u : uRepository.findAll()){
                if (u.getName().equals(name)){
                    for (Device d : deviceRepository.findAll()){
                        if (d.getClient().getName().equals(name)){
                            for (Data da : dataRepository.findAll()){
                                if(da.getDevice().getName().equals(d.getName()))
                                    dataRepository.deleteById(da.getId());
                            }
                            deviceRepository.deleteById(d.getId());
                        }
                    }
                    uRepository.deleteById(u.getId());
                    return true; 
                }
            } 
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            throw new ResourceNotFoundException(name);
            
        }
        return false;
    }

}
