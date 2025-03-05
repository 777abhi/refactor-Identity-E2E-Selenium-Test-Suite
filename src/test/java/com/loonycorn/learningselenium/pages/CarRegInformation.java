package com.loonycorn.learningselenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CarRegInformation {
    private WebDriver driver;

//    @FindBy(id = "subForm")
//    private WebElement CarRegInfo;

    @FindBy(css = ".pt-5 tr:nth-child(1) > .td-right")
    private WebElement CarMake;

    @FindBy(css = ".pt-5 tr:nth-child(2) > .td-right")
    private WebElement CarModel;

    @FindBy(css = ".pt-5 tr:nth-child(3) > .td-right")
    private WebElement CarColour;

    @FindBy(css = ".pt-5 tr:nth-child(4) > .td-right")
    private WebElement CarYear;


    public CarRegInformation(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isCarRegInfoDisplayed() {
        return driver.getCurrentUrl().contains("report");
    }

    public String clickCarRegInfo() {
        WebElement CarRegInfo = driver.findElement(By.id("subForm"));
        return CarRegInfo.getText();
    }

    public String getCarMake() {
        return CarMake.getText();
    }
    public String getCarModel() {
        return CarModel.getText();
    }
    public String getCarColour(){
        return CarColour.getText();
    }
    public String getCarYear(){
        return CarYear.getText();
    }
    
    
}