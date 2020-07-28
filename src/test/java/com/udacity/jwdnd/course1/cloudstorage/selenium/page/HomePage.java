package com.udacity.jwdnd.course1.cloudstorage.selenium.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends Page {

    private WebDriver webDriver;

    private final By fileTab = By.id("nav-files-tab");
    private final By noteTab = By.id("nav-notes-tab");
    private final By credentialTab = By.id("nav-credentials-tab");
    private final By logoutButton = By.id("logout-button");
    private final By addNoteButton = By.id("add-note-button");
    private final By title = By.name("title");
    private final By description = By.name("description");
    private final By saveChangesButton = By.name("save-changes-button");
    private final By messageText = By.name("message-text");
    private final By button = By.name("button");
    private final By noteTitle = By.name("title");
    private final By noteDescription = By.name("description");
    private final By deleteButton = By.name("delete-button");
    private final By editButton = By.name("edit-button");
    private final By addCredentialButton = By.id("add-credential-button");
    private final By credentialUrl = By.name("url");
    private final By credentialUsername = By.name("username");
    private final By credentialPassword = By.name("password");


    public HomePage(WebDriver driver) {
        super(driver);
        this.webDriver = driver;
    }

    public void logout() {
        clickButton(logoutButton);
    }

    public void waitUntilPageIsLoaded() {
        waitUntilElementIsVisible(logoutButton);
        waitUntilElementIsVisible(fileTab);
        waitUntilElementIsVisible(noteTab);
        waitUntilElementIsVisible(credentialTab);
    }

    public void addNote(String title, String description) {
        goToNoteTab();
        clickAddNoteButton();
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
        if (!findElements(noteTitle).get(0).getText().equals(title)
                || !findElements(noteDescription).get(0).equals(description)) {
            return false;
        }
        return true;
    }

    public boolean checkIfNoteIsDeleted(String title) {
        goToNoteTab();
        if (findElements(By.linkText(title)).size() > 0) {
            return false;
        }
        return true;
    }

    public void deleteFirstNote() {
        goToNoteTab();
        findElements(deleteButton).get(0).click();
    }

    public void deleteFirstCredential() {
        goToCredentialTab();
        findElements(deleteButton).get(0).click();
    }

    public void goToNoteTab() {
        clickButton(noteTab);
        waitUntilElementIsVisible(addNoteButton);
    }

    public void goToCredentialTab() {
        clickButton(credentialTab);
        waitUntilElementIsVisible(addCredentialButton);
    }

    public void addCredential(String url, String username, String password) {
        goToCredentialTab();
        clickAddCredentialButton();
        waitUntilElementIsVisible(credentialUrl);
        typeUrl(url);
        typeUsername(username);
        typePassword(password);
        clickSaveChanges();
    }

    public boolean checkCredential(String url, String username) {
        goToCredentialTab();
        if (!findElements(credentialUrl).get(0).getText().equals(url)
                || !findElements(credentialUsername).get(0).equals(username)
                || findElements(credentialPassword).get(0).getText() == null) {
            return false;
        }
        return true;
    }

    public boolean checkIfCredentialIsDeleted(String url, String username) {
        goToCredentialTab();
        if (findElements(By.linkText(url)).size() > 0 && findElements(By.linkText(username)).size() > 0) {
            return false;
        }
        return true;
    }

    private void typeTitle(String valueTitle) {
        typeValue(title, valueTitle);
    }

    private void typeDescription(String valueDescription) {
        typeValue(description, valueDescription);
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
        typeValue(credentialUrl, url);
    }

    private void typeUsername(String username) {
        typeValue(credentialUsername, username);
    }

    private void typePassword(String password) {
        typeValue(credentialPassword, password);
    }

    private void clickAddCredentialButton() {
        clickButton(addCredentialButton);
    }

}
