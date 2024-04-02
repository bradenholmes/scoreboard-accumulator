package com.mindfuldroid.scoreboard.accumulator.scheduler;

import java.io.IOException;
import java.util.Properties;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Configuration
public class AccumJobScheduler {
	
	Logger logger = LoggerFactory.getLogger("JobSchedulerConfiguration");
	
	@Autowired
	private ApplicationContext appContext;
	

	@Bean(name="AccumJobSchedulerBean")
	public Scheduler getScheduler() throws SchedulerException {
		logger.info("getting the scheduler...");
		try {
			Scheduler scheduler = schedulerFactoryBean().getScheduler();
			
			logger.info("starting scheduler threads...");
			scheduler.start();
			
			logger.info("finished initializing the scheduler!");
			return scheduler;
			
			
		} catch (IOException e) {
			logger.error("failed to get the scheduler!");
			throw new SchedulerException(e);
		}
	}
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setJobFactory(makeJobFactory());
		factory.setQuartzProperties(quartzProperties());
		return factory;
	}
	
	@Bean
	public SpringBeanJobFactory makeJobFactory() {
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		logger.debug("configuring job factory");
		
		jobFactory.setApplicationContext(appContext);
		return jobFactory;
	}
	
	private Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propFactoryBean = new PropertiesFactoryBean();
		propFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		propFactoryBean.afterPropertiesSet();
		return propFactoryBean.getObject();
	}
	

	

}
