package com.mindfuldroid.scoreboard.accumulator.response;

public class ErrorResponse extends AccumulatorResponse {

	private Exception exception;
	
	public ErrorResponse(String message, Exception exception) {
		super(500, message);
		this.exception = exception;
	}
	
	public Exception getException() {
		return exception;
	}
	
}
