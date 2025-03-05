package com.loonycorn.learningselenium.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

class ReadInputFileReg {

    public static void main(String[] args) {
        // Define the path to the file

        String filePath = "./src/test/java/com/loonycorn/learningselenium/store/car_input - V6.txt";

        // Define the registration number regex pattern
        String registrationPattern = "\\b[A-Z]{2}\\d{2}\\s?[A-Z]{3}\\b";

        try {
            // Read all lines from the file
            String fileContent = Files.readString(Path.of(filePath));

            // Compile the pattern and match against the file content
            Pattern pattern = Pattern.compile(registrationPattern);
            Matcher matcher = pattern.matcher(fileContent);

            // Loop through and print all matches in "getText" format
            System.out.println("Extracted Registration Numbers:");
            List<String> registrationNumbers = new ArrayList<>();
            while (matcher.find()) {
                String registration = matcher.group();
                registrationNumbers.add(registration);
            }
            System.out.println(registrationNumbers);


        }
        catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
