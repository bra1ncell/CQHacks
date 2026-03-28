package com.cqhacks.racelive.demo;

import java.util.ArrayList;
import java.util.List;

public class Driver {

    public final String name;
    public final int driverNumber;
    public final String team;
    private final List<Lap> laps = new ArrayList<>();

    public Driver(String name, int driverNumber, String team) {
        this.name = name;
        this.driverNumber = driverNumber;
        this.team = team;
    }

    public void addLap(Lap lap) {
        laps.add(lap);
    }

    public List<Lap> getLaps() {
        return laps;
    }

    @Override
    public String toString() {
        return String.format("  • #%d %s (%s) — %d laps%n", driverNumber, name, team, laps.size());
    }
}
