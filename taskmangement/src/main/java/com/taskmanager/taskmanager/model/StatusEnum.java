package com.taskmanager.taskmanager.model;



public enum StatusEnum {
	OPEN("open"),
	INPROGRESS("inprogress"),
	CLOSED("closed");

	private String value;

	StatusEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
