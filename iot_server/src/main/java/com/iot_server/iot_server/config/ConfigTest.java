package com.iot_server.iot_server.config;


import java.util.Arrays;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.iot_server.iot_server.entities.Device;
import com.iot_server.iot_server.entities.User;
import com.iot_server.iot_server.entities.Data;
import com.iot_server.iot_server.entities.enums.TypeDevice;
import com.iot_server.iot_server.repositories.DataRepository;
import com.iot_server.iot_server.repositories.DeviceRepository;
import com.iot_server.iot_server.repositories.UserRepository;

@Configuration
@Profile("test")
public class ConfigTest implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository; 

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DataRepository dataRepository;

    @Override
    public void run(String... args) throws Exception {
        User u1 = new User(null, "jr", "jr", "123"); 
        User u2 = new User(null, "admin", "admin", "admin");

        Device d1 = new Device(null, "LED1", TypeDevice.ACTUADOR, u1);
        Device d2 = new Device(null, "LED2", TypeDevice.ACTUADOR, u2);
        Device d3 = new Device(null, "Time_Sensor", TypeDevice.SENSOR, u1);
        Device d4 = new Device(null, "GELADEIRA", TypeDevice.ACTUADOR, u2);

        Data da1 = new Data(null, Instant.parse("2022-06-20T19:53:07Z"), 3500 , 100, d1);
        Data da2 = new Data(null, Instant.parse("2022-06-20T20:53:07Z"), 1500 , 50, d1);
        Data da3 = new Data(null, Instant.parse("2022-06-20T20:53:07Z"), 2000 , 75, d2);
        Data da4 = new Data(null, Instant.parse("2022-06-20T21:53:07Z"), 5000 , 250, d4);
    
        userRepository.saveAll(Arrays.asList(u1,u2));
        deviceRepository.saveAll(Arrays.asList(d1,d2,d3, d4));
        dataRepository.saveAll(Arrays.asList(da1, da2, da3, da4));

    }
}
