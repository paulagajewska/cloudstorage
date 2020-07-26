package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = DEFINED_PORT)
class CredentialControllerIT {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private UserService userService;

    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(username = "username")
    @Test
    void createCredential() throws Exception {
        CredentialForm form = new CredentialForm("url", "username", "password");
        User user = userService.getUser("username");

        mockMvc.perform(post("/credential")
                .with(csrf()).flashAttr("credentialForm", form))
                .andExpect(status().isOk());

        Boolean isCreated = credentialService.getAllCredentials("username")
                .stream()
                .anyMatch(it -> it.getUsername().equals(form.getUsername())
                        && it.getUrl().equals(form.getUrl())
                        && it.getId().equals(user.getUserId()));
        Assert.isTrue(isCreated);
    }

    @WithMockUser(username = "username")
    @Test
    void deleteCredential() throws Exception {
        CredentialForm form = new CredentialForm("url", "username", "password");
        Integer credentialId = credentialService.createCredential(form, "username");

        mockMvc.perform(get("/credential/delete/" + credentialId))
                .andExpect(status().isOk());

        Boolean isDeleted = !credentialService.getAllCredentials("username")
                .stream()
                .anyMatch(it -> it.getId() == credentialId);

        Assert.isTrue(isDeleted);
    }
}