package com.iot_server.iot_server.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iot_server.iot_server.entities.enums.TypeDevice;

@Entity
@Table(name = "tb_device")
public class Device implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer type;
    private Boolean status = false;
    private String threadAssign = "";
    private Boolean isConnected = false;  
    private String macAdress; 

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client; 

    
    @JsonIgnore
    @OneToMany
    private Set<Data> datas = new HashSet<>();

    public Device() {
    }
    
    public Device(Long id, String name, TypeDevice type, User client) {
        this.id = id;
        this.name = name;
        setType(type);
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeDevice getType() {
        return TypeDevice.valueOf(type);
    }

    public void setType(TypeDevice type) {
        if(type != null){
            this.type = type.getCode();
        }
        

    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getTotalCharge(){
        Integer sum = 0; 
        for (Data i : datas ){
            sum += i.getCharge(); 
        }
        return sum; 
    }

    public Integer getTotalUseTime(){
        Integer sum = 0; 
        for (Data i : datas ){
            sum += i.getUseTime(); 
        }
        return sum; 
    }

    public User getClient() {
        return client;
    }

    public String getMacAdress() {
        return macAdress;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((client == null) ? 0 : client.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Device other = (Device) obj;
        if (client == null) {
            if (other.client != null)
                return false;
        } else if (!client.equals(other.client))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    public String getThreadAssign() {
        return threadAssign;
    }

    public void setThreadAssign(String threadAssign) {
        this.threadAssign = threadAssign;
    }

    public Boolean getIsConnected() {
        return isConnected;
    }

    public void setIsConnected(Boolean isConnected) {
        this.isConnected = isConnected;
    }

    public void newMacAdress(){
        Random rand = new Random();
        byte[] macAddr = new byte[6];
        rand.nextBytes(macAddr);

        macAddr[0] = (byte)(macAddr[0] & (byte)254); 

        StringBuilder sb = new StringBuilder(18);
        for(byte b : macAddr){
            if (sb.length() > 0) sb.append(":");

            sb.append(String.format("%02x", b));
        }

        this.macAdress = sb.toString();
    }
    
}
