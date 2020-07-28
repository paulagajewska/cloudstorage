package com.udacity.jwdnd.course1.cloudstorage.selenium.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends Page{

    private WebDriver webDriver;

    private final By usernameInput = By.id("inputUsername");
    private final By passwordInput = By.id("inputPassword");
    private final By loginButton = By.id("login-button");
    private final By signUpLink = By.id("signup");
    private final By errorMessage = By.id("error-message");

    public LoginPage(WebDriver driver) {
        super(driver);
        this.webDriver = driver;
    }

    public void waitUntilPageIsLoaded(){
        waitUntilElementIsVisible(loginButton);
    }

    public void goToSignUpPage(){
        clickButton(signUpLink);
    }

    public void loginUser(String username, String password){
        waitUntilPageIsLoaded();
        typeUsername(username);
        typePassword(password);
        login();
    }

    private void typeUsername(String username){
        typeValue(usernameInput, username);
    }

    private void typePassword(String password) {
        typeValue(passwordInput, password);
    }

    private void login(){
        clickButton(loginButton);
    }

    public boolean isErrorDisplayed() {
        return findElement(errorMessage).isDisplayed();
    }

    public String getErrorMessage(){
        WebElement element = findElement(errorMessage);
        return element.getText();
    }
}
