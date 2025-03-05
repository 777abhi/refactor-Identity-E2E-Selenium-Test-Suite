package com.loonycorn.learningselenium.Test;

import com.loonycorn.learningselenium.pages.CarRegInformation;
import com.loonycorn.learningselenium.pages.CarRegSiteMainPage;
import com.loonycorn.learningselenium.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.tracing.opentelemetry.SeleniumSpanExporter;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

public class ComparePageObjectWithOutPut {
    private static final String SITE = "https://car-checking.com/";
    private static final String HOME = SITE + "home";

    private WebDriver driver;
    private CarRegSiteMainPage CarRegSiteMainPage;
    private CarRegInformation CarRegInformation;

    private List<String> registrationNumbers; // Moved to class level

    private List<String> readRegistrationNumbers(String filePath) {
        List<String> registrationNumbers = new ArrayList<>();
        String registrationPattern = "\\b[A-Z]{2}\\d{2}\\s?[A-Z]{3}\\b";
        try {
            String fileContent = Files.readString(Path.of(filePath));
            Pattern pattern = Pattern.compile(registrationPattern);
            Matcher matcher = pattern.matcher(fileContent);

            while (matcher.find()) {
                registrationNumbers.add(matcher.group());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return registrationNumbers;
    }

    @BeforeTest
    public void setup() {
        String filePath = "./src/test/java/com/loonycorn/learningselenium/store/car_input - V6.txt";
        registrationNumbers = readRegistrationNumbers(filePath);
        System.out.println("Extracted Registration Numbers: " + registrationNumbers + " (Count: " + registrationNumbers.size() + ")");

        driver = DriverFactory.initializeDriver(DriverFactory.BrowserType.CHROME);
        CarRegSiteMainPage = new CarRegSiteMainPage(driver); //instantiate the method on private class
        CarRegInformation = new CarRegInformation(driver);
        driver.get(SITE);


    }

    private static void delay(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCarReg() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String regNumbers : registrationNumbers) {
            List<String> carDetails = new ArrayList<>();

            CarRegSiteMainPage.clickRegForm(regNumbers);
            delay(1000); // Optional delay between iterations
            CarRegSiteMainPage.clickFirstTitle();
            CarRegSiteMainPage.SubButton();
            delay(2000);
            try {
                String alertMessage = CarRegSiteMainPage.getAlertMessage();
                System.out.println("----------------------------------");
                System.out.println("Alert Message: " + alertMessage + " for registration number: " + regNumbers);
                continue;
            } catch (org.openqa.selenium.NoSuchElementException e) {
                System.out.println("----------------------------------");
                System.out.println("No alert message found for registration number: " + regNumbers);
            }

            System.out.println("Processing registration number: " + regNumbers);

            delay(2000);
            CarRegInformation.clickCarRegInfo();
            String carMake = CarRegInformation.getCarMake();
            String carModel = CarRegInformation.getCarModel();
            String carYear = CarRegInformation.getCarYear();
            carDetails.add(regNumbers);
            carDetails.add(carMake);
            carDetails.add(carModel);
            carDetails.add(carYear);
            System.out.println(carDetails.add(regNumbers.replaceAll("(\\w{2})(\\d{2})(\\w{3})", "$1$2 $3")));
            System.out.println("-------------------------");
            carDetails.stream()
                    .map(String::trim)
                    .forEach(s -> {
                        try {
                            List<String> fileLines = Files.readAllLines(Path.of("./src/test/java/com/loonycorn/learningselenium/store/car_output - V6.txt"));
                            String carDetailsString = carDetails.get(0).replaceAll("(\\w{2})(\\d{2})(\\w{3})", "$1$2 $3")
                                    + "," + String.join(",", carDetails.subList(1, Math.min(carDetails.size(), 4)))
                                    .replaceAll("\\[|\\]", "").replace(", ", ",");
                            for (int i = 0; i < fileLines.size(); i++) {
                                if (fileLines.get(i).equals(carDetailsString)) {
                                    System.out.println("Match found at line " + (i + 1) + ": " + fileLines.get(i));
                                    break;
                                }
                            }
                        } catch (IOException e) {
                            System.err.println("Error reading file: " + e.getMessage());
                        }
                    });

            driver.get(HOME);
        }
    }

            @AfterTest
        public void tearDown () {
            if (driver != null) {
                driver.quit();
            }
        }
    }

