package com.micro.cadastroQueo.exceptions;

public class ServiceException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceException(String entity) {
        super(entity + " server error");
    }

 
}
