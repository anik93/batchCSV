package com.example.batch.processor;

import com.example.batch.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserJobCompletionNotificationListener extends JobExecutionListenerSupport {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("============ JOB FINISHED ============ Verifying the results....");
            log.info(userRepository.findAll().toString());
        }
    }

}
