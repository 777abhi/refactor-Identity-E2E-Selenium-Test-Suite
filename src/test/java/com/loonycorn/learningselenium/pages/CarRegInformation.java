package com.loonycorn.learningselenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CarRegInformation {
    private final WebDriver driver;

    @FindBy(css = ".pt-5 tr:nth-child(1) > .td-right")
    private WebElement carMake;

    @FindBy(css = ".pt-5 tr:nth-child(2) > .td-right")
    private WebElement carModel;

    @FindBy(css = ".pt-5 tr:nth-child(3) > .td-right")
    private WebElement carColour;

    @FindBy(css = ".pt-5 tr:nth-child(4) > .td-right")
    private WebElement carYear;

    public CarRegInformation(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isCarRegInfoDisplayed() {
        return driver.getCurrentUrl().contains("report");
    }

    public String getCarRegInfoText() {
        WebElement carRegInfo = driver.findElement(By.id("subForm"));
        return carRegInfo.getText();
    }

    public String getCarMake() {
        return carMake.getText();
    }

    public String getCarModel() {
        return carModel.getText();
    }

    public String getCarColour() {
        return carColour.getText();
    }

    public String getCarYear() {
        return carYear.getText();
    }
}