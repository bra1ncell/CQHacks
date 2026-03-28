package Project;

public class Lap {

    // Per-lap timing
    String sessionTime;     // "Time" col — session clock at lap end
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
    String  compound;       // e.g. "INTERMEDIATE"
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

    @Override
    public String toString() {
        return  "        ┌─ Lap " + fmt(lapNumber) + (isPersonalBest ? "  ★ Personal Best" : "") + "\n" +
                "        │  Date/Start   : " + fmt(lapStartDate) + "  " + fmt(lapStartTime) + "\n" +
                "        │  Lap Time     : " + fmt(lapTime) + "\n" +
                "        │  Position     : " + fmt(position) + "   Stint: " + fmt(stint) + "\n" +
                "        │  Sectors      : S1=" + fmt(sector1Time) +
                "  S2=" + fmt(sector2Time) +
                "  S3=" + fmt(sector3Time) + "\n" +
                "        │  Speeds(km/h) : I1=" + fmt(speedI1) +
                "  I2=" + fmt(speedI2) +
                "  FL=" + fmt(speedFL) +
                "  ST=" + fmt(speedST) + "\n" +
                "        │  Tyre         : " + fmt(compound) +
                "  Life=" + fmt(tyreLife) +
                "  Fresh=" + freshTyre + "\n" +
                "        │  Pit Out      : " + fmt(pitOutTime) + "\n" +
                "        │  Pit In       : " + fmt(pitInTime) + "\n" +
                "        │  Track Status : " + fmt(trackStatus) + "\n" +
                "        │  Deleted      : " + deleted + (deleted ? "  Reason: " + fmt(deletedReason) : "") + "\n" +
                "        │  Accurate     : " + isAccurate + "   FastF1Generated: " + fastF1Generated + "\n" +
                "        └─────────────────────────────────────────────";
    }
}
