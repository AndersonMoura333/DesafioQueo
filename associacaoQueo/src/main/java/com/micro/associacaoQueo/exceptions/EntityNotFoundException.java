package com.micro.associacaoQueo.exceptions;

public class EntityNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String entity) {
        super(entity + " not found");
    }

    public EntityNotFoundException(String entity, String message) {
        super(entity + " not found: " + message);
    }
}