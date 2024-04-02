package com.mindfuldroid.scoreboard.accumulator;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mindfuldroid.scoreboard.accumulator.jobs.TestJob;
import com.mindfuldroid.scoreboard.accumulator.response.TestGreeting;

import jakarta.annotation.PostConstruct;

@RestController
public class Controller {
	private static final String template = "Hello, %s!";
	
	@Autowired
	ApplicationContext context;
	
	Scheduler scheduler;
	
	//routine jobs are jobs that fire based on some regular repeating schedule
	Map<JobDetail, Trigger> routineJobs = new HashMap<>();
	
	@PostConstruct
	private void init() {
		scheduler = context.getBean("AccumJobSchedulerBean", Scheduler.class);
		defineRoutineJobs();
	}
	
	private void defineRoutineJobs() {
		JobDetail morningUpdate = JobBuilder.newJob(TestJob.class).build();
		Trigger morningUpdateTrigger = TriggerBuilder.newTrigger()
				.withIdentity("morning_update_trigger")
				.withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(19, 6))
				.build();
		
		routineJobs.put(morningUpdate, morningUpdateTrigger);
	
	}
	
	@GetMapping("/greeting")
	public TestGreeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new TestGreeting(1, String.format(template, name));
	}
	
	@GetMapping("/start")
	public TestGreeting start() {
		
		try {
			for (Entry<JobDetail, Trigger> e: routineJobs.entrySet()) {
				scheduler.scheduleJob(e.getKey(), e.getValue());
			}
		} catch (SchedulerException e) {
			return new TestGreeting(1, String.format(template, "it failed :("));
		}
		
		return new TestGreeting(1, String.format(template, "I DID IT"));
	}
}
