package Project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class LapTimesReader {

    String fileName = "LapTimes.csv";

    public LapTimesReader(String fileName) {
        this.fileName = fileName;
    }

    /*
     * Time,Driver,DriverNumber,LapTime,LapNumber,Stint,PitOutTime,PitInTime,
     * Sector1Time,Sector2Time,Sector3Time,Sector1SessionTime,Sector2SessionTime,
     * Sector3SessionTime,SpeedI1,SpeedI2,SpeedFL,SpeedST,IsPersonalBest,Compound,
     * TyreLife,FreshTyre,Team,LapStartTime,LapStartDate,TrackStatus,Position,
     * Deleted,DeletedReason,FastF1Generated,IsAccurate,Round,Country,Location,Event Name
     */

    private final int TIME                  = 0;
    private final int DRIVER                = 1;
    private final int DRIVER_NUMBER         = 2;
    private final int LAP_TIME              = 3;
    private final int LAP_NUMBER            = 4;
    private final int STINT                 = 5;
    private final int PIT_OUT_TIME          = 6;
    private final int PIT_IN_TIME           = 7;
    private final int SECTOR_1_TIME         = 8;
    private final int SECTOR_2_TIME         = 9;
    private final int SECTOR_3_TIME         = 10;
    private final int SECTOR_1_SESSION_TIME = 11;
    private final int SECTOR_2_SESSION_TIME = 12;
    private final int SECTOR_3_SESSION_TIME = 13;
    private final int SPEEDI1               = 14;
    private final int SPEEDI2               = 15;
    private final int SPEEDFL               = 16;
    private final int SPEEDST               = 17;
    private final int IS_PERSONAL_BEST      = 18;
    private final int COMPOUND              = 19;
    private final int TYRE_LIFE             = 20;
    private final int FRESH_TYRE            = 21;
    private final int TEAM                  = 22;
    private final int LAP_START_TIME        = 23;
    private final int LAP_START_DATE        = 24;
    private final int TRACK_STATUS          = 25;
    private final int POSITION              = 26;
    private final int DELETED               = 27;
    private final int DELETED_REASON        = 28;
    private final int FAST_F1_GENERATED     = 29;
    private final int IS_ACCURATE           = 30;
    private final int ROUND                 = 31;
    private final int COUNTRY               = 32;
    private final int LOCATION              = 33;
    private final int EVENT_NAME            = 34;

    ArrayList<Race> readCSV() {
        // Keyed by Event Name so all rows for the same race share one Race object
        Map<String, Race> racesByEvent = new LinkedHashMap<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            br.readLine(); // skip header

            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] t = line.split(",", -1); // -1 keeps empty trailing fields

                String eventName = get(t, EVENT_NAME);

                // Look up existing Race for this event, or create a new one
                Race race = racesByEvent.computeIfAbsent(eventName, key -> new Race(parseInt(t, ROUND), get(t, COUNTRY), get(t, LOCATION), key));

                int driverNumber = parseInt(t, DRIVER_NUMBER);
                String driverName = get(t, DRIVER);
                String team = get(t, TEAM);

                Driver driver = race.getDrivers()
                        .stream()
                        .filter(d -> d.driverNumber == driverNumber)
                        .findFirst()
                        .orElseGet(() -> {
                            Driver d = new Driver(driverName, driverNumber, team);
                            race.addDriver(d);
                            return d;
                        });

                // Build Lap from remaining columns and attach to Driver
                Lap lap = new Lap(
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
                );

                driver.addLap(lap);
            }

            br.close();

        } catch (IOException e) {
            System.out.println("File not found! " + e.getMessage());
        }

        return new ArrayList<>(racesByEvent.values());
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /** Safe field access — returns empty string if index is out of bounds or value is NaN. */
    private String get(String[] tokens, int index) {
        if (index >= tokens.length) return "";
        String v = tokens[index].trim();
        return v.equalsIgnoreCase("nan") ? "" : v;
    }

    private Double parseDouble(String[] tokens, int index) {
        String v = get(tokens, index);
        if (v.isEmpty()) return null;
        try { return Double.parseDouble(v); } catch (NumberFormatException e) { return null; }
    }

    private int parseInt(String[] tokens, int index) {
        String v = get(tokens, index);
        if (v.contains(".")) v = v.substring(0, v.indexOf('.'));
        return Integer.parseInt(v);
    }

    private boolean parseBool(String[] tokens, int index) {
        return get(tokens, index).equalsIgnoreCase("true");
    }
}