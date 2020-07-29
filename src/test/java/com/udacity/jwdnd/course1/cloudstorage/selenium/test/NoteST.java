package com.udacity.jwdnd.course1.cloudstorage.selenium.test;

import com.udacity.jwdnd.course1.cloudstorage.selenium.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.selenium.page.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.util.Assert;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteST {
    @LocalServerPort
    public int port;

    public static WebDriver driver;

    public String baseURL;

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @AfterAll
    public static void cleanUp(){
        driver.quit();
    }

    @AfterEach
    public void cleanUpEach() {
        driver.close();
    }

    @BeforeEach
    public void beforeEach() {
        driver = new ChromeDriver();
        baseURL = "http://localhost:" + port;
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        driver.get(baseURL + "/login");
    }

    @Test
    public void add_note() {
        String username = "username";
        String password = "password";
        loginPage.loginUser(username, password);

        String title = "example title" + RandomStringUtils.randomAlphabetic(2);
        String description = "example note description" + RandomStringUtils.randomAlphabetic(10);
        homePage.waitUntilPageIsLoaded();
        homePage.clickAddNote();
        homePage.addNote(title, description);

        String message = "Your note was successfully saved.";
        Assert.isTrue(homePage.checkMessage(message), "Message is incorrect");
        homePage.waitUntilPageIsLoaded();
        Assert.isTrue(!homePage.checkNote(title, description), "Saved note is not correctly presented");
    }

    @Test
    public void delete_note() {
        String username = "username";
        String password = "password";
        loginPage.loginUser(username, password);

        String title = "example title" + RandomStringUtils.randomAlphabetic(2);
        String description = "example note description" + RandomStringUtils.randomAlphabetic(10);
        homePage.waitUntilPageIsLoaded();
        homePage.clickAddNote();
        homePage.addNote(title, description);

        String saveNoteMessage = "Your note was successfully saved.";
        Assert.isTrue(homePage.checkMessage(saveNoteMessage), "Message is incorrect");
        homePage.waitUntilPageIsLoaded();
        homePage.deleteNote(title);

        String deleteNoteMessage = "Your note was successfully deleted.";
        Assert.isTrue(homePage.checkMessage(deleteNoteMessage), "Message is incorrect");
        homePage.waitUntilPageIsLoaded();
        Assert.isTrue(homePage.checkIfNoteIsDeleted(title), "Note was not deleted");
    }

    @Test
    public void edit_note() {
        String username = "username";
        String password = "password";
        loginPage.loginUser(username, password);

        String title = "example title" + RandomStringUtils.randomAlphabetic(2);
        String description = "example note description" + RandomStringUtils.randomAlphabetic(10);
        homePage.waitUntilPageIsLoaded();
        homePage.clickAddNote();
        homePage.addNote(title, description);

        String saveNoteMessage = "Your note was successfully saved.";
        Assert.isTrue(homePage.checkMessage(saveNoteMessage), "Message is incorrect");
        homePage.waitUntilPageIsLoaded();
        homePage.editNote(title);

        String editedTitle = "edit example title" + RandomStringUtils.randomAlphabetic(2);
        String editedDescription = "edit example note description" + RandomStringUtils.randomAlphabetic(10);
        homePage.waitUntilPageIsLoaded();
        homePage.addNote(editedTitle, editedDescription);

        String deleteNoteMessage = "Your note was successfully updated.";
        Assert.isTrue(homePage.checkMessage(deleteNoteMessage), "Message is incorrect");
        homePage.waitUntilPageIsLoaded();
        Assert.isTrue(!homePage.checkNote(editedTitle, editedDescription), "Updated note is not correctly presented");
    }
}
