package com.study.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    @Bean
    Job printHelloJob(JobRepository jobRepository, Step printHelloStep) {
		return new JobBuilder("printHelloJob", jobRepository)
				.start(printHelloStep)
				.build();

	}
	
	@Bean
	Step printHelloStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("printHelloStep", jobRepository)
				.tasklet((StepContribution contribution, ChunkContext chunkContext) -> {
					System.out.println("--- Hello World ---");
					return RepeatStatus.FINISHED;
				}, transactionManager)
				.build();
	}

}