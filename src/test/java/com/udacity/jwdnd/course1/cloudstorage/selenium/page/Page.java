package com.udacity.jwdnd.course1.cloudstorage.selenium.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

abstract class Page {
    private WebDriver webDriver;
    private final Integer timeoutInSeconds = 10;

    public Page(WebDriver driver) {
        this.webDriver = driver;
    }

    public WebElement findElement(By locator) {
        return webDriver.findElement(locator);
    }

    public List<WebElement> findElements(By locator) {
        return webDriver.findElements(locator);
    }

    public void waitUntilElementIsVisible(By locator) {
        new WebDriverWait(webDriver, timeoutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void typeValue(By locator, String text) {
        WebElement element = findElement(locator);
        element.sendKeys(text);
    }

    public void clickButton(By locator) {
        WebElement element = findElement(locator);
        element.click();
    }

}
