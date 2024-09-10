package com.study.springbatch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirstJobSpringBatchConfig {
	
    @Bean
    Job printHelloJob(JobRepository jobRepository, Step printHelloStep) {
		return new JobBuilder("printHelloJob", jobRepository)
				.start(printHelloStep)
				.incrementer(new RunIdIncrementer())
				.build();
	}
    
    @Bean
    Job printEvenOrOddJob(JobRepository jobRepository, Step printEvenOrOddStep) {
		return new JobBuilder("printEvenOrOddJob", jobRepository) //printEvenOrOddJob -> jobName
				.start(printEvenOrOddStep)
				.incrementer(new RunIdIncrementer())
				.build();
	}

}