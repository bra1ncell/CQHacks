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

    ArrayList<Lap>        laps;
    ArrayList<RaceResult> raceResults;

    // Constructor stays the same — LapTimesReader uses this
    public Driver(String name, int driverNumber, String team) {
        this.name         = name;
        this.driverNumber = driverNumber;
        this.team         = team;
        this.laps         = new ArrayList<>();
        this.raceResults  = new ArrayList<>();
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

    void addRaceResult(RaceResult result) {
        raceResults.add(result);
    }

    ArrayList<Lap> getLaps() {
        return laps;
    }

    ArrayList<RaceResult> getRaceResults() {
        return raceResults;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("    ┌─ Driver : ").append(fullName != null ? fullName : name)
                .append("  (#").append(driverNumber).append(")")
                .append(abbreviation != null ? "  [" + abbreviation + "]" : "")
                .append("  Team: ").append(team).append("\n")
                .append("    │  Laps driven : ").append(laps.size()).append("\n");

        if (!raceResults.isEmpty()) {
            sb.append("    │  Race results : ").append(raceResults.size()).append(" races\n");
            for (RaceResult rr : raceResults) {
                sb.append("    │  ").append(rr.toString()).append("\n");
            }
        }

        for (Lap lap : laps) {
            sb.append(lap.toString()).append("\n");
        }

        sb.append("    └─────────────────────────────────────────────────────");

        return sb.toString();
    }

    // ── Inner class ──────────────────────────────────────────────────────────

    static class RaceResult {

        String eventName;
        int    round;
        Double position;
        String classifiedPosition;
        Double gridPosition;
        String time;
        String elapsedTime;
        String status;
        Double points;
        Double laps;
        String q1;
        String q2;
        String q3;

        public RaceResult(String eventName, int round, Double position, String classifiedPosition,
                          Double gridPosition, String time, String elapsedTime, String status,
                          Double points, Double laps, String q1, String q2, String q3) {
            this.eventName          = eventName;
            this.round              = round;
            this.position           = position;
            this.classifiedPosition = classifiedPosition;
            this.gridPosition       = gridPosition;
            this.time               = time;
            this.elapsedTime        = elapsedTime;
            this.status             = status;
            this.points             = points;
            this.laps               = laps;
            this.q1                 = q1;
            this.q2                 = q2;
            this.q3                 = q3;
        }

        @Override
        public String toString() {
            return String.format("        R%02d %-30s  P%-3s (Grid: %s)  %s  %.0f pts",
                    round,
                    eventName,
                    classifiedPosition != null && !classifiedPosition.isEmpty() ? classifiedPosition : "—",
                    gridPosition != null ? String.format("%.0f", gridPosition) : "—",
                    status,
                    points != null ? points : 0.0);
        }
    }
}
