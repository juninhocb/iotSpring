package com.iot_server.iot_server.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.iot_server.iot_server.services.DeviceService;
import com.iot_server.iot_server.entities.Device;

@RestController
@RequestMapping(value = "/devices")
public class DeviceResource {

    @Autowired
    private DeviceService service;
    
    @Autowired
    private KeepAliveResource kResource; 

    @GetMapping
    public ResponseEntity <List<Device>> findAll(){
        List<Device> list = service.findAll();
        //kResource.keepAliveShowThreads();
        return ResponseEntity.ok().body(list); 
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity <Device> findById(@PathVariable long id){
        Device device = service.findById(id);
        return ResponseEntity.ok().body(device);
    }

    @PostMapping(value = {"/{name}"})
    public ResponseEntity <Device> newAttemptConnection(@PathVariable String name, @RequestBody Device obDevice){
        Device d1 = service.newAttemptConnection(obDevice.getName(), obDevice.getType(), name);
        if(!service.isConnected(obDevice.getName())){kResource.keepAliveProd(obDevice.getName());}
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(d1.getId()).toUri();
        return ResponseEntity.created(uri).body(d1);
    }

    @DeleteMapping(value = {"/{name}"})
    public ResponseEntity <String> deleteByName(@PathVariable String name){
        boolean verifyStatus; 
        verifyStatus = service.deleteDevice(name);
        if (verifyStatus){
            return ResponseEntity.ok("Dispositivo removido");
        }
        return ResponseEntity.ok("Dispositivo não encontrado!");

    }

    @PutMapping(value = {"/{oldName}"})
    public ResponseEntity <String> updateDeviceName(@PathVariable String oldName, @RequestBody String newName){
        boolean verifyStatus = false; 
        verifyStatus = service.updateDevice(oldName, newName);
        if (verifyStatus){
            return ResponseEntity.ok("Nome do dispositivo atualizado com sucesso!");
        }
        return ResponseEntity.ok("Nome de dispositivo não atualizado, já está em uso ou é um nome inválido!");
    }
    

}
