package com.micro.associacaoQueo.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.micro.associacaoQueo.exceptions.AlreadyAssociatedException;
import com.micro.associacaoQueo.exceptions.EntityNotFoundException;
import com.micro.associacaoQueo.exceptions.InvalidAssociationException;



@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	 @ExceptionHandler(EntityNotFoundException.class)
	    private ResponseEntity<RestErrorMessage> handleEntityNotFoundException(EntityNotFoundException exception) {
	        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	    }

	    @ExceptionHandler(AlreadyAssociatedException.class)
	    private ResponseEntity<RestErrorMessage> handleAlreadyAssociatedException(AlreadyAssociatedException exception) {
	        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
	    }

	    @ExceptionHandler(InvalidAssociationException.class)
	    private ResponseEntity<RestErrorMessage> handleInvalidAssociationException(InvalidAssociationException exception) {
	        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
	    }
}
