package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class FileServiceIT {

    @Autowired
    FileService fileService;

    @Autowired
    UserService userService;

    @Test
    void createFile() {
        File file = new File(
                null,
                "file_name",
                "content_type",
                "size",
                1,
                new byte[16],
                LocalDate.now());

        Assert.isTrue(fileService.createFile(file) > 0);

    }

    @Test
    void getAllFile() {
        User user = new User(null, "xusername", "xsalt", "xpassword", "xfirstName", "xLastName");
        Integer userId = userService.createUser(user);

        File file = new File(
                null,
                "file_name",
                "content_type",
                "size",
                userId,
                new byte[16],
                LocalDate.now());

        fileService.createFile(file);
        fileService.createFile(file);

        Assert.isTrue(fileService.getAllFile(userId).size() == 2, "Incorrect number of files ");

    }

    @Test
    void deleteFile() {
        Integer fileId = createSampleFile();

        fileService.deleteFile(fileId);
        List<File> files = fileService.getAllFile(1);

        Assert.isTrue(!files.stream().anyMatch(it -> it.getId() == fileId), "File was not deleted");
    }

    @Test
    void getFile() {
        Integer fileId = createSampleFile();

        File file = fileService.getFile(fileId);
        Assert.isTrue(file.getId() == fileId, "File id is incorrect");
        Assert.isTrue(file.getName() == "file_name", "File name is incorrect");
        Assert.isTrue(file.getContentType() == "content_type", "File content type is incorrect");
        Assert.isTrue(file.getSize() == "size", "File size is incorrect");
        Assert.isTrue(file.getSaveDate().equals(LocalDate.now()), "File saved date is incorrect");
    }

    @Test
    void isUniqueName() {
        createSampleFile();

        String sameFileName = "file_name";
        String differentFileName = "different_file_name";

        Assert.isTrue(!fileService.isUniqueName(sameFileName, 1));
        Assert.isTrue(fileService.isUniqueName(differentFileName, 1));
    }

    private Integer createSampleFile() {
        File file = new File(
                null,
                "file_name",
                "content_type",
                "size",
                1,
                new byte[16],
                LocalDate.now());

        return fileService.createFile(file);
    }

}