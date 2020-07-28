package com.udacity.jwdnd.course1.cloudstorage.selenium.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignupPage extends Page {

    private WebDriver webDriver;

    private final By loginBackLink = By.id("back-login");
    private final By firstNameInput = By.id("inputFirstName");
    private final By lastNameInput = By.id("inputLastName");
    private final By usernameInput = By.id("inputUsername");
    private final By passwordInput = By.id("inputPassword");
    private final By signUpButton = By.id("sign-up-button");
    private final By successMessage = By.id("success-message");

    public SignupPage(WebDriver driver) {
        super(driver);
        this.webDriver = driver;
    }

    public void signup(String username, String password) {
        waitForLoadedPage();
        typeFirstName();
        typeLastName();
        typeUsername(username);
        typePassword(password);
        clickSignUpButton();
    }

    public void goToLoginPage(){
        clickButton(loginBackLink);
    }
    private void waitForLoadedPage(){
        webDriver.findElement(firstNameInput).isDisplayed();
    }

    private void typeFirstName(){
        typeValue(firstNameInput, "John");
    }

    private void typeLastName(){
        typeValue(lastNameInput, "Smith");
    }

    private void typeUsername(String username){
       typeValue(usernameInput, username);
    }

    private void typePassword(String password){
      typeValue(passwordInput, password);
    }

    private void clickSignUpButton(){
        clickButton(signUpButton);
    }

    public boolean isSuccessMessageDisplayed(){
      return findElement(successMessage).isDisplayed();
    }

    public String getSuccessMessageTest(){
        return findElement(successMessage).getText();
    }
}
