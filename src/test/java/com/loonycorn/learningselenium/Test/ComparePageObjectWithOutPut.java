package com.loonycorn.learningselenium.Test;

import com.loonycorn.learningselenium.pages.CarRegInformation;
import com.loonycorn.learningselenium.pages.CarRegSiteMainPage;
import com.loonycorn.learningselenium.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComparePageObjectWithOutPut {
    private static final String SITE = "https://car-checking.com/";
    private static final String HOME = SITE + "home";
    private static final String FILE_PATH = "./src/test/java/com/loonycorn/learningselenium/store/car_input - V6.txt";
    private static final String OUTPUT_FILE_PATH = "./src/test/java/com/loonycorn/learningselenium/store/car_output - V6.txt";
    private static final String REGISTRATION_PATTERN = "\\b[A-Z]{2}\\d{2}\\s?[A-Z]{3}\\b";

    private WebDriver driver;
    private CarRegSiteMainPage carRegSiteMainPage;
    private CarRegInformation carRegInformation;
    private List<String> registrationNumbers;

    @BeforeTest
    public void setup() {
        registrationNumbers = readRegistrationNumbers(FILE_PATH);
        System.out.println("Extracted Registration Numbers: " + registrationNumbers + " (Count: " + registrationNumbers.size() + ")");

        driver = DriverFactory.initializeDriver(DriverFactory.BrowserType.CHROME);
        carRegSiteMainPage = new CarRegSiteMainPage(driver);
        carRegInformation = new CarRegInformation(driver);
        driver.get(SITE);
    }

    @Test
    public void testCarReg() {
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        for (String regNumber : registrationNumbers) {
            List<String> carDetails = new ArrayList<>();

            carRegSiteMainPage.clickRegForm(regNumber);
            delay(1000);
            carRegSiteMainPage.clickFirstTitle();
            carRegSiteMainPage.SubButton();
            delay(2000);

            try {
                String alertMessage = carRegSiteMainPage.getAlertMessage();
                System.out.println("----------------------------------");
                System.out.println("Alert Message: " + alertMessage + " for registration number: " + regNumber);
                continue;
            } catch (org.openqa.selenium.NoSuchElementException e) {
                System.out.println("----------------------------------");
                System.out.println("No alert message found for registration number: " + regNumber);
            }

            System.out.println("Processing registration number: " + regNumber);

            delay(2000);
            carDetails.add(regNumber);
            carDetails.add(carRegInformation.getCarMake());
            carDetails.add(carRegInformation.getCarModel());
            carDetails.add(carRegInformation.getCarYear());

            String formattedRegNumber = regNumber.replaceAll("(\\w{2})(\\d{2})(\\w{3})", "$1$2 $3");
            carDetails.add(formattedRegNumber);

            System.out.println("Car Details: " + carDetails);
            compareWithOutputFile(carDetails);

            driver.get(HOME);
        }
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private List<String> readRegistrationNumbers(String filePath) {
        List<String> registrationNumbers = new ArrayList<>();
        try {
            String fileContent = Files.readString(Path.of(filePath));
            Pattern pattern = Pattern.compile(REGISTRATION_PATTERN);
            Matcher matcher = pattern.matcher(fileContent);

            while (matcher.find()) {
                registrationNumbers.add(matcher.group());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return registrationNumbers;
    }

    private void compareWithOutputFile(List<String> carDetails) {
        try {
            List<String> fileLines = Files.readAllLines(Path.of(OUTPUT_FILE_PATH));
            String carDetailsString = carDetails.get(0).replaceAll("(\\w{2})(\\d{2})(\\w{3})", "$1$2 $3")
                    + "," + String.join(",", carDetails.subList(1, carDetails.size()))
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
    }

    private static void delay(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

