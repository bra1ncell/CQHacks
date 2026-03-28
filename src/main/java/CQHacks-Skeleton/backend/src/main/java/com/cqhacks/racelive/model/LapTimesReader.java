package com.cqhacks.racelive.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LapTimesReader {

    /*
     * Time,Driver,DriverNumber,LapTime,LapNumber,Stint,PitOutTime,PitInTime,
     * Sector1Time,Sector2Time,Sector3Time,Sector1SessionTime,Sector2SessionTime,
     * Sector3SessionTime,SpeedI1,SpeedI2,SpeedFL,SpeedST,IsPersonalBest,Compound,
     * TyreLife,FreshTyre,Team,LapStartTime,LapStartDate,TrackStatus,Position,
     * Deleted,DeletedReason,FastF1Generated,IsAccurate,Round,Country,Location,Event Name
     */

    private static final int TIME                  = 0;
    private static final int DRIVER                = 1;
    private static final int DRIVER_NUMBER         = 2;
    private static final int LAP_TIME              = 3;
    private static final int LAP_NUMBER            = 4;
    private static final int STINT                 = 5;
    private static final int PIT_OUT_TIME          = 6;
    private static final int PIT_IN_TIME           = 7;
    private static final int SECTOR_1_TIME         = 8;
    private static final int SECTOR_2_TIME         = 9;
    private static final int SECTOR_3_TIME         = 10;
    private static final int SECTOR_1_SESSION_TIME = 11;
    private static final int SECTOR_2_SESSION_TIME = 12;
    private static final int SECTOR_3_SESSION_TIME = 13;
    private static final int SPEEDI1               = 14;
    private static final int SPEEDI2               = 15;
    private static final int SPEEDFL               = 16;
    private static final int SPEEDST               = 17;
    private static final int IS_PERSONAL_BEST      = 18;
    private static final int COMPOUND              = 19;
    private static final int TYRE_LIFE             = 20;
    private static final int FRESH_TYRE            = 21;
    private static final int TEAM                  = 22;
    private static final int LAP_START_TIME        = 23;
    private static final int LAP_START_DATE        = 24;
    private static final int TRACK_STATUS          = 25;
    private static final int POSITION              = 26;
    private static final int DELETED               = 27;
    private static final int DELETED_REASON        = 28;
    private static final int FAST_F1_GENERATED     = 29;
    private static final int IS_ACCURATE           = 30;
    private static final int ROUND                 = 31;
    private static final int COUNTRY               = 32;
    private static final int LOCATION              = 33;
    private static final int EVENT_NAME            = 34;

    public static List<Race> read(Path filePath) throws IOException {
        Map<String, Race> racesByEvent = new LinkedHashMap<>();

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            br.readLine(); // skip header

            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] t = line.split(",", -1);

                String eventName = get(t, EVENT_NAME);

                Race race = racesByEvent.computeIfAbsent(eventName, key ->
                        new Race(parseInt(t, ROUND), get(t, COUNTRY), get(t, LOCATION), key));

                int    driverNumber = parseInt(t, DRIVER_NUMBER);
                String driverName   = get(t, DRIVER);
                String team         = get(t, TEAM);

                Driver driver = race.getDrivers().stream()
                        .filter(d -> d.getDriverNumber() == driverNumber)
                        .findFirst()
                        .orElseGet(() -> {
                            Driver d = new Driver(driverName, driverNumber, team);
                            race.addDriver(d);
                            return d;
                        });

                driver.addLap(new Lap(
                        get(t, TIME),
                        get(t, LAP_TIME),
                        parseDouble(t, LAP_NUMBER),
                        parseDouble(t, STINT),
                        get(t, PIT_OUT_TIME),
                        get(t, PIT_IN_TIME),
                        get(t, SECTOR_1_TIME),
                        get(t, SECTOR_2_TIME),
                        get(t, SECTOR_3_TIME),
                        get(t, SECTOR_1_SESSION_TIME),
                        get(t, SECTOR_2_SESSION_TIME),
                        get(t, SECTOR_3_SESSION_TIME),
                        parseDouble(t, SPEEDI1),
                        parseDouble(t, SPEEDI2),
                        parseDouble(t, SPEEDFL),
                        parseDouble(t, SPEEDST),
                        parseBool(t, IS_PERSONAL_BEST),
                        get(t, COMPOUND),
                        parseDouble(t, TYRE_LIFE),
                        parseBool(t, FRESH_TYRE),
                        get(t, LAP_START_TIME),
                        get(t, LAP_START_DATE),
                        get(t, TRACK_STATUS),
                        parseDouble(t, POSITION),
                        parseBool(t, DELETED),
                        get(t, DELETED_REASON),
                        parseBool(t, FAST_F1_GENERATED),
                        parseBool(t, IS_ACCURATE)
                ));
            }
        }

        return new ArrayList<>(racesByEvent.values());
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

    private static boolean parseBool(String[] t, int i) {
        return get(t, i).equalsIgnoreCase("true");
    }
}
