package com.iot_server.iot_server.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iot_server.iot_server.services.DataService;
import com.iot_server.iot_server.entities.Data;

@RestController
@RequestMapping(value = "/data")
public class DataResource {
    
    @Autowired
    private DataService service; 
    
    @GetMapping
    public ResponseEntity <List<Data>> findAll(){
        List<Data> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity <Data> findById(@PathVariable long id){
        Data obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping (value = "/consume")
    public ResponseEntity <Integer> getConsume(){
        Integer consumo = service.getConsume();
        return ResponseEntity.ok().body(consumo);
    }

}
