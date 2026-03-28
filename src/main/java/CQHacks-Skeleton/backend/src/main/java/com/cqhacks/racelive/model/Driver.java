package com.cqhacks.racelive.model;

import java.util.ArrayList;
import java.util.List;

public class Driver {

    // Core identity — set by LapTimesReader
    String name;
    int    driverNumber;
    String team;

    // Extra fields — filled in by RaceResultsReader
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

    // Result fields — filled in by RaceResultsReader
    Double  position;
    Integer classifiedPosition;
    Double  gridPosition;
    String  time;
    String  elapsedTime;
    String  status;
    Double  points;
    Double  laps;
    String  q1;
    String  q2;
    String  q3;

    List<Lap> lapData;

    public Driver(String name, int driverNumber, String team) {
        this.name         = name;
        this.driverNumber = driverNumber;
        this.team         = team;
        this.lapData      = new ArrayList<>();
    }

    /** Called by RaceResultsReader to enrich an existing Driver with result data. */
    public void updateFromRaceResults(String broadcastName, String abbreviation, String driverID,
                                      String teamColor, String teamID, String firstName,
                                      String lastName, String fullName, String headshotUrl,
                                      String countryCode, Double position, Integer classifiedPosition,
                                      Double gridPosition, String time, String elapsedTime,
                                      String status, Double points, Double laps,
                                      String q1, String q2, String q3) {
        this.broadcastName      = broadcastName;
        this.abbreviation       = abbreviation;
        this.driverID           = driverID;
        this.teamColor          = teamColor;
        this.teamID             = teamID;
        this.firstName          = firstName;
        this.lastName           = lastName;
        this.fullName           = fullName;
        this.headshotUrl        = headshotUrl;
        this.countryCode        = countryCode;
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

    public void addLap(Lap lap) { lapData.add(lap); }

    // ── Getters (needed for Jackson JSON serialisation) ───────────────────────

    public String  getName()               { return name; }
    public int     getDriverNumber()       { return driverNumber; }
    public String  getTeam()               { return team; }
    public String  getBroadcastName()      { return broadcastName; }
    public String  getAbbreviation()       { return abbreviation; }
    public String  getDriverID()           { return driverID; }
    public String  getTeamColor()          { return teamColor; }
    public String  getTeamID()             { return teamID; }
    public String  getFirstName()          { return firstName; }
    public String  getLastName()           { return lastName; }
    public String  getFullName()           { return fullName; }
    public String  getHeadshotUrl()        { return headshotUrl; }
    public String  getCountryCode()        { return countryCode; }
    public Double  getPosition()           { return position; }
    public Integer getClassifiedPosition() { return classifiedPosition; }
    public Double  getGridPosition()       { return gridPosition; }
    public String  getTime()               { return time; }
    public String  getElapsedTime()        { return elapsedTime; }
    public String  getStatus()             { return status; }
    public Double  getPoints()             { return points; }
    public Double  getLaps()               { return laps; }
    public String  getQ1()                 { return q1; }
    public String  getQ2()                 { return q2; }
    public String  getQ3()                 { return q3; }
    public List<Lap> getLapData()          { return lapData; }
}
