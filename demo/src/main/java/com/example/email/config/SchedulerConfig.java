package com.example.email.config;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public class SchedulerConfig {
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5);//this means upto 5 csv file that are scheduled at the same time they will run in parallel
        scheduler.setThreadNamePrefix("campaign-scheduler-");
        scheduler.initialize();
        return scheduler;
    }
}
