package com.mokhovav.goodcare_scheduler.scheduler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Bean (name = "schedulers")
    public Schedulers getSchedulers(){
        return new Schedulers();
    }
//    @Bean
//    public TaskScheduler taskScheduler() {
//        ThreadPoolTaskScheduler poolTaskScheduler = new ThreadPoolTaskScheduler();
//        return poolTaskScheduler;
//    }
}
