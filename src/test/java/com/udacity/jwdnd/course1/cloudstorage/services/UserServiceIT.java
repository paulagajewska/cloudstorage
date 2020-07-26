package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class UserServiceIT {

    @Autowired
    UserService userService;

    @Test
    void isUserAvailable() {
        Assert.isTrue(!userService.isUserAvailable("username"),
                "The user was found with the same name");
        Assert.isTrue(userService.isUserAvailable("username2"),
                "The user was found with the same name");
    }

    @Test
    void correctCreateUser() {
        User user = new User(
                null,
                "new_username",
                "salt",
                "password",
                "firstName",
                "lastName");

        int userId = userService.createUser(user);
        User createdUser = userService.getUser("new_username");

        Assert.isTrue(createdUser.getUserId() == userId, "User id is incorrect");
        Assert.isTrue(createdUser.getUsername() == user.getUsername(), "Username is incorrect");
        Assert.isTrue(createdUser.getFirstName() == user.getFirstName(), "First name is incorrect");
        Assert.isTrue(createdUser.getLastName() == user.getLastName(), "Last name is incorrect");
    }

}