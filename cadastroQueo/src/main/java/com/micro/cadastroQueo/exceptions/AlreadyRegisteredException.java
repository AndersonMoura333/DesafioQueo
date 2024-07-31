package com.micro.cadastroQueo.exceptions;

public class AlreadyRegisteredException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlreadyRegisteredException(String entity, String property) {
        super(entity + " with this " + property + " is already registered");
    }

    public AlreadyRegisteredException(String entity, String property, String message) {
        super(entity + " with this " + property + " is already registered: " + message);
    }
}
