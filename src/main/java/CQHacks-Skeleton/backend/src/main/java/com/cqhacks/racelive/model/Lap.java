package com.cqhacks.racelive.model;

public class Lap {

    // Per-lap timing
    String sessionTime;
    String lapTime;
    Double lapNumber;
    Double stint;

    // Pit lane
    String pitOutTime;
    String pitInTime;

    // Sectors
    String sector1Time;
    String sector2Time;
    String sector3Time;
    String sector1SessionTime;
    String sector2SessionTime;
    String sector3SessionTime;

    // Speed traps
    Double speedI1;
    Double speedI2;
    Double speedFL;
    Double speedST;

    // Tyre
    boolean isPersonalBest;
    String  compound;
    Double  tyreLife;
    boolean freshTyre;

    // Lap context
    String  lapStartTime;
    String  lapStartDate;
    String  trackStatus;
    Double  position;
    boolean deleted;
    String  deletedReason;
    boolean fastF1Generated;
    boolean isAccurate;

    public Lap(String sessionTime, String lapTime, Double lapNumber, Double stint,
               String pitOutTime, String pitInTime,
               String sector1Time, String sector2Time, String sector3Time,
               String sector1SessionTime, String sector2SessionTime, String sector3SessionTime,
               Double speedI1, Double speedI2, Double speedFL, Double speedST,
               boolean isPersonalBest, String compound, Double tyreLife, boolean freshTyre,
               String lapStartTime, String lapStartDate, String trackStatus,
               Double position, boolean deleted, String deletedReason,
               boolean fastF1Generated, boolean isAccurate) {

        this.sessionTime        = sessionTime;
        this.lapTime            = lapTime;
        this.lapNumber          = lapNumber;
        this.stint              = stint;
        this.pitOutTime         = pitOutTime;
        this.pitInTime          = pitInTime;
        this.sector1Time        = sector1Time;
        this.sector2Time        = sector2Time;
        this.sector3Time        = sector3Time;
        this.sector1SessionTime = sector1SessionTime;
        this.sector2SessionTime = sector2SessionTime;
        this.sector3SessionTime = sector3SessionTime;
        this.speedI1            = speedI1;
        this.speedI2            = speedI2;
        this.speedFL            = speedFL;
        this.speedST            = speedST;
        this.isPersonalBest     = isPersonalBest;
        this.compound           = compound;
        this.tyreLife           = tyreLife;
        this.freshTyre          = freshTyre;
        this.lapStartTime       = lapStartTime;
        this.lapStartDate       = lapStartDate;
        this.trackStatus        = trackStatus;
        this.position           = position;
        this.deleted            = deleted;
        this.deletedReason      = deletedReason;
        this.fastF1Generated    = fastF1Generated;
        this.isAccurate         = isAccurate;
    }

    // ── Getters (needed for Jackson JSON serialisation) ───────────────────────

    public String  getSessionTime()         { return sessionTime; }
    public String  getLapTime()             { return lapTime; }
    public Double  getLapNumber()           { return lapNumber; }
    public Double  getStint()               { return stint; }
    public String  getPitOutTime()          { return pitOutTime; }
    public String  getPitInTime()           { return pitInTime; }
    public String  getSector1Time()         { return sector1Time; }
    public String  getSector2Time()         { return sector2Time; }
    public String  getSector3Time()         { return sector3Time; }
    public String  getSector1SessionTime()  { return sector1SessionTime; }
    public String  getSector2SessionTime()  { return sector2SessionTime; }
    public String  getSector3SessionTime()  { return sector3SessionTime; }
    public Double  getSpeedI1()             { return speedI1; }
    public Double  getSpeedI2()             { return speedI2; }
    public Double  getSpeedFL()             { return speedFL; }
    public Double  getSpeedST()             { return speedST; }
    public boolean isPersonalBest()         { return isPersonalBest; }
    public String  getCompound()            { return compound; }
    public Double  getTyreLife()            { return tyreLife; }
    public boolean isFreshTyre()            { return freshTyre; }
    public String  getLapStartTime()        { return lapStartTime; }
    public String  getLapStartDate()        { return lapStartDate; }
    public String  getTrackStatus()         { return trackStatus; }
    public Double  getPosition()            { return position; }
    public boolean isDeleted()              { return deleted; }
    public String  getDeletedReason()       { return deletedReason; }
    public boolean isFastF1Generated()      { return fastF1Generated; }
    public boolean isAccurate()             { return isAccurate; }
}
