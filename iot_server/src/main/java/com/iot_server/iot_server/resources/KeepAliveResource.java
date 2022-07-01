package com.iot_server.iot_server.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.iot_server.iot_server.services.KeepAliveService;

@RestController
public class KeepAliveResource {

    @Autowired
    private KeepAliveService service; 
    
    public void keepAliveStart(){
    
        service.keepAliveInitial();
           
    }

    public void keepAliveProd(String nameDevice){
    
        service.keepAliveOnProd(nameDevice);     
    }

    //public void keepAliveShowThreads(){
        //service.showListThread();
    //}
}
