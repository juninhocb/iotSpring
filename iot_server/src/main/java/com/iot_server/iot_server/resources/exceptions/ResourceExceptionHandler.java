package com.iot_server.iot_server.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.iot_server.iot_server.entities.StandardError;
import com.iot_server.iot_server.services.exceptions.InvalidResourceException;
import com.iot_server.iot_server.services.exceptions.NullResourceException;
import com.iot_server.iot_server.services.exceptions.ResourceNotFoundException;

//anotation that trigger exceptions
@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler (ResourceNotFoundException.class)
    public ResponseEntity <StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
        String error = "Recurso não encontrado";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler (InvalidResourceException.class)
    public ResponseEntity <StandardError> invalidResource(InvalidResourceException e, HttpServletRequest request){
        String error = "Recurso inválido";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler (NullResourceException.class)
    public ResponseEntity <StandardError> nullResource(NullResourceException e, HttpServletRequest request){
        String error = "Recurso inválido";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
    
}
