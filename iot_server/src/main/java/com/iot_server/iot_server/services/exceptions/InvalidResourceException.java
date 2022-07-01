package com.iot_server.iot_server.services.exceptions;

public class InvalidResourceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidResourceException(Object id){
        super("Recurso inválido /" + id + " já possuí este dispositivo ou dispositivo já está cadastrado!");
    } 
    
}
