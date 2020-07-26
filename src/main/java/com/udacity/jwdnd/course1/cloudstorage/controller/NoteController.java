package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/note")
@AllArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping
    public String addNote(@ModelAttribute(value = "noteForm") NoteForm noteForm, Authentication authentication, Model model) {
        noteService.createNote(noteForm, authentication.getName());
        model.addAttribute("message", "SuccessAddNote");

        return "result";
    }

    @GetMapping("/delete/{note_id}")
    public String deleteFile(@PathVariable(value = "note_id") Integer noteId, Model model) {
        noteService.deleteNote(noteId);
        model.addAttribute("message", "SuccessDeleteNote");

        return "result";
    }


    @GetMapping("/edit/{note_id}")
    public String editFile(@PathVariable(value = "note_id") Integer noteId, @ModelAttribute(value = "noteForm") NoteForm noteForm, Model model) {
        Note note = noteService.getNote(noteId);
        note.setDescription(noteForm.getDescription());
        noteService.updateNote(note);
        model.addAttribute("message", "SuccessUpdateNote");

        return "result";
    }
}
