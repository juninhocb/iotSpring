package com.iot_server.iot_server.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iot_server.iot_server.services.DeactivateService;

@RestController
@RequestMapping("/deactivate")
public class DeactivateResource {

    @Autowired
    private DeactivateService service; 
 
    @PostMapping(value = ("/{device}"))
    public ResponseEntity<String> publish(@PathVariable String device){
        Boolean verifyStatusMessage = false; 
        if (service.isConnected(device)){
            verifyStatusMessage = service.publish(device);
            if (verifyStatusMessage){
                return ResponseEntity.ok("Dispositivo desligado com sucesso!");
            }
            return ResponseEntity.ok("Dispositivo já está desligado!");
        }
    
        return ResponseEntity.badRequest().body("Dipositivo não está conectado com o Broker");
    }
}
