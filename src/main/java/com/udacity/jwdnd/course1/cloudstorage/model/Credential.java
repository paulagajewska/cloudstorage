package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Credential {
    private Integer id;
    private String url;
    private String username;
    private String key;
    private String password;
    private Integer userId;
}
