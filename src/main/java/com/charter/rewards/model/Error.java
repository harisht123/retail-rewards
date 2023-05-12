package com.charter.rewards.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Error {

    private String errorMessage;
    private String fieldName;
    private String fieldValue;
}
