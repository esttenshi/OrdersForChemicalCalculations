package com.javtest.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "folder")
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer folder_id;
    private String name;
    private String path;

    public Folder() {}

    public Folder(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public Integer getFolderId() {
        return folder_id;
    }

    public void setFolderId(Integer folder_id) {
        this.folder_id = folder_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
