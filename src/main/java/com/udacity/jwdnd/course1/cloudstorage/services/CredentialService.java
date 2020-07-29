package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialDto;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

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
                credentialForm.getUsername(),
                encodeKey,
                encryptedPassword,
                user.getUserId());

        return credentialMapper.createCredential(credential);
    }

    public int updateCredential(CredentialForm credentialForm, String username) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodeKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodeKey);
        User user = userMapper.getUser(username);
        Credential credential = new Credential(
                credentialForm.getId(),
                credentialForm.getUrl(),
                credentialForm.getUsername(),
                encodeKey,
                encryptedPassword,
                user.getUserId());

        return credentialMapper.updateCredential(credential);
    }

    public List<CredentialDto> getAllCredentials(String username) {
        User user = userMapper.getUser(username);
        List<Credential> credentials = credentialMapper.getAllCredentials(user.getUserId());
        return credentials.stream()
                .map(credential -> mapToCredentialDto(credential))
                .collect(Collectors.toList());
    }

    private CredentialDto mapToCredentialDto(Credential credential) {
        return new CredentialDto(
                credential.getId(),
                credential.getUrl(),
                credential.getUsername(),
                decryptPassword(credential.getPassword(), credential.getKey()), credential.getPassword());
    }

    private String decryptPassword(String password, String key) {
        return encryptionService.decryptValue(password, key);
    }

    public int deleteCredential(Integer credentialId) {
        return credentialMapper.deleteCredential(credentialId);
    }

    public Credential getCredential(Integer credentialId) {
        return credentialMapper.getCredential(credentialId);
    }
}
