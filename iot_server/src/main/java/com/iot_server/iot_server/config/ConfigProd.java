package com.iot_server.iot_server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.iot_server.iot_server.resources.KeepAliveResource;

@Configuration
@Profile("dev")
public class ConfigProd implements CommandLineRunner{

    @Autowired
    private KeepAliveResource kResource;

    @Override 
    public void run(String... args) throws Exception {
        kResource.keepAliveStart();
    }
    
}
