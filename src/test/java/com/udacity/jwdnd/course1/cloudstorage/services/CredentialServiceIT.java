package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
class CredentialServiceIT {

    @Autowired
    CredentialService credentialService;

    @Autowired
    EncryptionService encryptionService;

    @Autowired
    UserService userService;

    @Test
    void createCredential() {
        CredentialForm form = new CredentialForm("url", "username", "password");
        String username = "username";

        Integer id = credentialService.createCredential(form, username);
        Credential credential = credentialService.getCredential(id);
        Assert.isTrue(credential.getUsername().equals(form.getUsername()), "Credential username is incorrect");
        Assert.isTrue(encryptionService.decryptValue(credential.getPassword(), credential.getKey()).equals(form.getPassword()),
                "Credential password is incorrect");
        Assert.isTrue(credential.getUrl().equals(form.getUrl()), "Credential url is incorrect");
    }

    @Test
    void getAllCredentials() {
        User user = new User(null, "xusername", "xsalt", "xpassword", "xfirstName", "xlastName");
        userService.createUser(user);
        CredentialForm form = new CredentialForm("url", "username", "password");

        credentialService.createCredential(form, user.getUsername());
        credentialService.createCredential(form, user.getUsername());

        Assert.isTrue(credentialService.getAllCredentials(user.getUsername()).size() == 2);
    }

    @Test
    void deleteCredential() {
        CredentialForm form = new CredentialForm("url", "username", "password");
        String username = "username";

        Integer id = credentialService.createCredential(form, username);
        credentialService.deleteCredential(id);
        List<Credential> credentials = credentialService.getAllCredentials(username);
        Assert.isTrue(!credentials.stream().anyMatch(it -> it.getId() == id), "Credential was not deleted");
    }
}