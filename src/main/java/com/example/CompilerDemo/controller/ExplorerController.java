package com.example.CompilerDemo.controller;

import com.example.CompilerDemo.explorer.ExplorerItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ExplorerController {
    private static final String BASE_DIRECTORY = "C:\\Codelyser\\github\\Codelyzer_Backend\\Codelyzer\\src";
    @GetMapping("/explorer")
    public ResponseEntity<List<ExplorerItem>> getExplorerData() {
        // Replace this with the path to your local directory
        String directoryPath = "C:\\Codelyser\\github\\Codelyzer_Backend\\Codelyzer\\src";

        List<ExplorerItem> explorerData = fetchDataFromDirectory(new File(directoryPath));
        return ResponseEntity.ok(explorerData);
    }
    @PostMapping("explorer/file-content")
    public ResponseEntity<Map<String,String>> getFileContent(@RequestBody Map<String, String> requestData) {
        String absolutePath = requestData.get("absolutePath");
        try {
            File file = new File(absolutePath);
            String content = new String(Files.readAllBytes(file.toPath()));
            Map<String, String> response = new HashMap<>();
            response.put("content",content);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("content","Error reading file content.");
            return ResponseEntity.status(500).body(response);
        }
    }
    private List<ExplorerItem> fetchDataFromDirectory(File directory) {
        File[] files = directory.listFiles();

        if (files == null) {
            return null;
        }

        return Arrays.stream(files)
                .map(file -> {
                    if (file.isDirectory()) {
                        List<ExplorerItem> subItems = fetchDataFromDirectory(file);
                        return new ExplorerItem(file.getName(), "folder", file.getAbsolutePath(), subItems);
                    } else {
                        return new ExplorerItem(file.getName(), "file", file.getAbsolutePath());
                    }
                })
                .collect(Collectors.toList());
    }
}
