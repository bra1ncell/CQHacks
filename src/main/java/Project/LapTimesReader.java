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

    void readCSV () {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            br.readLine(); // skip header

        } catch (IOException e) {
            System.out.println("File not found! " + e.getMessage());
        }
    }
}
