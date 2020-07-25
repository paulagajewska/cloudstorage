package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteService {
    private final NoteMapper noteMapper;
    private final UserMapper userMapper;

    public int createNote(NoteForm noteForm, String username) {
        User user = userMapper.getUser(username);
        Note note = new Note(null, noteForm.getTitle(), noteForm.getDescription(), user.getUserId());

        return noteMapper.createNote(note);
    }

    public List<Note> getAllNote(String username) {
        User user = userMapper.getUser(username);

        return noteMapper.getAllNotes(user.getUserId());
    }

    public int deleteNote(Integer noteId) {
        return noteMapper.deleteNote(noteId);
    }

    public int updateNote(Note note) {
        return noteMapper.updateNote(note);
    }

    public Note getNote(Integer noteId) {
        return noteMapper.getNote(noteId);
    }
}
