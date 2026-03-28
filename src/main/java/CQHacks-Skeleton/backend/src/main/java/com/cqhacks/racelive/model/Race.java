package com.cqhacks.racelive.model;

import java.util.ArrayList;
import java.util.List;

public class Race {

    String country;
    int    round;
    String location;
    String eventName;

    List<Driver> drivers;

    public Race(int round, String country, String location, String eventName) {
        this.round     = round;
        this.country   = country;
        this.location  = location;
        this.eventName = eventName;
        this.drivers   = new ArrayList<>();
    }

    public void addDriver(Driver driver) { drivers.add(driver); }

    // ── Getters (needed for Jackson JSON serialisation) ───────────────────────

    public String       getCountry()   { return country; }
    public int          getRound()     { return round; }
    public String       getLocation()  { return location; }
    public String       getEventName() { return eventName; }
    public List<Driver> getDrivers()   { return drivers; }
}
