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
        NoteForm noteForm = new NoteForm("title", "description");
        String username = "username";

        noteService.createNote(noteForm, username);
        List<Note> notes = noteService.getAllNote(username);


        Assert.isTrue(notes.get(0).getNoteTitle() == noteForm.getTitle(), "Note title is incorrect");
        Assert.isTrue(notes.get(0).getNoteDescription() == noteForm.getDescription(), "Note description is incorrect");
    }

    @Test
    public void correctlyUpdateNote(){
        NoteForm noteForm = new NoteForm("title", "description");
        String username = "username";
        noteService.createNote(noteForm, username);

        Note note = noteService.getNote(1);
        String updatedDescription = "updated_description";
        String updatedTitle = "updated_title";
        note.setNoteDescription(updatedDescription);
        note.setNoteTitle(updatedTitle);
        noteService.updateNote(note);

        Note updatedNote = noteService.getNote(1);
        Assert.isTrue(updatedNote.getNoteDescription() == updatedDescription, "Updated description is incorrect");
        Assert.isTrue(updatedNote.getNoteTitle() == updatedTitle, "Updated title is incorrect");
    }

    @Test
    public void deleteNote(){
        NoteForm noteForm = new NoteForm("title", "description");
        String username = "username";
        int noteId = noteService.createNote(noteForm, username);

        noteService.deleteNote(noteId);
        List<Note> notes = noteService.getAllNote(username);

        Assert.isTrue(!notes.stream().anyMatch(it -> it.getNoteId() == noteId), "Note was not deleted");
    }

}