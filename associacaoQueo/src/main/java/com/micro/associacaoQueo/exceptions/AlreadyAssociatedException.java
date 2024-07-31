package com.micro.associacaoQueo.exceptions;

public class AlreadyAssociatedException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlreadyAssociatedException(String message) {
        super(message);
    }
}
