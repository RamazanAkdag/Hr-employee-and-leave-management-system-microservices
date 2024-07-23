package com.id3.config;

import org.quartz.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration
public class QuartzConfig {

    @Value("${spring.quartz.properties.org.quartz.dataSource.myQuartzDataSource.driver}")
    private String driver;
    @Value("${spring.quartz.properties.org.quartz.dataSource.myQuartzDataSource.URL}")
    private String url;
    @Value("${spring.quartz.properties.org.quartz.dataSource.myQuartzDataSource.user}")
    private String username;
    @Value("${spring.quartz.properties.org.quartz.dataSource.myQuartzDataSource.password}")
    private String password;
    @Bean
    @QuartzDataSource
    public DataSource quartzDataSource() {
        return DataSourceBuilder.create()
                .driverClassName(driver)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource quartzDataSource) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setJobFactory(springBeanJobFactory());
        schedulerFactory.setDataSource(quartzDataSource);
        schedulerFactory.setAutoStartup(true);
        return schedulerFactory;
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
        return jobFactory;
    }

    @Bean
    public Scheduler scheduler(SchedulerFactoryBean factory) throws SchedulerException {
        return factory.getScheduler();
    }
}
