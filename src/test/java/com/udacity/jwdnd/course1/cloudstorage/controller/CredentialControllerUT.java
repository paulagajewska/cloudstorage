package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CredentialControllerUT {
    private CredentialService credentialService = mock(CredentialService.class);

    private CredentialController credentialController = new CredentialController(credentialService);

    @Test
    void addCredential() {
        //given
        CredentialForm form = new CredentialForm();
        Model model = mock(Model.class);
        Authentication authentication = mock(Authentication.class);
        String username = "username";
        when(authentication.getName()).thenReturn(username);

        //when
        credentialController.addCredential(form, authentication, model);

        //then 
        verify(credentialService, atMostOnce()).createCredential(form, username);
        verify(model).addAttribute("message", "SuccessAddCredential");
    }

    @Test
    void deleteCredential() {
        //given
        Integer credentialId = 1;
        Model model = mock(Model.class);

        //when
        credentialController.deleteCredential(credentialId, model);

        //then
        verify(credentialService, atMostOnce()).deleteCredential(credentialId);
        verify(model).addAttribute("message", "SuccessDeleteCredential");
    }

}