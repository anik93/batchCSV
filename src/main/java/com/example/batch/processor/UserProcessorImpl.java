package com.example.batch.processor;

import com.example.batch.domain.UserCsvStructure;
import com.example.batch.domain.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class UserProcessorImpl implements ItemProcessor<UserCsvStructure, User> {

    @Override
    public User process(UserCsvStructure userCsvStructure) throws Exception {
        return User.builder()
                .email(userCsvStructure.getEmail())
                .firstName(userCsvStructure.getFirstName())
                .lastName(userCsvStructure.getLastName())
                .build();
    }


}
