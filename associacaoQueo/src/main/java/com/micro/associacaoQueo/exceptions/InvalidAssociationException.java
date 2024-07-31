package com.micro.associacaoQueo.exceptions;

public class InvalidAssociationException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidAssociationException(String message) {
        super(message);
    }
}
