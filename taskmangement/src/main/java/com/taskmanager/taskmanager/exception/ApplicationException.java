package com.taskmanager.taskmanager.exception;



public class ApplicationException extends RuntimeException {
	
	static final long serialVersionUID = -5788345229281494152L;
	
	Error error = null;

	/**
	 * ApplicationException
	 */
	public ApplicationException() {
		super();
	}

	/**
	 * ApplicationException
	 *
	
	 */
	public ApplicationException(Error mError) {
		super(mError.getMessage());
		this.error = mError;
	}

	
	public Error getError() {
		return error;
	}
}
