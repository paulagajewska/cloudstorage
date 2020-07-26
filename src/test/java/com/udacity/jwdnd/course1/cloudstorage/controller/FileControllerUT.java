package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FileControllerUT {

    private FileService fileService = mock(FileService.class);
    private UserMapper userMapper = mock(UserMapper.class);

    private FileController fileController = new FileController(fileService, userMapper);

    @Test
    void successAddFile() throws IOException {
        //given
        Model model = mock(Model.class);
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("xyzFileName");
        when(file.getContentType()).thenReturn("contentType");
        when(file.getSize()).thenReturn(1L);
        when(file.getBytes()).thenReturn(new byte[16]);

        Authentication authentication = mock(Authentication.class);
        String username = "username";
        when(authentication.getName()).thenReturn(username);

        File createdFile = mock(File.class);

        User user = mock(User.class);
        Integer userId = 1;
        when(userMapper.getUser(username)).thenReturn(user);
        when(user.getUserId()).thenReturn(userId);

        when(fileService.isUniqueName(StringUtils.cleanPath(file.getOriginalFilename()), userId)).thenReturn(true);

        //when
        fileController.addFile(file, authentication, model);

        //then
        verify(fileService, atMostOnce()).createFile(createdFile);
        verify(model).addAttribute("message", "SuccessAddFile");
    }

    @Test
    void notUniqueFileNameException() throws IOException {
        //given
        Model model = mock(Model.class);
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("xyzFileName");
        when(file.getContentType()).thenReturn("contentType");
        when(file.getSize()).thenReturn(1L);
        when(file.getBytes()).thenReturn(new byte[16]);

        Authentication authentication = mock(Authentication.class);
        String username = "username";
        when(authentication.getName()).thenReturn(username);

        User user = mock(User.class);
        Integer userId = 1;
        when(userMapper.getUser(username)).thenReturn(user);
        when(user.getUserId()).thenReturn(userId);

        when(fileService.isUniqueName(StringUtils.cleanPath(file.getOriginalFilename()), userId)).thenReturn(false);

        //when
        fileController.addFile(file, authentication, model);

        //then
        verify(model).addAttribute("message", "NotUniqueFileName");
    }

    @Test
    void emptyFileException() throws IOException {
        //given
        Model model = mock(Model.class);
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);

        Authentication authentication = mock(Authentication.class);
        String username = "username";
        when(authentication.getName()).thenReturn(username);

        User user = mock(User.class);
        Integer userId = 1;
        when(userMapper.getUser(username)).thenReturn(user);
        when(user.getUserId()).thenReturn(userId);

        //when
        fileController.addFile(file, authentication, model);

        //then
        verify(model).addAttribute("message", "EmptyFile");
    }


    @Test
    void deleteFile() {
        //given
        Model model = mock(Model.class);
        Integer fileId = 1;

        //when
        fileController.deleteFile(fileId, model);

        //then
        verify(fileService, atMostOnce()).deleteFile(fileId);
        verify(model).addAttribute("message", "SuccessDeleteFile");
    }

    @Test
    void downloadFile() {
        //given
        Integer fileId = 1;
        File file = mock(File.class);
        when(fileService.getFile(fileId)).thenReturn(file);
        when(file.getData()).thenReturn(new byte[16]);

        //when
        fileController.downloadFile(fileId);

        //then
        verify(fileService, atMostOnce()).getFile(fileId);
    }

}