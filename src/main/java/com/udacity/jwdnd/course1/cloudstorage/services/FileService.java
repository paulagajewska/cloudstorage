package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FileService {
    private final FileMapper fileMapper;

    public int createFile(File file) {
        return fileMapper.createFile(file);
    }

    public List<File> getAllFile(Integer userId) {
        return fileMapper.getAllFile(userId);
    }

    public void deleteFile(Integer fileId) {
        fileMapper.deleteFile(fileId);
    }

    public File getFile(Integer fileId){
       return fileMapper.getFile(fileId);
    }
}
