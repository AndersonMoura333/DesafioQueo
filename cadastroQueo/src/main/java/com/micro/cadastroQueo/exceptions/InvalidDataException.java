package com.micro.cadastroQueo.exceptions;

public class InvalidDataException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidDataException(String entity) {
        super("Invalid data for " + entity);
    }

    public InvalidDataException(String entity, String message) {
        super("Invalid data for " + entity + ": " + message);
    }
}
