package com.iot_server.iot_server.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.iot_server.iot_server.entities.User;
import com.iot_server.iot_server.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity <List<User>> findAll(){
        List<User> list = service.findAll();
        return ResponseEntity.ok().body(list);
    } 

    @GetMapping(value = "/{id}")
    public ResponseEntity <User> findById(@PathVariable long id){
        User obj = service.findById(id);
        return  ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity <User> instantiateUser(@RequestBody User objUser){
        User u1 = service.newUser(objUser.getName(), objUser.getUsername(), objUser.getPassword());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(u1.getId()).toUri();
        return ResponseEntity.created(uri).body(u1);
    } 

    @DeleteMapping(value = "/{name}")
    public ResponseEntity <String> deleteUser (@PathVariable String name){
        Boolean verifyStatus;
        verifyStatus = service.deleteUser(name);
        if (verifyStatus){
            return ResponseEntity.ok("Deletado!");   
        }
        return ResponseEntity.ok("Usuário não encontrado!");
    }

}
