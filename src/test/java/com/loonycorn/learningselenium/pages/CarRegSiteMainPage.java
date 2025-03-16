package com.loonycorn.learningselenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CarRegSiteMainPage {
    private final WebDriver driver;

    @FindBy(id = "subForm1")
    private WebElement regFormInput;

    @FindBy(css = "h1.fw-bold.first-title[style*='color: rgb(70, 160, 148)']")
    private WebElement firstTitle;

    @FindBy(css = "button.check-now-button")
    private WebElement submitButton;

    @FindBy(css = "div.alert.alert-danger")
    private WebElement alertMessage;

    public CarRegSiteMainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterRegistrationNumber(String regNumber) {
        regFormInput.clear();
        regFormInput.sendKeys(regNumber);
    }

    public void clickFirstTitle() {
        if (firstTitle.isDisplayed()) {
            firstTitle.click();
        } else {
            throw new IllegalStateException("First title is not displayed");
        }
    }

    public void clickSubmitButton() {
        submitButton.click();
    }

    public String getAlertMessage() {
        return alertMessage.getText();
    }

    public boolean isAlertMessageDisplayed() {
        return alertMessage.isDisplayed();
    }
}