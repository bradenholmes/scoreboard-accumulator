package com.mindfuldroid.scoreboard.accumulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindfuldroid.scoreboard.accumulator.jobs.MorningUpdateJob;
import com.mindfuldroid.scoreboard.accumulator.response.AccumulatorResponse;
import com.mindfuldroid.scoreboard.accumulator.response.ErrorResponse;
import com.mindfuldroid.scoreboard.accumulator.response.SimpleResponse;
import com.mindfuldroid.scoreboard.accumulator.response.StartResponse;

import jakarta.annotation.PostConstruct;

@RestController
public class Controller {
	
	Logger logger = LoggerFactory.getLogger("CONTROLLER");
	
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
		JobDetail morningUpdate = JobBuilder.newJob(MorningUpdateJob.class).withDescription("morning_update_job").build();
		Trigger morningUpdateTrigger = TriggerBuilder.newTrigger()
				.withIdentity("morning_update_trigger")
				.withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(19, 6))
				.build();
		
		routineJobs.put(morningUpdate, morningUpdateTrigger);
	
	}
	
	@GetMapping("/health")
	public AccumulatorResponse healthCheack() {
		return new SimpleResponse(200, "Hello! I'm alive :)");
	}
	
	@GetMapping("/start")
	public AccumulatorResponse start() {
		List<String> jobsScheduled = new ArrayList<>();
		try {
			for (Entry<JobDetail, Trigger> e: routineJobs.entrySet()) {
				scheduler.scheduleJob(e.getKey(), e.getValue());
				jobsScheduled.add(e.getKey().getDescription());
			}
		} catch (SchedulerException e) {
			return new ErrorResponse("", e);
		}
		
		return new StartResponse("scheduled all jobs successfully, system is operational!", jobsScheduled);
	}
	
	@GetMapping("/pause")
	public AccumulatorResponse pause() {
		try {
			scheduler.pauseAll();
		} catch (SchedulerException e) {
			return new ErrorResponse("FAILED TO PAUSE", e);
		}
		
		return new SimpleResponse(200, "accumulation is paused");
	}
	
	@GetMapping("/resume")
	public AccumulatorResponse resume() {
		try {
			scheduler.resumeAll();
		} catch (SchedulerException e) {
			return new ErrorResponse("FAILED TO RESUME", e);
		}
		
		return new SimpleResponse(200, "accumulation is resumed!");
	}
}
