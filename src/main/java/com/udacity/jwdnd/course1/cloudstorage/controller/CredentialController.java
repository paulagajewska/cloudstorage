package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
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
@RequestMapping("/credential")
@AllArgsConstructor
public class CredentialController {
    private final CredentialService credentialService;

    @PostMapping
    public String addCredential(@ModelAttribute(value = "credentialForm") CredentialForm credentialForm, Authentication authentication, Model model) {
        if (credentialForm.getId() != null) {
            credentialService.updateCredential(credentialForm, authentication.getName());
            model.addAttribute("message", "SuccessUpdateCredential");
        } else {
            credentialService.createCredential(credentialForm, authentication.getName());
            model.addAttribute("message", "SuccessAddCredential");
        }

        return "result";
    }

    @GetMapping("/delete/{credential_id}")
    public String deleteCredential(@PathVariable(value = "credential_id") Integer credentialId, Model model) {
        credentialService.deleteCredential(credentialId);
        model.addAttribute("message", "SuccessDeleteCredential");

        return "result";
    }
}
