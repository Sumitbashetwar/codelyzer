package com.example.CompilerDemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestCaseDTO {
    private String testCaseInput;
    private String testCaseOutput;
}
