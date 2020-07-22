package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
@AllArgsConstructor
public class HomeController {

    private final FileService fileService;
    private final UserService userService;
    private final NoteService noteService;

    @GetMapping()
    public String homeView(Authentication authentication, Model model) {
        User user = userService.getUser(authentication.getName());
        System.out.println(user);
        model.addAttribute("uploadedFiles", fileService.getAllFile(user.getUserId()));
        model.addAttribute("addedNotes", noteService.getAllNote(user.getUsername()));
        model.addAttribute("noteForm", new NoteForm());

        return "home";
    }

}
