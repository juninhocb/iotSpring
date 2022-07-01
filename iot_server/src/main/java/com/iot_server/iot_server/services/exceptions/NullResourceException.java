package com.iot_server.iot_server.services.exceptions;

public class NullResourceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NullResourceException(){
        super("Recurso inválido, campos nulos! Verifique se há {String Nome : Integer Type : Boolean Status}");
        
    }
    
}
