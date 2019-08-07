package com.example.batch.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCsvStructure {

    private String firstName;

    private String lastName;

    private String email;

}
