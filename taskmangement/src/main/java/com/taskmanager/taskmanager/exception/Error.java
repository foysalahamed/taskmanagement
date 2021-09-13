package com.taskmanager.taskmanager.exception;

import java.util.ResourceBundle;

import com.taskmanager.taskmanager.constant.TaskManagerApiConstants;



public class Error {
	private static final String MESSAGE_RESOURCE = TaskManagerApiConstants.API_ERROR_SOURCE;
	private static final ResourceBundle rb = ResourceBundle.getBundle(MESSAGE_RESOURCE);
	private String errorCode;
	private String message;
	private int status;

	public Error(String message, int status) {
		super();
		this.message = message;
		this.status = status;
	}

	public Error(String mErrorCode) {
		this.errorCode = mErrorCode;
		this.status = Integer.parseInt(rb.getString(mErrorCode + "_STS"));
		this.message = rb.getString(mErrorCode + "_MSG");
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
