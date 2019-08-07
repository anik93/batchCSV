package com.example.batch.service;

import com.example.batch.config.UserBatchCsvConfig;
import com.example.batch.processor.UserJobCompletionNotificationListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;

@Slf4j
@Service
public class WatcherService implements Runnable {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private UserBatchCsvConfig job;

    @Autowired
    private UserJobCompletionNotificationListener userJobCompletionNotificationListener;

    @Override
    public void run() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get("C:\\Users\\Anik\\Desktop\\angular");
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
            WatchKey key;
            while ((key = watchService.take()) != null) {

                key.pollEvents()
                        .stream()
                        .filter(event -> event.kind().equals(StandardWatchEventKinds.ENTRY_CREATE))
                        .forEach(watchEvent -> {
                            JobParameters params = new JobParametersBuilder()
                                    .addString("JobID", String.valueOf(System.currentTimeMillis()))
                                    .toJobParameters();
                            try {
                                Path filePath = Paths.get(path.toString(), watchEvent.context().toString());
                                if (Files.isRegularFile(filePath)) {
                                    log.info("New file created {}", filePath);
                                    jobLauncher.run(job.csvFileToDatabaseJob(userJobCompletionNotificationListener, filePath.toString()), params);
                                    Files.delete(filePath);
                                }
                            } catch (Exception e) {
                                log.error(e.getMessage(), e);
                            }
                        });
                key.reset();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
