package com.javtest.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer file_id;
    private Integer id_folder;
    private String name;
    private String path;
    private String extension;

    public File() {}

    public File(Integer id_folder, String name, String path, String extension) {
        this.id_folder = id_folder;
        this.name = name;
        this.path = path;
        this.extension = extension;
    }

    public Integer getFileId() {
        return file_id;
    }

    public void setFileId(Integer file_id) {
        this.file_id = file_id;
    }

    public Integer getFolderId() {
        return id_folder;
    }

    public void setFolderId(Integer id_folder) {
        this.id_folder = id_folder;
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

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}

