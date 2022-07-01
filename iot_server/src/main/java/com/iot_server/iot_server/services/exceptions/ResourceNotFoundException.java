package com.iot_server.iot_server.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    // id of object that not found
    public ResourceNotFoundException(Object id){
        super("Recurso não encontrado ou não existe! Id: " + id);
    }    
}
