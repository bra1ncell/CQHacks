package com.cqhacks.racelive.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RaceResultsReader {

    /*
     * DriverNumber,BroadcastName,Abbreviation,DriverId,TeamName,TeamColor,TeamId,
     * FirstName,LastName,FullName,HeadshotUrl,CountryCode,Position,ClassifiedPosition,
     * GridPosition,Time,ElapsedTime,Status,Points,Laps,Round,Country,Location,
     * Event Name,Q1,Q2,Q3
     */

    private static final int DRIVER_NUMBER       = 0;
    private static final int BROADCAST_NAME      = 1;
    private static final int ABBREVIATION        = 2;
    private static final int DRIVER_ID           = 3;
    private static final int TEAM_NAME           = 4;
    private static final int TEAM_COLOR          = 5;
    private static final int TEAM_ID             = 6;
    private static final int FIRST_NAME          = 7;
    private static final int LAST_NAME           = 8;
    private static final int FULL_NAME           = 9;
    private static final int HEADSHOT_URL        = 10;
    private static final int COUNTRY_CODE        = 11;
    private static final int POSITION            = 12;
    private static final int CLASSIFIED_POSITION = 13;
    private static final int GRID_POSITION       = 14;
    private static final int TIME                = 15;
    private static final int ELAPSED_TIME        = 16;
    private static final int STATUS              = 17;
    private static final int POINTS              = 18;
    private static final int LAPS                = 19;
    private static final int ROUND               = 20;
    private static final int COUNTRY             = 21;
    private static final int LOCATION            = 22;
    private static final int EVENT_NAME          = 23;
    private static final int Q1                  = 24;
    private static final int Q2                  = 25;
    private static final int Q3                  = 26;

    /**
     * Reads RaceResults.csv and merges result data into the races list
     * that was already built by LapTimesReader.
     * Races present in RaceResults.csv but missing from the list are created.
     */
    public static void merge(Path filePath, List<Race> races) throws IOException {
        // Index existing races by round number for fast lookup
        Map<Integer, Race> byRound = races.stream()
                .collect(Collectors.toMap(Race::getRound, Function.identity()));

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            br.readLine(); // skip header

            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] t = line.split(",", -1);

                int    round     = parseInt(t, ROUND);
                String eventName = get(t, EVENT_NAME);
                String country   = get(t, COUNTRY);
                String location  = get(t, LOCATION);

                Race race = byRound.computeIfAbsent(round, r -> {
                    Race newRace = new Race(r, country, location, eventName);
                    races.add(newRace);
                    return newRace;
                });

                int driverNumber = parseInt(t, DRIVER_NUMBER);

                // Find the matching driver already loaded from LapTimes, or create one
                Driver driver = race.getDrivers().stream()
                        .filter(d -> d.getDriverNumber() == driverNumber)
                        .findFirst()
                        .orElseGet(() -> {
                            Driver d = new Driver(get(t, FULL_NAME), driverNumber, get(t, TEAM_NAME));
                            race.addDriver(d);
                            return d;
                        });

                driver.updateFromRaceResults(
                        get(t, BROADCAST_NAME),
                        get(t, ABBREVIATION),
                        get(t, DRIVER_ID),
                        get(t, TEAM_COLOR),
                        get(t, TEAM_ID),
                        get(t, FIRST_NAME),
                        get(t, LAST_NAME),
                        get(t, FULL_NAME),
                        get(t, HEADSHOT_URL),
                        get(t, COUNTRY_CODE),
                        parseDouble(t, POSITION),
                        parseIntOrNull(t, CLASSIFIED_POSITION),
                        parseDouble(t, GRID_POSITION),
                        get(t, TIME),
                        get(t, ELAPSED_TIME),
                        get(t, STATUS),
                        parseDouble(t, POINTS),
                        parseDouble(t, LAPS),
                        get(t, Q1),
                        get(t, Q2),
                        get(t, Q3)
                );
            }
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static String get(String[] t, int i) {
        if (i >= t.length) return "";
        String v = t[i].trim();
        return v.equalsIgnoreCase("nan") ? "" : v;
    }

    private static Double parseDouble(String[] t, int i) {
        String v = get(t, i);
        if (v.isEmpty()) return null;
        try { return Double.parseDouble(v); } catch (NumberFormatException e) { return null; }
    }

    private static int parseInt(String[] t, int i) {
        String v = get(t, i);
        if (v.contains(".")) v = v.substring(0, v.indexOf('.'));
        return Integer.parseInt(v);
    }

    private static Integer parseIntOrNull(String[] t, int i) {
        String v = get(t, i);
        if (v.isEmpty()) return null;
        if (v.contains(".")) v = v.substring(0, v.indexOf('.'));
        try { return Integer.parseInt(v); } catch (NumberFormatException e) { return null; }
    }
}
