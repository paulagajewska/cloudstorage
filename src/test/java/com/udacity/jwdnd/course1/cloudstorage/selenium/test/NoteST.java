package com.udacity.jwdnd.course1.cloudstorage.selenium.test;

import com.udacity.jwdnd.course1.cloudstorage.selenium.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.selenium.page.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
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
        driver = new ChromeDriver();

    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
        driver = null;
    }

    @BeforeEach
    public void beforeEach() {
        baseURL = "http://localhost:" + port + "/login";
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        driver.get(baseURL + "/login");
    }

    @Test
    public void add_note() {
        String username = "username";
        String password = "password";
        loginPage.loginUser(username, password);

        String title = "example title";
        String description = "example note description";
        homePage.waitUntilPageIsLoaded();
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

        String title = "example title";
        String description = "example note description";
        homePage.waitUntilPageIsLoaded();
        homePage.addNote(title, description);

        String saveNoteMessage = "Your note was successfully saved.";
        Assert.isTrue(homePage.checkMessage(saveNoteMessage), "Message is incorrect");
        homePage.waitUntilPageIsLoaded();
        homePage.deleteFirstNote();

        String deleteNoteMessage = "Your note was successfully deleted.";
        Assert.isTrue(homePage.checkMessage(deleteNoteMessage), "Message is incorrect");
        homePage.waitUntilPageIsLoaded();
        Assert.isTrue(!homePage.checkIfNoteIsDeleted(title), "Note was not deleted");
    }

    //TODO add test to edit notes
}
