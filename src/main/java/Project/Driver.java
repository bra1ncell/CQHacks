package Project;

import java.util.ArrayList;

public class Driver {

    // Identity — constant across all laps
    String name;          // e.g. "VER"
    int    driverNumber;  // e.g. 1
    String team;          // e.g. "Red Bull Racing"

    ArrayList<Lap> laps;

    public Driver(String name, int driverNumber, String team) {
        this.name         = name;
        this.driverNumber = driverNumber;
        this.team         = team;
        this.laps         = new ArrayList<>();
    }

    void addLap(Lap lap) {
        laps.add(lap);
    }

    ArrayList<Lap> getLaps() {
        return laps;
    }
}