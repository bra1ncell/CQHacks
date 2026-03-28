package com.cqhacks.racelive.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Skeleton from backend team (RaceResults.csv). Extend when you need structured results.
 */
public class RaceResultsReader {

    private final String fileName;

    public RaceResultsReader(String fileName) {
        this.fileName = fileName;
    }

    public void readCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.readLine();
            while (br.readLine() != null) {
                // placeholder — use OpenCSV in {@link com.cqhacks.racelive.service.RaceDataService} for API
            }
        } catch (IOException e) {
            System.out.println("File not found! " + e.getMessage());
        }
    }
}
