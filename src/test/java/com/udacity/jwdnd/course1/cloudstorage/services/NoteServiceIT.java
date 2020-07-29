package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
class NoteServiceIT {

    @Autowired
    NoteService noteService;

    @Test
    public void correctlyCreateNote() {
        NoteForm noteForm = new NoteForm(null, "title", "description");
        String username = "username";

        noteService.createNote(noteForm, username);
        List<Note> notes = noteService.getAllNote(username);


        Assert.isTrue(notes.get(0).getTitle() == noteForm.getTitle(), "Note title is incorrect");
        Assert.isTrue(notes.get(0).getDescription() == noteForm.getDescription(), "Note description is incorrect");
    }

    @Test
    public void correctlyUpdateNote(){
        NoteForm noteForm = new NoteForm(null, "title", "description");
        String username = "username";
        noteService.createNote(noteForm, username);

        Note note = noteService.getNote(1);
        String updatedDescription = "updated_description";
        String updatedTitle = "updated_title";
        note.setDescription(updatedDescription);
        note.setTitle(updatedTitle);
     //   noteService.updateNote(note);

        Note updatedNote = noteService.getNote(1);
        Assert.isTrue(updatedNote.getDescription() == updatedDescription, "Updated description is incorrect");
        Assert.isTrue(updatedNote.getTitle() == updatedTitle, "Updated title is incorrect");
    }

    @Test
    public void deleteNote(){
        NoteForm noteForm = new NoteForm(null, "title", "description");
        String username = "username";
        int noteId = noteService.createNote(noteForm, username);

        noteService.deleteNote(noteId);
        List<Note> notes = noteService.getAllNote(username);

        Assert.isTrue(!notes.stream().anyMatch(it -> it.getId() == noteId), "Note was not deleted");
    }

}