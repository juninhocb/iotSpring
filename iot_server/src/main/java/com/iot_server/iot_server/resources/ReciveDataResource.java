package com.iot_server.iot_server.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iot_server.iot_server.entities.Data;
import com.iot_server.iot_server.services.ReciveDataService;



@RestController
@RequestMapping(value = "/recive")
public class ReciveDataResource {

    @Autowired
    private ReciveDataService service;

    @PostMapping(value = "/{device}")
    public ResponseEntity <String> reciveData(@PathVariable String device, @RequestBody Data data){
        Boolean verifyRecive = false;
        verifyRecive = service.reciveMessage(device, data.getTime(), data.getCharge(), data.getUseTime()); 
        if (verifyRecive){
            return ResponseEntity.ok("Mensagem recebida com sucesso!");
        }

        return ResponseEntity.ok("Mensagem não recebida!");


    }
    



}
