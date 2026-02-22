package com.example.CompilerDemo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity(name = "testcase")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TestCase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer testCaseId;
    @Column(name = "input")
    private String testCaseInput;
    @Column(name = "output")
    private String testCaseOutput;

    public TestCase(String testCaseInput, String testCaseOutput) {
        this.testCaseInput=testCaseInput;
        this.testCaseOutput = testCaseOutput;
    }
}
