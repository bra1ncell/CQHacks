package Project;

import java.util.ArrayList;

public class Driver {

    // Identity — constant across all laps
    String name;
    int    driverNumber;
    String team;

    // Extra fields populated by RaceResultsReader
    String broadcastName;
    String abbreviation;
    String driverID;
    String teamColor;
    String teamID;
    String firstName;
    String lastName;
    String fullName;
    String headshotUrl;
    String countryCode;

    ArrayList<Lap> laps;

    // Constructor stays the same — LapTimesReader uses this
    public Driver(String name, int driverNumber, String team) {
        this.name         = name;
        this.driverNumber = driverNumber;
        this.team         = team;
        this.laps         = new ArrayList<>();
    }

    // Called by RaceResultsReader to fill in the extra fields on an existing Driver
    void updateFromRaceResults(String broadcastName, String abbreviation, String driverID,
                               String teamColor, String teamID, String firstName,
                               String lastName, String fullName, String headshotUrl,
                               String countryCode) {
        this.broadcastName = broadcastName;
        this.abbreviation  = abbreviation;
        this.driverID      = driverID;
        this.teamColor     = teamColor;
        this.teamID        = teamID;
        this.firstName     = firstName;
        this.lastName      = lastName;
        this.fullName      = fullName;
        this.headshotUrl   = headshotUrl;
        this.countryCode   = countryCode;
    }

    void addLap(Lap lap) {
        laps.add(lap);
    }

    ArrayList<Lap> getLaps() {
        return laps;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("    ┌─ Driver : ").append(fullName != null ? fullName : name)
                .append("  (#").append(driverNumber).append(")")
                .append(abbreviation != null ? "  [" + abbreviation + "]" : "")
                .append("  Team: ").append(team).append("\n")
                .append("    │  Laps driven : ").append(laps.size()).append("\n");

        for (Lap lap : laps) {
            sb.append(lap.toString()).append("\n");
        }

        sb.append("    └─────────────────────────────────────────────────────");

        return sb.toString();
    }
}