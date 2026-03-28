package com.cqhacks.racelive.service;

import com.cqhacks.racelive.config.DataPathsProperties;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class RaceDataService {

    private final DataPathsProperties paths;

    public RaceDataService(DataPathsProperties paths) {
        this.paths = paths;
    }

    public List<Map<String, String>> loadRaceResults() throws IOException, CsvException {
        java.nio.file.Path path = paths.getRaceResultsCsv();
        assertReadable(path);
        try (var br = Files.newBufferedReader(path);
                CSVReader reader = new CSVReader(br)) {
            return readAllRows(reader);
        }
    }

    public List<Map<String, String>> loadLapTimesForRound(int round, int limit) throws IOException, CsvException {
        java.nio.file.Path path = paths.getLapTimesCsv();
        assertReadable(path);
        try (var br = Files.newBufferedReader(path);
                CSVReader reader = new CSVReader(br)) {
            String[] header = reader.readNext();
            if (header == null) {
                return List.of();
            }
            int roundIdx = indexOf(header, "Round");
            if (roundIdx < 0) {
                throw new IllegalStateException("LapTimes CSV missing Round column");
            }
            List<Map<String, String>> out = new ArrayList<>();
            String[] row;
            while ((row = reader.readNext()) != null && out.size() < limit) {
                String raw = get(row, roundIdx);
                if (raw.isBlank()) {
                    continue;
                }
                if (roundMatches(raw, round)) {
                    out.add(rowToMap(header, row));
                }
            }
            return out;
        }
    }

    private static boolean roundMatches(String cell, int round) {
        try {
            double v = Double.parseDouble(cell.trim());
            return Math.abs(v - round) < 0.001;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int indexOf(String[] header, String name) {
        for (int i = 0; i < header.length; i++) {
            if (name.equals(header[i])) {
                return i;
            }
        }
        return -1;
    }

    private static String get(String[] row, int i) {
        return i < row.length && row[i] != null ? row[i] : "";
    }

    private static List<Map<String, String>> readAllRows(CSVReader reader) throws IOException, CsvException {
        String[] header = reader.readNext();
        if (header == null) {
            return List.of();
        }
        List<Map<String, String>> rows = new ArrayList<>();
        for (String[] row : reader.readAll()) {
            rows.add(rowToMap(header, row));
        }
        return rows;
    }

    private static Map<String, String> rowToMap(String[] header, String[] row) {
        Map<String, String> m = new LinkedHashMap<>();
        for (int i = 0; i < header.length; i++) {
            m.put(header[i], i < row.length && row[i] != null ? row[i] : "");
        }
        return m;
    }

    private static void assertReadable(java.nio.file.Path path) throws IOException {
        if (!Files.isRegularFile(path)) {
            throw new IOException(
                    "CSV not found: " + path + ". Run the server from the backend/ directory, set RACE_RESULTS_CSV / LAP_TIMES_CSV, or place CSVs in the repo root.");
        }
    }
}
