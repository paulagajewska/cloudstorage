package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
@AllArgsConstructor
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;
    private final UserMapper userMapper;

    public int createCredential(CredentialForm credentialForm, String username) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodeKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodeKey);
        User user = userMapper.getUser(username);
        Credential credential = new Credential(
                null,
                credentialForm.getUrl(),
                encryptedPassword,
                encodeKey,
                credentialForm.getPassword(),
                user.getUserId());

        return credentialMapper.createCredential(credential);
    }

    public List<Credential> getAllCredentials(String username) {
        User user = userMapper.getUser(username);

        return credentialMapper.getAllCredentials(user.getUserId());
    }

    public int deleteCredential(Integer credentialId) {
        return credentialMapper.deleteCredential(credentialId);
    }
}
