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
public class LoginST {

    @LocalServerPort
    public int port;

    public static WebDriver driver;

    public String baseURL;

    private LoginPage loginPage;
    private HomePage  homePage;

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
    public void unauthorized_user_can_not_login() {
        String errorMessage = "invalid username or password";
        loginPage.loginUser("invalid_username", "inavlid_password");

        Assert.isTrue(loginPage.isErrorDisplayed(), "Error is not presented");
        Assert.isTrue(loginPage.getErrorMessage().contains(errorMessage), "Error message is incorrect");
    }

    @Test
    public void authorized_user_can_log_in() {
        loginPage.loginUser("username", "password");
        String url = driver.getCurrentUrl();

        Assert.isTrue(url.contains("/home"), "User was redirected to wrong page");
    }

    @Test
    public void user_can_log_out() {
        loginPage.loginUser("username", "password");

        String homeUrl = driver.getCurrentUrl();
        Assert.isTrue(homeUrl.contains("/home"), "User was redirected to wrong page");

        homePage.waitUntilPageIsLoaded();
        homePage.logout();
        loginPage.waitUntilPageIsLoaded();
        String loginUrl = driver.getCurrentUrl();
        //TODO check correct message
        Assert.isTrue(loginUrl.contains("/login"), "User was redirected to wrong page");
    }


}
