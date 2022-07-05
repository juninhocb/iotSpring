package com.iot_server.iot_server.resources;

import java.util.Dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iot_server.iot_server.services.StatusService;

@RestController
@RequestMapping(value = "/stats")
public class StatusResource {
    
    @Autowired
    private StatusService sService; 
    
    @GetMapping(value = "/{deviceName}")
    public ResponseEntity <Dictionary> getStatus(@PathVariable String deviceName){

        Dictionary obj = sService.getStatus(deviceName); 
        return ResponseEntity.ok().body(obj); 
    }

}
