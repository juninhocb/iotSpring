package com.iot_server.iot_server.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iot_server.iot_server.repositories.DeviceRepository;
import com.iot_server.iot_server.services.auxiliaryServices.PublishOnBrokerService;
import com.iot_server.iot_server.services.auxiliaryServices.SubscribeOnBrokerService;


import com.iot_server.iot_server.entities.Device;

@Service
public class KeepAliveService {

    List<Timer> list = new ArrayList<Timer>();

    @Autowired
    private DeviceRepository dRepository;

    @Autowired
    private PublishOnBrokerService auxServicePub;

    @Autowired
    private SubscribeOnBrokerService auxServiceSub;
    
    //Thread Properties
    final Integer delay = 10000;
    final Integer interval = 60000;

    public void keepAliveInitial() {
        for (Device d : dRepository.findAll()){
            newClientThread(d.getName());
        }
    }

    public void keepAliveOnProd(String deviceName){
        newClientThread(deviceName);
    }

    public void newClientThread(String deviceAddName){      
        for (Device d : dRepository.findAll()){
            if (d.getName().equals(deviceAddName)){
                Timer timer = new Timer();
                d.setThreadAssign(timer.toString());
                d.setIsConnected(true);
                dRepository.save(d);
                Device cloneObj = d; 
                list.add(timer);
                timer.scheduleAtFixedRate(new TimerTask(){
                @Override
                public synchronized void run(){
                    try {
                        System.out.println("[NEW CLIENT THREAD] Inicializando novamente a thread DEVICE: " + d.getName()); 
                        newRunThreadAssignment(cloneObj);    
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                }, delay, interval);
            }     
        }
    }

    public synchronized void showListThread(String nameDevice){


        for (int i = 0 ; i < list.size(); i ++){
            System.out.println("Timer: " + i + " = " + list.get(i) + "Device: "+ nameDevice);
        }

        for (Device d : dRepository.findAll()){
            System.out.println("Valor da Thread: " + d.getThreadAssign() + "por thread assign Device: " + nameDevice);
        }
    }

    public synchronized Boolean newRunThreadAssignment(Device deviceHandle) throws InterruptedException{
        Boolean resultSend;
         
        //System.out.println(deviceHandle.getName() + "[NEW RUN THREAD ASSIGNMENT] Cliente " + deviceHandle.getClient().getName() + " Rodando");

        resultSend = auxServicePub.publish(deviceHandle.getName(), "30", false);
        if(resultSend){
            System.out.println("[NEW RUN THREAD ASSIGNMENT] Ping Req OK, Dispositivo: " + deviceHandle.getName());

            if(reciveClientResponse(deviceHandle)){
                System.out.println("[NEW RUN THREAD ASSIGNMENT] Dispositivo: " + deviceHandle.getName() + " se mant??m conectado!"); 
                return true; 
            }
            System.out.println("[NEW RUN THREAD ASSIGNMENT] Dispositivo: " + deviceHandle.getName() + " ser?? desconectado!"); 
            stopThread(deviceHandle.getName());
            return false; 
        } 
        System.out.println("[NEW RUN THREAD ASSIGNMENT] FALHA DO SERVIDOR \"n??o foi poss??vel enviar keep alive, checar conex??o com internet!\" ");
        return false; 
    }

    public synchronized Boolean reciveClientResponse(Device deviceHandle) throws InterruptedException{
        Boolean isResponse = false; 
        System.out.println("[RECIVE CLIENTE RESPONSE] Cliente: " + deviceHandle.getName() + " ir?? se inscrever no t??pico Alive");
        isResponse = auxServiceSub.subAndWait(deviceHandle);             
        return isResponse;     
    }

    public synchronized void stopThread(String nameCancel){
        
        for (Device d : dRepository.findAll()){
            if(d.getName().equals(nameCancel)){
                for (int i = 0; i < list.size(); i ++){
                    if (d.getThreadAssign().equals(list.get(i).toString())){
                        list.get(i).cancel();
                        d.setThreadAssign("");
                        d.setStatus(false);
                        d.setIsConnected(false);
                        list.remove(i);
                        System.out.println("[CLOSE THREAD] Fechando a conex??o com o broker, pois o ESP 8266 Desligou! Dispositivo: " + d.getName());
                        dRepository.save(d);
                    }
                }
            }
        }
    }

}

