package com.iot_server.iot_server.services.auxiliaryServices;

import static java.nio.charset.StandardCharsets.UTF_8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot_server.iot_server.entities.Device;
import com.iot_server.iot_server.entities.MqttProperties;
import com.iot_server.iot_server.repositories.DeviceRepository;
import com.iot_server.iot_server.services.exceptions.ResourceNotFoundException;

@Service
public class PublishOnBrokerService {

    @Autowired
    DeviceRepository deviceRepository;

    public Boolean publish(String deviceName, String msg, Boolean changeState){
       MqttProperties master = new MqttProperties();
       for (Device d : deviceRepository.findAll()){
            if(d.getName().equals(deviceName) && changeState == true){
                connAndPub(master, deviceName, msg);
                if (msg == "10" && d.getStatus().equals(true)){
                    d.setStatus(false);
                    deviceRepository.save(d);
                    System.out.println("[PUBLISH] Mensagem 10 enviada ao topico: " + deviceName + " com Sucesso!");
                    return true; 
                } else if(msg == "11" && d.getStatus().equals(false)){
                    d.setStatus(true);
                    deviceRepository.save(d);
                    System.out.println("[PUBLISH] Mensagem 11 enviada ao topico: "+ deviceName + " com Sucesso!");
                    return true;
                }

                if (d.getStatus().equals(false)){
                    System.out.println("[PUBLISH] O aparelho já se encontra desligado!");
                    return false; 
                } 
                System.out.println("[PUBLISH] O aparelho já se encontra ligado!");
                return false; 

            } else if (d.getName().equals(deviceName)){
                try{
                    connAndPub(master, deviceName, msg);
                    System.out.println("[PUBLISH] Mensagem de keep alive enviada com sucesso! Dispositivo --> " + deviceName);
                    return true;
                } catch(RuntimeException e){
                    e.printStackTrace();
                    d.setIsConnected(false);
                    d.setStatus(false);
                    System.out.println("[PUBLISH] Mensgem do keep alive com falhas Dispositivo -->" + deviceName);
                    deviceRepository.save(d);
                    return false; 
                }
            }
       }

       throw new ResourceNotFoundException(deviceName);
    }

    public void connAndPub(MqttProperties master, String deviceName, String msg){
        master.getClient().connectWith()
            .simpleAuth()
            .username(master.getUsername())
            .password(UTF_8.encode(master.getPassword()))
            .applySimpleAuth()
            .send();

        master.getClient().publishWith()
            .topic(deviceName)
            .payload(UTF_8.encode(msg))
            .send();
    }
}