package com.udacity.jwdnd.course1.cloudstorage.selenium.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.stream.Collectors;

public class HomePage extends Page {

    private WebDriver webDriver;

    private final By fileTab = By.id("nav-files-tab");
    private final By noteTab = By.id("nav-notes-tab");
    private final By credentialTab = By.id("nav-credentials-tab");
    private final By logoutButton = By.id("logout-button");
    private final By addNoteButton = By.id("add-note-button");
    private final By inputTitle = By.id("note-title");
    private final By inputDescription = By.id("note-description");
    private final By saveChangesButton = By.name("save-changes-button");
    private final By messageText = By.name("message-text");
    private final By button = By.name("button");
    private final By noteTitle = By.name("title");
    private final By noteDescription = By.name("description");
    private final By addCredentialButton = By.id("add-credential-button");
    private final By credentialUrl = By.name("url");
    private final By inputCredentialUrl = By.id("credential-url");
    private final By credentialUsername = By.name("username");
    private final By inputCredentialUsername = By.id("credential-username");
    private final By credentialPassword = By.name("password");
    private final By inputCredentialPassword = By.id("credential-password");


    public HomePage(WebDriver driver) {
        super(driver);
        this.webDriver = driver;
    }

    public void logout() {
        waitUntilElementIsVisible(logoutButton);
        clickButton(logoutButton);
    }

    public void waitUntilPageIsLoaded() {
        waitUntilElementIsVisible(logoutButton);
        waitUntilElementIsVisible(fileTab);
        waitUntilElementIsVisible(noteTab);
        waitUntilElementIsVisible(credentialTab);
    }

    public void clickAddNote() {
        goToNoteTab();
        clickAddNoteButton();
    }

    public void addNote(String title, String description) {
        waitUntilElementIsVisible(saveChangesButton);
        typeTitle(title);
        typeDescription(description);
        clickSaveChanges();
    }


    public boolean checkMessage(String message) {
        waitUntilElementIsVisible(messageText);
        String textMessage = getMessageText();
        if (!textMessage.equals(message)) {
            return false;
        }
        clickContinue();
        return true;
    }

    public boolean checkNote(String title, String description) {
        goToNoteTab();
        if (getNumberOfValues(noteTitle, title) > 0 || getNumberOfValues(noteDescription, description) > 0) {
            return false;
        }
        return true;
    }

    private int getNumberOfValues(By locator, String value) {
        return findElements(locator).stream().filter(it -> it.getText().equals(value)).collect(Collectors.toList()).size();
    }

    public boolean checkIfNoteIsDeleted(String title) {
        goToNoteTab();
        if (findElements(By.linkText(title)).size() > 0) {
            return false;
        }
        return true;
    }

    public void deleteNote(String title) {
        goToNoteTab();
        findElement(By.xpath("//th[text()='" + title + "']/..//a[@name='delete-button']")).click();
    }

    public void editNote(String title) {
        goToNoteTab();
        findElement(By.xpath("//th[text()='" + title + "']/..//button[@name='edit-button']")).click();
    }

    public void deleteCredential(String url) {
        goToCredentialTab();
        findElement(By.xpath("//th[text()='" + url + "']/..//a[@name='delete-button']")).click();
    }

    public void editCredential(String url) {
        goToCredentialTab();
        findElement(By.xpath("//th[text()='" + url + "']/..//button[@name='edit-button']")).click();
    }

    public void goToNoteTab() {
        clickButton(noteTab);
        if (!findElement(addNoteButton).isDisplayed()) {
            clickButton(noteTab);
        }
        waitUntilElementIsVisible(addNoteButton);
    }

    public void goToCredentialTab() {
        clickButton(credentialTab);
        if (!findElement(addCredentialButton).isDisplayed()) {
            clickButton(credentialTab);
        }
        waitUntilElementIsVisible(addCredentialButton);
    }

    public void clickAddCredential() {
        goToCredentialTab();
        clickAddCredentialButton();
    }

    public void addCredential(String url, String username, String password) {
        waitUntilElementIsVisible(credentialUrl);
        typeUrl(url);
        typeUsername(username);
        typePassword(password);
        clickSaveChanges();
    }

    public boolean checkCredential(String url) {
        goToCredentialTab();
        if (getNumberOfValues(credentialUrl, url) > 0) {
            return true;
        }
        return false;
    }

    public boolean checkIfCredentialIsDeleted(String url, String username) {
        goToCredentialTab();
        if (findElements(By.linkText(url)).size() > 0 && findElements(By.linkText(username)).size() > 0) {
            return false;
        }
        return true;
    }

    private void typeTitle(String valueTitle) {
        typeValue(inputTitle, valueTitle);
    }

    private void typeDescription(String valueDescription) {
        typeValue(inputDescription, valueDescription);
    }

    private void clickSaveChanges() {
        try {
            clickButton(saveChangesButton);
        } catch (Exception e) {
            findElements(saveChangesButton).get(1).click();
        }
    }

    private void clickAddNoteButton() {
        clickButton(addNoteButton);
    }

    private String getMessageText() {
        return findElement(messageText).getText();
    }

    private void clickContinue() {
        clickButton(button);
    }

    private void typeUrl(String url) {
        typeValue(inputCredentialUrl, url);
    }

    private void typeUsername(String username) {
        typeValue(inputCredentialUsername, username);
    }

    private void typePassword(String password) {
        typeValue(inputCredentialPassword, password);
    }

    private void clickAddCredentialButton() {
        clickButton(addCredentialButton);
    }

}
