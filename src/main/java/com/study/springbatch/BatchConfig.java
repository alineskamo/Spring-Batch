package com.study.springbatch;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    @Bean
    Job printEvenOrOddJob(JobRepository jobRepository, Step printEvenOrOddStep) {
		return new JobBuilder("printEvenOrOddJob", jobRepository) //printEvenOrOddJob -> jobName
				.start(printEvenOrOddStep)
				.incrementer(new RunIdIncrementer())
				.build();

	}
	
	@Bean
	Step printEvenOrOddStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("printEvenOrOddStep", jobRepository)
				.<Integer, String>chunk(20, transactionManager)
				.reader(countToTenReader())
				.processor(everOrdOddProcessor())
				.writer(printWriter())
				.build();
	}
	
	IteratorItemReader<Integer> countToTenReader() {
		List<Integer> oneToTen = Arrays.asList(1, 2, 3 , 4, 5, 6, 7, 8, 9, 10);
		return new IteratorItemReader<>(oneToTen.iterator());
	}
	
	FunctionItemProcessor<Integer, String> everOrdOddProcessor() {
		return new FunctionItemProcessor<>(item -> item % 2 == 0 ? String.format("%s is Even", item) : String.format("%s is Odd", item));
	}
	
	ItemWriter<String> printWriter() {
		return itens -> itens.forEach(System.out::println);
	}
	
}