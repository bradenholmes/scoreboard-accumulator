package com.mindfuldroid.scoreboard.accumulator.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class OtherJob implements Job {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		

		logger.info("OTHER JOB WAS FIRED!");
		
		
	}
}
