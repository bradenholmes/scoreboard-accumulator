package com.mindfuldroid.scoreboard.accumulator.mongo.schema;

public enum League {
	NBA("NBA"),
	NFL("NFL"),
	NHL("NHL");
	
	private String value;
	
	private League(String value) {
		this.value = value;
	}
	
	public String toString() {
		return value;
	}
	
	public static League fromString(String string) {
		for (League t : League.values()) {
			if (string.equals(t.value)) {
				return t;
			}
		}
		return null;
	}
}
