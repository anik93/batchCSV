package com.example.batch.scheduler;

import com.example.batch.config.UserBatchCsvConfig;
import com.example.batch.processor.UserJobCompletionNotificationListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserCsvProcessor {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private UserBatchCsvConfig job;

    @Autowired
    private UserJobCompletionNotificationListener userJobCompletionNotificationListener;

   // @Scheduled(fixedDelay = 10000000)
    public void runJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        jobLauncher.run(job.csvFileToDatabaseJob(userJobCompletionNotificationListener, "C:\\Users\\Anik\\Desktop\\angular\\test1.csv"), params);
    }
}

