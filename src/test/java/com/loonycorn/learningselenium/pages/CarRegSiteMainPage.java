package com.loonycorn.learningselenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.openqa.selenium.By.*;

public class CarRegSiteMainPage {
    private WebDriver driver;
    @FindBy(id = "subForm1")
    private WebElement RegFormInput;

    @FindBy(css = "h1.fw-bold.first-title[style*='color: rgb(70, 160, 148)']")
    private WebElement FirstTitle;

    @FindBy(css = "button.check-now-button")
    private WebElement SubmitButton;

    @FindBy(css = "div.alert.alert-danger")
    private WebElement AlertMessage;

//    RegFormInput.clear();
//RegFormInput.sendKeys(regNumbers);




    public CarRegSiteMainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public void clickRegForm(String regNumbers) {
        RegFormInput.sendKeys(regNumbers);
    }
    public void clickFirstTitle() {

        assert(FirstTitle.isDisplayed());
    }

    public void SubButton() {
        SubmitButton.click();
    }
    public String getAlertMessage() {
        return AlertMessage.getText();
    }

//    public boolean isAlertMessageDisplayed() {
//        return AlertMessage.isDisplayed();
//    }

        


}