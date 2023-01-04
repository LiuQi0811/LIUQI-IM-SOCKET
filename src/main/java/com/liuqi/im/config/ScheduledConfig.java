package com.liuqi.im.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/*
 *@ClassName ScheduledConfig
 *@Description TODO
 *@Author LiuQi
 *@Date 2023/1/4 12:54
 *@Version 1.0
 */
@Configuration
public class ScheduledConfig {
    @Bean
    public TaskScheduler taskScheduler(){
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        taskScheduler.initialize();
        return taskScheduler;
    }
}
