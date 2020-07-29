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
public class CredentialST {
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
    public static void cleanUp() {
        driver.quit();
    }

    @AfterEach
    public void cleanUpEach() {
        driver.close();
    }

    @BeforeEach
    public void beforeEach() {
        driver = new ChromeDriver();
        baseURL = "http://localhost:" + port + "/login";
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        driver.get(baseURL + "/login");
    }

    @Test
    public void add_credential() {
        String username = "username";
        String password = "password";
        loginPage.loginUser(username, password);

        String url = "example url" + RandomStringUtils.randomAlphabetic(2);
        homePage.waitUntilPageIsLoaded();
        homePage.clickAddCredential();
        homePage.addCredential(url, username, password);

        String message = "Your credential was successfully added.";
        Assert.isTrue(homePage.checkMessage(message), "Message is incorrect");
        homePage.waitUntilPageIsLoaded();
        Assert.isTrue(homePage.checkCredential(url), "Saved credential is not correctly presented");
    }

    @Test
    public void delete_credential() {
        String username = "username";
        String password = "password";
        loginPage.loginUser(username, password);

        String url = "example url" + RandomStringUtils.randomAlphabetic(2);
        homePage.waitUntilPageIsLoaded();
        homePage.clickAddCredential();
        homePage.addCredential(url, username, password);

        String message = "Your credential was successfully added.";
        Assert.isTrue(homePage.checkMessage(message), "Message is incorrect");
        homePage.waitUntilPageIsLoaded();
        homePage.deleteCredential(url);

        String deleteCredentialMessage = "Your credential was successfully deleted.";
        Assert.isTrue(homePage.checkMessage(deleteCredentialMessage), "Message is incorrect");
        homePage.waitUntilPageIsLoaded();
        Assert.isTrue(homePage.checkIfCredentialIsDeleted(url, username), "Credential was not deleted");
    }

    @Test
    public void edit_credential() {
        String username = "username";
        String password = "password";
        loginPage.loginUser(username, password);

        String url = "example url" + RandomStringUtils.randomAlphabetic(2);
        homePage.waitUntilPageIsLoaded();
        homePage.clickAddCredential();
        homePage.addCredential(url, username, password);

        String message = "Your credential was successfully added.";
        Assert.isTrue(homePage.checkMessage(message), "Message is incorrect");

        String editedUrl = "edited url" + RandomStringUtils.randomAlphabetic(2);
        homePage.waitUntilPageIsLoaded();
        homePage.editCredential(url);
        homePage.addCredential(editedUrl, username, password);

        String updatedCredentialMessage = "Your credential was successfully updated.";
        Assert.isTrue(homePage.checkMessage(updatedCredentialMessage), "Message is incorrect");
        homePage.waitUntilPageIsLoaded();
        Assert.isTrue(homePage.checkCredential(editedUrl), "Credential was not updated");
    }
}
