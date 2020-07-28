package com.udacity.jwdnd.course1.cloudstorage.selenium.test;

import com.udacity.jwdnd.course1.cloudstorage.selenium.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.selenium.page.SignupPage;
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
public class SignupST {

    @LocalServerPort
    public int port;

    public static WebDriver driver;

    public String baseURL;

    private SignupPage signupPage;
    private LoginPage loginPage;

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
        baseURL = "http://localhost:" + port;
        loginPage = new LoginPage(driver);
        signupPage = new SignupPage(driver);
        driver.get(baseURL + "/signup");
    }

    @Test
    public void new_user_can_log_in() {
        String username = "smith";
        String password = "password";

        signupPage.signup(username, password);
        signupPage.goToLoginPage();

        Assert.isTrue(signupPage.isSuccessMessageDisplayed(), "Success message is not presented");
        Assert.isTrue(signupPage.getSuccessMessageTest().equals("You successfully signed up! Please continue to the login page."),
                "Success message is incorrect");

        signupPage.goToLoginPage();
        String loginUrl = driver.getCurrentUrl();
        Assert.isTrue(loginUrl.contains("/login"), "User was redirected to wrong page");

        loginPage.loginUser(username, password);

        String homeUrl = driver.getCurrentUrl();
        Assert.isTrue(homeUrl.contains("/home"), "User was redirected to wrong page");
    }
}
