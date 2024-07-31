package com.micro.cadastroQueo.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.micro.cadastroQueo.exceptions.EntityNotFoundException;
import com.micro.cadastroQueo.exceptions.AlreadyRegisteredException;
import com.micro.cadastroQueo.exceptions.InvalidDataException;
import com.micro.cadastroQueo.exceptions.ServiceException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<RestErrorMessage> entityNotFoundExceptionHandler(EntityNotFoundException exception) {
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(ServiceException.class)
    private ResponseEntity<RestErrorMessage> serviceExceptionHandler(ServiceException exception) {
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
    
    @ExceptionHandler(AlreadyRegisteredException.class)
    private ResponseEntity<RestErrorMessage> alreadyRegisteredExceptionHandler(AlreadyRegisteredException exception) {
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(InvalidDataException.class)
    private ResponseEntity<RestErrorMessage> invalidDataExceptionHandler(InvalidDataException exception) {
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
