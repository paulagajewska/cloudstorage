package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class File {
    private Integer id;
    private String name;
    private String contentType;
    private String size;
    private Integer userId;
    private byte[] data;
    private LocalDate saveDate;
}
