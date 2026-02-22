package com.example.CompilerDemo.compiler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompilationResult {
    private final boolean success;
    private final String errorDetails;
}
