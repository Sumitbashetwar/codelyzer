package com.example.CompilerDemo.compiler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class CppCompiler {

    public String writeCodeToTempFile(String code, String fileName, String fileExtension) throws IOException {
        // Define the file path for the temporary file
        Path file = Path.of(fileName + fileExtension);

        // Create the file or override its content if it already exists
        if (Files.exists(file)) {
            Files.delete(file);
        }
        Files.createFile(file);

        // Write the code to the temporary file using BufferedWriter
        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardOpenOption.WRITE)) {
            writer.write(code);
        }
        return file.toString();
    }

    public CompilationResult compileCpp(String code) throws IOException, InterruptedException {
        // Write the C++ code to a temporary file
        String filePath = writeCodeToTempFile(code, "Temp", ".cpp");

        // Compile the C++ code using g++
        ProcessBuilder processBuilder = new ProcessBuilder("g++", "Temp.cpp", "-o", "Temp_cpp_executable");
        processBuilder.redirectErrorStream(true);  // Redirect the error stream to the input stream

        Process compileProcess = processBuilder.start();
        compileProcess.waitFor();

        // Capture the output and error streams
        InputStream inputStream = compileProcess.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder output = new StringBuilder();

        // Read the output of the compilation process
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        if (!output.isEmpty()) {
            output.replace(output.length()-1, output.length(), "");
        }

        // Check the exit value to determine success or failure
        int exitValue = compileProcess.exitValue();
        if (exitValue == 0) {
            return new CompilationResult(true, null);
        } else {
            return new CompilationResult(false, output.toString());
        }
    }


    public String executeCpp(String input) throws IOException, InterruptedException {
        // Execute the compiled C++ program
        Process executeProcess = new ProcessBuilder("./Temp_cpp_executable").start();
        InputStream inputDataStream = executeProcess.getInputStream();


        // Provide user-defined input to the C++ program
        executeProcess.getOutputStream().write(input.getBytes());
        executeProcess.getOutputStream().close();

        // Read the output of the C++ program
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputDataStream));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        // removing the last one newline character to match with testcases
        output.replace(output.length()-1,output.length(),"");
        return output.toString();
    }
}
