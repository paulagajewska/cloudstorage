package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CredentialDto {
    private Integer id;
    private String url;
    private String username;
    private String password;
    private String encryptedPassword;
}
