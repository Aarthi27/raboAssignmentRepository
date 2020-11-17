package com.rabo.demo.exception;

public class RecordNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	public RecordNotFoundException(int id) {
		super("Searched Record not found with Id "+id);
	}
	public RecordNotFoundException() {
		super("Searched Record not found");
	}
}
