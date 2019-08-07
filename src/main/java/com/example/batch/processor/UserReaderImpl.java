package com.example.batch.processor;

import com.example.batch.domain.UserCsvStructure;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Component
public class UserReaderImpl {

    @StepScope
    public ItemReader<UserCsvStructure> read(String filepath) {
        return new FlatFileItemReaderBuilder<UserCsvStructure>()
                .name("userItemReader")
                .linesToSkip(1)
                .resource(new FileSystemResource(filepath))
                .delimited()
                .names(new String[]{"firstName", "lastName", "email"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<UserCsvStructure>() {{
                    setTargetType(UserCsvStructure.class);
                }})
                .build();
    }

}
