package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Controller
@RequestMapping("/file")
public class FileController {
    // private final String UPLOAD_DIR = "./uploads/";
    private final FileService fileService;
    private final UserMapper userMapper;

    public FileController(FileService fileService, UserMapper userMapper) {
        this.fileService = fileService;
        this.userMapper = userMapper;
    }

    @PostMapping("/upload")
    public String addFile(@RequestParam("fileUpload") MultipartFile file, Authentication authentication,
                          Model model) throws IOException {

        if (file.isEmpty()) {
//TODO add alert that file is empty
        }
        //TODO add alert that user can not add file with the same name
        //TODO add alert that user can not add too big file

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String contentType = file.getContentType();
        String fileSize = String.valueOf(file.getSize());
        byte[] fileData = file.getBytes();

        User user = userMapper.getUser(authentication.getName());

        File createdFile = new File(null, fileName, contentType, fileSize, user.getUserId(), fileData, LocalDate.now());
        System.out.println(createdFile);
        fileService.createFile(createdFile);

        model.addAttribute("uploadedFiles", fileService.getAllFile(user.getUserId()));
        return "redirect:/home";
    }

    @GetMapping("/delete/{file_id}")
    public String deleteFile(@PathVariable(value = "file_id") Integer fileId, Authentication authentication, Model model){
        User user = userMapper.getUser(authentication.getName());
        fileService.deleteFile(fileId);
        model.addAttribute("uploadedFiles", fileService.getAllFile(user.getUserId()));
        return "redirect:/home";
    }

    @GetMapping("/download/{file_id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable(value = "file_id") Integer fileId, Authentication authentication, Model model) {
        File file = fileService.getFile(fileId);
        byte[] data = file.getFileData();
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getFileName())
                .contentLength(data.length)
                .body(resource);
    }
}
