package com.example.CompilerDemo.explorer;

import lombok.Data;
import java.util.List;

@Data
public class ExplorerItem {
    private String name;
    private String type; // "folder" or "file"
    private String absolutePath;
    private List<ExplorerItem> children; // Subitems for folders

    public ExplorerItem(String name, String type) {
        this.name = name;
        this.type = type;
    }
    public ExplorerItem(String name, String type, String path) {
        this.name = name;
        this.type = type;
        this.absolutePath = path;
    }

    public ExplorerItem(String name, String type, List<ExplorerItem> children) {
        this.name = name;
        this.type = type;
        this.children = children;
    }
    public ExplorerItem(String name, String type, String path, List<ExplorerItem> children) {
        this.name = name;
        this.type = type;
        this.absolutePath=path;
        this.children = children;
    }
}
