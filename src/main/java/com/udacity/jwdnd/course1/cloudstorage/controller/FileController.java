package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class FileController {
    private final FileService fileService;
    private final UserMapper userMapper;

    @PostMapping("/upload")
    public String addFile(@RequestParam("fileUpload") MultipartFile file, Authentication authentication, Model model) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String contentType = file.getContentType();
        String fileSize = String.valueOf(file.getSize());
        byte[] fileData = file.getBytes();

        User user = userMapper.getUser(authentication.getName());

        if (file.isEmpty()) {
            model.addAttribute("message", "EmptyFile");
            return "result";
        }
        if (!fileService.isUniqueName(fileName, user.getUserId())) {
            model.addAttribute("message", "NotUniqueFileName");
            return "result";
        }
        model.addAttribute("message", "SuccessAddFile");

        File createdFile = new File(null, fileName, contentType, fileSize, user.getUserId(), fileData, LocalDate.now());
        System.out.println(file);
        fileService.createFile(createdFile);

        return "result";
    }

    @GetMapping("/delete/{file_id}")
    public String deleteFile(@PathVariable(value = "file_id") Integer fileId, Model model) {
        fileService.deleteFile(fileId);
        model.addAttribute("message", "SuccessDeleteFile");

        return "result";
    }

    @GetMapping("/download/{file_id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable(value = "file_id") Integer fileId) {
        File file = fileService.getFile(fileId);
        byte[] data = file.getFileData();
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getFileName())
                .contentLength(data.length)
                .body(resource);
    }
}
