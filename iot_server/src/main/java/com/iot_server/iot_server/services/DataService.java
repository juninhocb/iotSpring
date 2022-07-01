package com.iot_server.iot_server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.iot_server.iot_server.repositories.DataRepository;
import com.iot_server.iot_server.services.exceptions.ResourceNotFoundException;
import com.iot_server.iot_server.entities.Data;

@Service
public class DataService {

    @Autowired
    private DataRepository dRepository;
    
    public List<Data> findAll(){
        List<Data> list = dRepository.findAll();
        return list;
    }
    
    public Data findById(@PathVariable long id){
        Optional <Data> obj = dRepository.findById(id);
        return obj.orElseThrow(()-> new ResourceNotFoundException(id));
    }

    public Integer getConsume(){
        Integer sum = 0;
        for (Data i : dRepository.findAll()){
            sum = sum + (i.getCharge()*i.getUseTime());
        }
        return sum;
    }

}
