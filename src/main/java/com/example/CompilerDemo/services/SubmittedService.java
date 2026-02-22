package com.example.CompilerDemo.services;

import com.example.CompilerDemo.dto.SubmittedDTO;

import java.io.IOException;
import java.util.List;

public interface SubmittedService {
    public List<String> submitCode(SubmittedDTO code) throws IOException, InterruptedException;

}
