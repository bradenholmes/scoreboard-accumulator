package com.mindfuldroid.scoreboard.accumulator.jobs;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class TestJob implements Job {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ApplicationContext appContext;
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		Scheduler scheduler = appContext.getBean("AccumJobSchedulerBean", Scheduler.class);
		
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		String param = dataMap.getString("param");
		logger.info("test job fired! parameter value: " + param);
		
		JobDetail otherJob = JobBuilder.newJob(OtherJob.class).build();
		Trigger otherJobTrigger = TriggerBuilder.newTrigger().startNow().build();
		
		try {
			scheduler.scheduleJob(otherJob, otherJobTrigger);
		} catch (SchedulerException e) {
			logger.error("FAILED TO SCHEDULE JOB!");
		}
	}
}
