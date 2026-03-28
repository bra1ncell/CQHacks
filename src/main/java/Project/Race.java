package Project;

public class Race {
    /*
     * Time,Driver,DriverNumber,LapTime,LapNumber,Stint,PitOutTime,PitInTime,Sector1Time,Sector2Time,Sector3Time,Sector1SessionTime,Sector2SessionTime,
     * Sector3SessionTime,SpeedI1,SpeedI2,SpeedFL,SpeedST,IsPersonalBest,Compound,TyreLife,FreshTyre,Team,LapStartTime,LapStartDate,TrackStatus,Position,
     * Deleted,DeletedReason,FastF1Generated,IsAccurate,Round,Country,Location,Event Name
     * */

    String country;
    int rounds;
    String location;
    String eventName;

    ArrayList<Driver> drivers;

    public Race(int rounds, String country, String location, String eventName) {
        this.rounds = rounds;
        this.country = country;
        this.location = location;
        this.eventName = eventName;java

        drivers = new ArrayList<>();
    }

    void addDriver(Driver driver) {
        drivers.add(driver);
    }

    ArrayList<Driver> getDrivers () {
        return drivers;
    }
}
