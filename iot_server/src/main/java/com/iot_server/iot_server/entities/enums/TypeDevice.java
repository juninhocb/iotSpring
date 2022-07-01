package com.iot_server.iot_server.entities.enums;

public enum TypeDevice {
    
    ACTUADOR(1),
    SENSOR(2);

    private int code; 

    private TypeDevice (int code){
        this.code = code; 
    }

    public int getCode(){
        return code;
    }
    
    public static TypeDevice valueOf(int code){
        for (TypeDevice value : TypeDevice.values()){
            if(value.getCode() == code){
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid TypeDevice Code");
    }

}
