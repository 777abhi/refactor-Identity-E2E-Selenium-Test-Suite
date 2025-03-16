package com.loonycorn.learningselenium.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

public class ReadInputFileReg {

    private static final String FILE_PATH = "./src/test/java/com/loonycorn/learningselenium/store/car_input - V6.txt";
    private static final String REGISTRATION_PATTERN = "\\b[A-Z]{2}\\d{2}\\s?[A-Z]{3}\\b";

    public static void main(String[] args) {
        List<String> registrationNumbers = extractRegistrationNumbers(FILE_PATH, REGISTRATION_PATTERN);
        System.out.println("Extracted Registration Numbers:");
        System.out.println(registrationNumbers);
    }

    public static List<String> extractRegistrationNumbers(String filePath, String pattern) {
        List<String> registrationNumbers = new ArrayList<>();
        try {
            String fileContent = Files.readString(Path.of(filePath));
            Pattern compiledPattern = Pattern.compile(pattern);
            Matcher matcher = compiledPattern.matcher(fileContent);

            while (matcher.find()) {
                registrationNumbers.add(matcher.group());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return registrationNumbers;
    }
}
