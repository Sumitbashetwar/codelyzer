package com.example.CompilerDemo.implementation;

import com.example.CompilerDemo.compiler.CompilationResult;
import com.example.CompilerDemo.entity.TestCase;
import com.example.CompilerDemo.dto.SubmittedDTO;
import com.example.CompilerDemo.dto.TestCaseDTO;
import com.example.CompilerDemo.compiler.CppCompiler;
import com.example.CompilerDemo.compiler.JavaCompiler;
import com.example.CompilerDemo.services.SubmittedService;
import com.example.CompilerDemo.services.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubmittedServiceImpl implements SubmittedService {
    @Autowired
    TestCaseService testCaseService;
    List<String> results = new ArrayList<>();
    @Override
    public List<String> submitCode(SubmittedDTO code) throws IOException, InterruptedException {
        results.clear();
        if (code.getLanguage().equals("java")) {
            JavaCompiler compiler = new JavaCompiler();
            CompilationResult compilationResult = compiler.compile(code.getSubmittedCode());

            //checking if the code runs without compilation error
            if (compilationResult.isSuccess()) {
                results.add("Compilation Successful");

                List<TestCase> testCases = testCaseService.getAllTestCases();
                int temp = 0;
                for (TestCase testCase : testCases) {

                    //getting the test case input and output
                    TestCaseDTO test = new TestCaseDTO(testCase.getTestCaseInput(), testCase.getTestCaseOutput());
                    String[] input = test.getTestCaseInput().split(" ");
                    String generatedClassName = compiler.extractClassName(code.getSubmittedCode());

                    //executing the code
                    String output = compiler.runCompiledClass(generatedClassName, input);


                    //Function to check if the test cases and output is matching
                    checkTestCase(test, temp, output);

                    //incrementing temp to show only 3 testcases
                    temp++;
                }
            } else
                results.add("Compilation Failed "+compilationResult.getErrorDetails());
        }
        // cpp compiler
        else {
            CppCompiler cppCompiler = new CppCompiler();
            CompilationResult compilationResult = cppCompiler.compileCpp(code.getSubmittedCode());
            if (compilationResult.isSuccess()) {
                results.add("Compilation Successful");

                List<TestCase> testCases = testCaseService.getAllTestCases();
                int temp = 0;
                for (TestCase testCase : testCases) {
                    TestCaseDTO test = new TestCaseDTO(testCase.getTestCaseInput(), testCase.getTestCaseOutput());
                    String output = cppCompiler.executeCpp(test.getTestCaseInput());
                    // will print the output only once


                    checkTestCase(test,temp,output);
                    temp++;
                }
            }
            else {
                results.add("Compilation Failed "+compilationResult.getErrorDetails());
            }
        }
        return results;
    }

    public void checkTestCase(TestCaseDTO test,int temp,String output){
        if (test.getTestCaseOutput().equals(output)) {
            if (temp < 1)
                results.add("Output: " + output);
            results.add("Test Case Successful");
        }
        else {
            if (temp < 3) {
                results.add("Your Output: " + output);
                results.add("Expected Output: " + test.getTestCaseOutput());
                results.add("Failed");
            } else {
                results.add("Test Case Failed");
            }
        }
    }
}
