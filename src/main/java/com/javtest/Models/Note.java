package com.javtest.Models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "note_order")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer note_id;
    
    private String content;
    private Date date_writing;

    private Integer id_order;

    public Note() {}

    public Note(String content, Date date_writing, Integer id_order) {
        this.content = content;
        this.date_writing = date_writing;
        this.id_order = id_order; 
    }

    public Integer getNoteId() {
        return note_id;
    }

    public void setNoteId(Integer note_id) {
        this.note_id = note_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getNoteDate() {
        return date_writing;
    }

    public void setNoteDate(Date date_writing) {
        this.date_writing = date_writing;
    }

    public Integer getIdOrder() {
        return id_order;
    }

    public void setIdOrder(Integer id_order) {
        this.id_order = id_order;
    }
}

