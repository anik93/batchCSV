package com.example.batch.config;

import com.example.batch.domain.UserCsvStructure;
import com.example.batch.domain.User;
import com.example.batch.processor.UserJobCompletionNotificationListener;
import com.example.batch.processor.UserProcessorImpl;
import com.example.batch.processor.UserReaderImpl;
import com.example.batch.processor.UserWriterImpl;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class UserBatchCsvConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private UserReaderImpl userReader;

    @Autowired
    private UserWriterImpl userWriter;

    @Autowired
    private UserProcessorImpl userProcessor;

    public Job csvFileToDatabaseJob(UserJobCompletionNotificationListener listener, String fileName) {
        return jobBuilderFactory.get("userCsvProcess")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(csvFileToDatabaseStep(fileName))
                .end()
                .build();
    }

    private Step csvFileToDatabaseStep(String fileName) { //chunk znaczy ile itemow na raz ma byc przetwarzanych
        return stepBuilderFactory.get("userCsvProcess")
                .<UserCsvStructure, User>chunk(100)
                .reader(userReader.read(fileName))
                .processor(userProcessor)
                .writer(userWriter)
                .taskExecutor(taskExecutor())
                .build();
        //task executor to na jakim watku ma byc odpalany
    }

    private TaskExecutor taskExecutor(){
        SimpleAsyncTaskExecutor asyncTaskExecutor=new SimpleAsyncTaskExecutor("spring_batch");
        asyncTaskExecutor.setConcurrencyLimit(5);
        return asyncTaskExecutor;
    }

}
