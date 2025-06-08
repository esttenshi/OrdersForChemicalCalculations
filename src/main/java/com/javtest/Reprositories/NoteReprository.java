package com.javtest.Reprositories;

import com.javtest.Models.Note;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoteReprository extends JpaRepository<Note, Integer> {
    @Query("SELECT o FROM Note o WHERE o.id_order = ?1")
    List<Note> findByIdOrder(Integer idOrder);
}
