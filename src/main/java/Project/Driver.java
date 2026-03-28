package Project;

import java.util.ArrayList;

public class Driver {

    // Identity — constant across all laps
    String name;          // e.g. "VER"
    int    driverNumber;  // e.g. 1
    String broadcastName;
    String abbreviation;
    String driverID;
    String team;          // e.g. "Red Bull Racing"
    String teamColor;
    String teamID;
    String firstName;
    String lastName;
    String fullName;
    String headshotUrl;
    String countryCode;

    ArrayList<Lap> laps;

    public Driver(String name, int driverNumber, String braodcastName, String abbreviation,
                  String driverID, String team, String teamColor, String teamID, String firstName,
                  String lastName, String fullName, String headshotUrl, String countryCode) {
        this.name         = name;
        this.driverNumber = driverNumber;
        this.broadcastName = broadcastName;
        this.abbreviation  = abbreviation;
        this.driverId      = driverId;
        this.team         = team;
        this.teamColor     = teamColor;
        this.teamId        = teamId;
        this.firstName     = firstName;
        this.lastName      = lastName;
        this.fullName      = fullName;
        this.headshotUrl   = headshotUrl;
        this.countryCode   = countryCode;
        this.laps         = new ArrayList<>();
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

        sb.append("    ┌─ Driver : ").append(name)
                .append("  (#").append(driverNumber).append(")")
                .append("  Team: ").append(team).append("\n")
                .append("    │  Laps driven : ").append(laps.size()).append("\n");

        for (Lap lap : laps) {
            sb.append(lap.toString()).append("\n");
        }

        sb.append("    └─────────────────────────────────────────────────────");

        return sb.toString();
    }
}