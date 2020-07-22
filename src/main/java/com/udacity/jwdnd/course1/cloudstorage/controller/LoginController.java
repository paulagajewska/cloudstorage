package com.udacity.jwdnd.course1.cloudstorage.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class LoginController {

    @GetMapping("/login")
    public String loginView(Authentication authentication, Model model) {
        return "login";
    }

}
