package com.iot_server.iot_server.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iot_server.iot_server.services.TriggerService;


@RestController
@RequestMapping(value = "/trigger")
public class TriggerResource {

    @Autowired
    private TriggerService service; 

 
    @PostMapping(value = ("/{device}"))
    public ResponseEntity <String> publish(@PathVariable String device){
        Boolean verifyStatusMessage;
            if (service.IsConnected(device)){
                verifyStatusMessage = service.publish(device);
            if (verifyStatusMessage){
                return ResponseEntity.ok("Dispositivo ligado com sucesso!");
            }else {
                return ResponseEntity.ok("Dispositivo já se encontra ligado!");
            }
        }

        return ResponseEntity.ok("Dispositivo não está conecatado com o broker"); 
        
    } 
}
