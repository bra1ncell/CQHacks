package Project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LapTimesReader {
    String fileName = "LapTimes.csv";
    public LapTimesReader(String fileName) {
        this.fileName = fileName;
    }

    /*
    * Time,Driver,DriverNumber,LapTime,LapNumber,Stint,PitOutTime,PitInTime,Sector1Time,Sector2Time,Sector3Time,Sector1SessionTime,Sector2SessionTime,
    * Sector3SessionTime,SpeedI1,SpeedI2,SpeedFL,SpeedST,IsPersonalBest,Compound,TyreLife,FreshTyre,Team,LapStartTime,LapStartDate,TrackStatus,Position,
    * Deleted,DeletedReason,FastF1Generated,IsAccurate,Round,Country,Location,Event Name
    * */

    private final int TIME = 0;
    private final int DRIVER = 1;
    private final int DRIVER_NUMBER = 2;
    private final int LAP_TIME = 3;
    private final int LAP_NUMBER = 4;
    private final int STINT = 5;
    private final int PIT_OUT_TIME = 6;
    private final int PIT_IN_TIME = 7;
    private final int SECTOR_1_TIME = 8;
    private final int SECTOR_2_TIME = 9;
    private final int SECTOR_3_TIME = 10;
    private final int SECTOR_1_SESSION_TIME = 11;
    private final int SECTOR_2_SESSION_TIME = 12;
    private final int SECTOR_3_SESSION_TIME = 13;
    private final int SPEEDI1 = 13;
    private final int SPEEDI2 = 14;
    private final int SPEEDFL = 15;
    private final int SPEEDST = 16;
    private final int IS_PERSONAL_BEST = 17;
    private final int COMPOUND = 18;
    private final int TYRE_LIFE = 19;
    private final int FRESH_TYRE = 20;
    private final int TEAM = 21;
    private final int LAP_START_TIME = 22;
    private final int LAP_START_DATE = 23;
    private final int TRACK_STATUS = 24;
    private final int POSITION = 25;
    private final int DELETED = 26;
    private final int DELETED_REASON = 27;
    private final int FAST_F1_GENERATED = 28;
    private final int IS_ACCURATE = 29;
    private final int ROUND = 30;
    private final int COUNTRY = 31;
    private final int LOCATION = 32;
    private final int EVENT = 33;
    private final int NAME = 34;



    void readCSV () {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                for (String token : tokens) {

                }
            }

        } catch (IOException e) {
            System.out.println("File not found! " + e.getMessage());
        }
    }
}
