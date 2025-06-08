package com.javtest.Controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javtest.Models.Note;
import com.javtest.Reprositories.NoteReprository;


@Controller
public class NoteController {

    @Autowired
    private NoteReprository noteRepo;

    @GetMapping("/notes")
    public String getAllNotes(Model model) {
        List<Note> notes = noteRepo.findAll();
        model.addAttribute("notes", notes);
        return "NoteList";
    }

    @GetMapping("/notes/{id}")
    public String getNoteById(@PathVariable Integer id, Model model) {
        Optional<Note> noteOptional = noteRepo.findById(id);
        if (noteOptional.isPresent()) {
            model.addAttribute("note", noteOptional.get());
            return "NoteDetail";
        }
        model.addAttribute("errorMessage", "Заметка не найдена.");
        return "Error";
    }

    @PostMapping("/createNote")
    public String createNote(@RequestParam("content") String content, @RequestParam("orderId") Integer id_order,
            Model model) {
            
        Note newNote = new Note();
        newNote.setContent(content);
        newNote.setNoteDate(new Date());
        newNote.setIdOrder(id_order);
        noteRepo.save(newNote);

        return "redirect:/users/" + model.getAttribute("userId");
    }
}
