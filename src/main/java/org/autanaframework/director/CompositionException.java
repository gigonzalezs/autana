package org.autanaframework.director;

public class CompositionException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public CompositionException(Throwable cause) {
		super(cause.getMessage(), cause);
	}

}
