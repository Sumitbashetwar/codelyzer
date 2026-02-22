package com.example.CompilerDemo.implementation;

import com.example.CompilerDemo.entity.TestCase;
import com.example.CompilerDemo.dto.TestCaseDTO;
import com.example.CompilerDemo.repository.TestCaseRepository;
import com.example.CompilerDemo.services.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestCaseServiceImpl implements TestCaseService {

    @Autowired
    TestCaseRepository testCaseRepository;
    @Override
    public List<TestCase> getAllTestCases() {
//        saveAllTestCase();
        return testCaseRepository.findAll();
    }

    // Hard coding the test cases for each code question
    /*@Override
    public void saveAllTestCase() {
        TestCaseDTO[] testCases = {
                new TestCaseDTO("5 3", "8"),
                new TestCaseDTO("10 20", "30"),
                new TestCaseDTO("-2 6", "4"),
                new TestCaseDTO("8 -4", "4"),
                new TestCaseDTO("15 25", "40"),
                new TestCaseDTO("0 0", "0"),
                new TestCaseDTO("7 3", "10"),
                new TestCaseDTO("12 8", "20"),
                new TestCaseDTO("-10 -5", "-15"),
                new TestCaseDTO("100 200", "300")
        };
        for(TestCaseDTO dto:testCases)
            testCaseRepository.save(convertDTOtoEntity(dto));
    }*/

    private TestCase convertDTOtoEntity(TestCaseDTO dto) {
        TestCase testCase = new TestCase(dto.getTestCaseInput(),dto.getTestCaseOutput());
        return testCase;
    }
}
