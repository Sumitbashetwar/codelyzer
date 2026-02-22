package com.example.CompilerDemo.controller;

import com.example.CompilerDemo.dto.SubmittedDTO;
import com.example.CompilerDemo.services.SubmittedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "http://localhost:4200")
public class submissionController {

    @Autowired
    SubmittedService submittedService;
    @PostMapping("/submit")
    public List<String> submitCode(@RequestBody SubmittedDTO code) throws IOException, InterruptedException {
       return submittedService.submitCode(code);
    }
}
