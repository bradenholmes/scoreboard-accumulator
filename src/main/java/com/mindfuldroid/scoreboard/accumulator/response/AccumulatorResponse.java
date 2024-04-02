package com.mindfuldroid.scoreboard.accumulator.response;

public abstract class AccumulatorResponse {
	private int responseCode;
	private String message;
	
	public AccumulatorResponse(int responseCode, String message) {
		this.responseCode = responseCode;
		this.message = message;
	}
	
	public int getResponseCode() {
		return responseCode;
	}
	
	public String getMessage() {
		return message;
	}
}
