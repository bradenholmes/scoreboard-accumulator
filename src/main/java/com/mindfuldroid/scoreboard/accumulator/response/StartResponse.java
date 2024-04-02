package com.mindfuldroid.scoreboard.accumulator.response;

import java.util.List;

public class StartResponse extends AccumulatorResponse {

	private List<String> routineJobsScheduled;
	
	public StartResponse(String message, List<String> jobsScheduled) {
		super(200, message);
		this.routineJobsScheduled = jobsScheduled;
	}
	
	public List<String> getJobsScheduled(){
		return routineJobsScheduled;
	}
	
}
