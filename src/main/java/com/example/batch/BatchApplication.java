package com.example.batch;

import com.example.batch.service.WatcherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
public class BatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}

	@Autowired
	private WatcherService watcherService;

	@Override
	public void run(String... args) throws Exception {
		log.info("Started watcher");
		new Thread(watcherService, "watcher-service").start();
	}
}
