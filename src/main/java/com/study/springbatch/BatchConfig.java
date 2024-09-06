package com.study.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    @Bean
    Job printHelloJob(JobRepository jobRepository, Step printHelloStep) {
		return new JobBuilder("printHelloJob", jobRepository)
				.start(printHelloStep)
				.incrementer(new RunIdIncrementer())
				.build();

	}
	
	@Bean
	Step printHelloStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("printHelloStep", jobRepository)
				.tasklet(printHelloTasklet(null), transactionManager)
				.build();
	}
	
	@Bean
	@StepScope //annotation required to add the parameter in the step context
	Tasklet printHelloTasklet(@Value("#{jobParameters['nome']}") String name) {
		return new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println(String.format("Olá, %s!", name));
				return RepeatStatus.FINISHED;
			}
		};		
		
	}

}